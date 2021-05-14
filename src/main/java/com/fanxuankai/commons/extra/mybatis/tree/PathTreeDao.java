package com.fanxuankai.commons.extra.mybatis.tree;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.ColumnCache;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;
import org.apache.ibatis.reflection.property.PropertyNamer;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
public interface PathTreeDao<T extends PathTreeNode, C> extends TreeDao<T, C> {
    /**
     * 祖先
     *
     * @param id 节点 id
     * @return Ancestor
     */
    @Override
    default Ancestor<T> ancestor(Long id) {
        T node = getById(id);
        List<String> codes = Arrays.stream(node.getPath().split(StrPool.SLASH)).collect(Collectors.toList());
        codes.remove(node.getCode());
        List<T> nodes = list(Wrappers.lambdaQuery(entityClass())
                .in(T::getCode, codes)
                .lt(T::getLevel, node.getLevel()));
        return TreeUtils.buildAncestor(node.getPid(), nodes);
    }

    /**
     * 子孙
     *
     * @param id 节点 id
     * @return /
     */
    @Override
    default List<Descendant<T>> descendants(Long id) {
        T node = getById(id);
        List<T> nodes = list(Wrappers.lambdaQuery(entityClass())
                .likeRight(T::getPath, node.getPath())
                .gt(T::getLevel, node.getLevel()));
        return TreeUtils.buildDescendants(id, nodes);
    }

    /**
     * 兄弟节点 拥有同一父节点的子节点
     *
     * @param id 节点 id
     * @return /
     */
    @Override
    default List<T> sibling(Long id) {
        T node = getById(id);
        int i = node.getPath().lastIndexOf(StrPool.SLASH + node.getCode());
        String parentPath = node.getPath().substring(0, i);
        Map<String, ColumnCache> columnMap = LambdaUtils.getColumnMap(entityClass());
        SFunction<T, String> codeF = PathTreeNode::getCode;
        SFunction<T, String> pathF = PathTreeNode::getCode;
        SerializedLambda codeResolve = LambdaUtils.resolve(codeF);
        SerializedLambda pathResolve = LambdaUtils.resolve(pathF);
        ColumnCache codeColumnCache =
                columnMap.get(LambdaUtils.formatKey(PropertyNamer.methodToProperty(codeResolve.getImplMethodName())));
        ColumnCache pathColumnCache =
                columnMap.get(LambdaUtils.formatKey(PropertyNamer.methodToProperty(pathResolve.getImplMethodName())));
        String last = String.format(" AND %s = CONCAT( '%s', %s)", pathColumnCache.getColumnSelect(), parentPath,
                codeColumnCache.getColumnSelect());
        return list(Wrappers.lambdaQuery(entityClass())
                .ne(T::getId, id)
                .eq(T::getLevel, node.getLevel())
                .last(last)
        );
    }

    /**
     * 叶节点 没有子节点的节点
     *
     * @param id 节点 id
     * @return /
     */
    @Override
    default List<T> leaf(Long id) {
        return TreeManager.leaf(loadAll(id), true);
    }

    /**
     * 非叶节点 有子节点的节点
     *
     * @param id 节点 id
     * @return /
     */
    @Override
    default List<T> nonLeaf(Long id) {
        return TreeManager.leaf(loadAll(id), false);
    }

    /**
     * 根节点 没有父节点的节点
     *
     * @param id 节点 id
     * @return /
     */
    @Override
    default T root(Long id) {
        T node = getById(id);
        if (node.getPid() == null) {
            return node;
        }
        String rootCode = node.getPath().split(StrPool.SLASH)[0];
        return getOne(Wrappers.lambdaQuery(entityClass())
                .eq(T::getCode, rootCode)
                .eq(T::getLevel, ROOT_LEVEL), false);
    }

    /**
     * 插入新节点
     *
     * @param node 节点
     */
    @Override
    default void insertNode(T node) {
        Long pid = node.getPid();
        int level;
        String path;
        if (pid == null) {
            level = ROOT_LEVEL;
            path = StrPool.SLASH + node.getCode();
        } else {
            T parent = getById(pid);
            level = parent.getLevel() + 1;
            path = parent.getPath() + StrPool.SLASH + node.getCode();
        }
        node.setLevel(level);
        node.setPath(path);
        save(node);
    }

    /**
     * 删除节点
     *
     * @param id 节点 id
     */
    @Override
    default void removeNode(Long id) {
        T node = getById(id);
        remove(Wrappers.lambdaQuery(entityClass())
                .likeRight(T::getPath, node.getPath())
                .ge(T::getLevel, node.getLevel()));
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
        // 上级节点未改变
        if (Objects.equals(node.getPid(), targetPid)) {
            return;
        }
        List<Descendant<T>> descendants = descendants(id);
        List<T> descendantNodes = TreeUtils.flat(descendants);
        String path;
        int oldLevel = node.getLevel();
        int newLevel;
        if (targetPid == null) {
            // 改为根节点
            path = StrPool.SLASH + node.getCode();
            newLevel = ROOT_LEVEL;
        } else {
            // 修改上级节点
            // 需要移动节点以及所有子节点
            if (descendantNodes.stream().anyMatch(t -> Objects.equals(t.getId(), targetPid))) {
                throw new IllegalArgumentException("不能以子孙节点作为自己的父节点");
            }
            T parent = getById(targetPid);
            newLevel = parent.getLevel() + 1;
            path = parent.getPath() + StrPool.SLASH + node.getCode();
        }
        int levelDistance = newLevel - oldLevel;
        T nodeEntity = ReflectUtil.newInstance(entityClass());
        nodeEntity.setLevel(newLevel);
        nodeEntity.setPid(targetPid);
        nodeEntity.setPath(path);
        // 修改本身
        update(nodeEntity, Wrappers.lambdaUpdate(entityClass())
                .eq(T::getId, id)
                .setSql(targetPid == null, "pid = null")
        );
        // 修改本身之前查出所有子孙节点
        List<Long> allSubNodeIds = descendantNodes.stream().map(T::getId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(allSubNodeIds)) {
            // 修改所有子孙节点
            update(Wrappers.lambdaUpdate(entityClass())
                    .gt(T::getLevel, oldLevel)
                    .in(T::getId, allSubNodeIds)
                    .setSql(levelDistance > 0, "level = level + " + levelDistance)
                    .setSql(levelDistance < 0, "level = level - " + Math.abs(levelDistance))
            );
        }
        T updatePathEntity = ReflectUtil.newInstance(entityClass());
        for (Map.Entry<Long, String> entry : TreeManager.updatePath(path, descendants).entrySet()) {
            updatePathEntity.setPath(entry.getValue());
            update(updatePathEntity, Wrappers.lambdaUpdate(entityClass()).eq(T::getId, entry.getKey()));
        }
    }

    /**
     * 加载树
     *
     * @param id 树 id
     * @return /
     */
    default List<T> loadAll(Long id) {
        T root = root(id);
        return list(Wrappers.lambdaQuery(entityClass()).likeRight(T::getPath, root.getPath()));
    }
}