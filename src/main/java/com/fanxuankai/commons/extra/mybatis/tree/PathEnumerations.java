package com.fanxuankai.commons.extra.mybatis.tree;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.ColumnCache;
import com.fanxuankai.commons.util.Node;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
public class PathEnumerations {
    public interface Entity extends DefaultEntity {
        /**
         * 编码
         *
         * @return /
         */
        String getCode();

        /**
         * 路径
         *
         * @return /
         */
        String getPath();

        /**
         * 路径
         *
         * @param path /
         */
        void setPath(String path);
    }

    public interface Dao<T extends Entity> extends DefaultTreeDao<T> {
        /**
         * 祖先(ancestor)节点：A是所有节点的祖先，F是K与L的祖先。
         *
         * @param id 节点 id
         * @return /
         */
        @Override
        default List<T> ancestors(Long id) {
            T node = getById(id);
            List<String> codes = Arrays.stream(node.getPath().split(StrPool.SLASH)).collect(Collectors.toList());
            codes.remove(node.getCode());
            List<T> nodes = list(Wrappers.lambdaQuery(EntityClassCache.<T>entityClass(getClass())).in(T::getCode, codes));
            nodes.sort(Comparator.comparingInt(o -> o.getPath().length()));
            return nodes;
        }

        /**
         * 子孙(descendant)节点：所有节点是A的子孙，K与L是F的子孙。
         *
         * @param id 节点 id
         * @return /
         */
        @Override
        default List<Node<T>> descendants(Long id) {
            T node = getById(id);
            List<T> nodes = list(Wrappers.lambdaQuery(EntityClassCache.<T>entityClass(getClass()))
                    .ne(T::getId, node.getId())
                    .likeRight(T::getPath, node.getPath()));
            return PathEnumerationsUtils.buildDescendants(node, nodes);
        }

        /**
         * 父节点(parent node)：B直接连到E与F且只差一个阶度，则B为E与F的父节点
         *
         * @param id 节点 id
         * @return /
         */
        @Override
        default T parent(Long id) {
            T node = getById(id);
            String parentPath = PathEnumerationsUtils.getParentPath(node);
            if (parentPath == null) {
                return null;
            }
            return getOne(Wrappers.lambdaQuery(EntityClassCache.<T>entityClass(getClass())).eq(T::getPath, parentPath));
        }

        /**
         * 子节点(children node)：B直接连到E与F且只差一个阶度，则E与F为B的子节点。
         *
         * @param id 节点 id
         * @return /
         */
        @Override
        default List<T> children(Long id) {
            T node = getById(id);
            String parentPath = node.getPath();
            Class<T> entityClass = EntityClassCache.entityClass(getClass());
            ColumnCache codeColumnCache = TreeNodeUtils.getColumnCache(entityClass, T::getCode);
            ColumnCache pathColumnCache = TreeNodeUtils.getColumnCache(entityClass, T::getPath);
            String last = String.format(" AND %s = CONCAT( '%s', '%s', %s)", pathColumnCache.getColumnSelect(),
                    parentPath, StrPool.SLASH, codeColumnCache.getColumnSelect());
            return list(Wrappers.lambdaQuery(entityClass)
                    .ne(T::getId, id)
                    .last(last));
        }

        /**
         * 根节点(root node)：没有父节点的节点，为树的源头。 如：A。
         *
         * @param wrapper /
         * @return /
         */
        @Override
        default List<T> roots(LambdaQueryWrapper<T> wrapper) {
            Class<T> entityClass = EntityClassCache.entityClass(getClass());
            ColumnCache codeColumnCache = TreeNodeUtils.getColumnCache(entityClass, T::getCode);
            ColumnCache pathColumnCache = TreeNodeUtils.getColumnCache(entityClass, T::getPath);
            String last = String.format(" AND %s = CONCAT( '%s', %s)", pathColumnCache.getColumnSelect(),
                    StrPool.SLASH, codeColumnCache.getColumnSelect());
            return list(wrapper.isNotNull(T::getPath).last(last));
        }

        /**
         * 插入新节点
         *
         * @param node 节点
         */
        @Override
        default void saveNode(T node) {
            String path;
            if (node.getPid() == null) {
                path = StrPool.SLASH + node.getCode();
            } else {
                path = getById(node.getPid()).getPath() + StrPool.SLASH + node.getCode();
            }
            node.setPath(path);
            save(node);
        }

        /**
         * 删除节点
         *
         * @param id               节点 id
         * @param removeDescendant 是否删除子孙节点
         */
        @Override
        @Transactional(rollbackFor = Exception.class)
        default void removeNode(Long id, boolean removeDescendant) {
            T node = getById(id);
            removeById(id);
            if (removeDescendant) {
                remove(Wrappers.lambdaQuery(EntityClassCache.<T>entityClass(getClass())).likeRight(T::getPath, node.getPath()));
            } else {
                Class<T> entityClass = EntityClassCache.entityClass(getClass());
                ColumnCache pidColumnCache = TreeNodeUtils.getColumnCache(entityClass, T::getPid);
                update(Wrappers.lambdaUpdate(entityClass).eq(T::getPid, id)
                        .setSql(pidColumnCache.getColumnSelect() + " = null"));
            }
        }

        /**
         * 修改节点
         *
         * @param node 节点
         */
        @Override
        @Transactional(rollbackFor = {Exception.class})
        default void updateNode(T node) {
            updateById(node);
            Long pid = getById(node.getId()).getPid();
            if (!Objects.equals(pid, node.getPid())) {
                moveNode(node.getId(), node.getPid());
            }
        }

        /**
         * 修改节点
         *
         * @param node          节点
         * @param updateWrapper /
         */
        @Override
        default void updateNode(T node, Wrapper<T> updateWrapper) {
            update(node, updateWrapper);
            Long pid = getById(node.getId()).getPid();
            if (!Objects.equals(pid, node.getPid())) {
                moveNode(node.getId(), node.getPid());
            }
        }

        /**
         * 移动节点
         *
         * @param id        节点 id
         * @param targetPid 目标父节点 id
         */
        @Override
        @Transactional(rollbackFor = Exception.class)
        default void moveNode(Long id, Long targetPid) {
            T node = getById(id);
            T parent = getById(targetPid);
            // 上级节点未改变
            if (Objects.equals(PathEnumerationsUtils.getParentPath(node), parent.getPath())) {
                return;
            }
            List<Node<T>> nodes = descendants(id);
            String path;
            if (targetPid == null) {
                // 改为根节点
                path = StrPool.SLASH + node.getCode();
            } else {
                // 修改上级节点
                // 需要移动节点以及所有子节点
                List<T> descendantNodes = TreeNodeUtils.flat(nodes);
                if (descendantNodes.stream().anyMatch(t -> Objects.equals(t.getId(), targetPid))) {
                    throw new IllegalArgumentException("不能以子孙节点作为自己的父节点");
                }
                path = parent.getPath() + StrPool.SLASH + node.getCode();
            }
            T nodeEntity = ReflectUtil.newInstance(EntityClassCache.entityClass(getClass()));
            nodeEntity.setPath(path);
            // 修改本身
            update(nodeEntity, Wrappers.lambdaUpdate(EntityClassCache.<T>entityClass(getClass())).eq(T::getId, id));
            // 修改子孙节点
            for (Map.Entry<Long, String> entry : PathEnumerationsUtils.updatePath(path, nodes).entrySet()) {
                nodeEntity.setPath(entry.getValue());
                update(nodeEntity, Wrappers.lambdaUpdate(EntityClassCache.<T>entityClass(getClass())).eq(T::getId, entry.getKey()));
            }
        }
    }
}