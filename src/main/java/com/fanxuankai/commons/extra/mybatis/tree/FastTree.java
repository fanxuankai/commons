package com.fanxuankai.commons.extra.mybatis.tree;

import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.ColumnCache;
import com.fanxuankai.commons.exception.BizException;
import com.fanxuankai.commons.util.Assert;
import com.fanxuankai.commons.util.IdUtils;
import com.fanxuankai.commons.util.Node;
import com.fanxuankai.commons.util.ParamUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
public class FastTree {
    public interface Entity extends DefaultEntity {
        /**
         * 阶度
         *
         * @return /
         */
        Integer getLevel();

        /**
         * (non-Javadoc)
         *
         * @param level 阶度
         */
        void setLevel(Integer level);

        /**
         * 树 id
         *
         * @return /
         */
        Long getTreeId();

        /**
         * (non-Javadoc)
         *
         * @param treeId 树 id
         */
        void setTreeId(Long treeId);
    }

    public interface Dao<T extends Entity> extends DefaultTreeDao<T> {
        /**
         * 搜索树
         *
         * @param wrapper          /
         * @param queryHasChildren 查询是否有子节点
         * @return List
         */
        default List<Node<T>> search(LambdaQueryWrapper<T> wrapper, boolean queryHasChildren) {
            List<T> nodes = list(wrapper);
            if (CollectionUtils.isEmpty(nodes)) {
                return Collections.emptyList();
            }
            return nodes.stream()
                    .collect(Collectors.groupingBy(T::getTreeId))
                    .entrySet()
                    .stream()
                    .map(entry -> {
                        Integer toLevel = entry.getValue().stream()
                                .map(T::getLevel)
                                .max(Integer::compareTo)
                                .orElse(null);
                        return root(entry.getKey(), toLevel, queryHasChildren);
                    })
                    .sorted((o1, o2) -> o2.getItem().getId().compareTo(o1.getItem().getId()))
                    .collect(Collectors.toList());
        }

        /**
         * 根节点
         *
         * @param treeId           树 id
         * @param toLevel          展开至第几层, 不传则不展开
         * @param queryHasChildren 查询是否有子节点
         * @return Node
         */
        default Node<T> root(Long treeId, Integer toLevel, boolean queryHasChildren) {
            Class<T> entityClass = EntityClassCache.entityClass(getClass());
            if (toLevel == null) {
                T root = getOne(Wrappers.lambdaQuery(entityClass)
                        .eq(T::getTreeId, treeId)
                        .isNull(T::getPid), false);
                Assert.notNull(root, "找不到根节点");
                Node<T> node = new Node<>(root, Collections.emptyList());
                if (queryHasChildren) {
                    node.setHasChildren(hasChildren(Collections.singletonList(root.getId())).get(root.getId()));
                }
                return node;
            }
            List<Node<T>> nodes = list(Wrappers.lambdaQuery(entityClass)
                    .eq(T::getTreeId, treeId)
                    .le(T::getLevel, toLevel))
                    .stream()
                    .map(o -> new Node<>(o, null))
                    .collect(Collectors.toList());
            if (queryHasChildren) {
                Map<Long, Boolean> hasChildren =
                        hasChildren(nodes.stream().map(o -> o.getItem().getId()).collect(Collectors.toList()));
                nodes.forEach(o -> o.setHasChildren(hasChildren.get(o.getItem().getId())));
            }
            Map<Long, Node<T>> nodeMap = nodes.stream()
                    .collect(Collectors.toMap(o -> o.getItem().getId(), Function.identity()));
            // 树构建
            for (Node<T> node : nodes) {
                if (node.getItem().getPid() == null) {
                    continue;
                }
                Node<T> parent = nodeMap.get(node.getItem().getPid());
                List<Node<T>> children = parent.getChildren();
                if (children == null) {
                    children = new ArrayList<>();
                    parent.setChildren(children);
                }
                children.add(node);
            }
            // 获取根节点
            Node<T> root = nodes.stream()
                    .filter(o -> o.getItem().getPid() == null)
                    .findFirst()
                    .orElseThrow(() -> new BizException("该树找不到根节点: " + treeId));
            // 排序: 创建 id 降序
            List<Node<T>> children = root.getChildren();
            if (!CollectionUtils.isEmpty(children)) {
                children.sort((o1, o2) -> o2.getItem().getId().compareTo(o1.getItem().getId()));
            }
            return root;
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
            if (node == null) {
                return Collections.emptyList();
            }
            List<T> descendants = list(Wrappers.lambdaQuery(EntityClassCache.<T>entityClass(getClass()))
                    .eq(T::getTreeId, node.getTreeId())
                    .gt(T::getLevel, node.getLevel()));
            return TreeNodeUtils.buildDescendants(id, descendants);
        }

        /**
         * 阶度(level)：为树中的第几代，而根节点为第一代，阶度为1。
         *
         * @param id 节点 id
         * @return /
         */
        @Override
        default int level(Long id) {
            return getById(id).getLevel();
        }

        /**
         * 插入新节点
         *
         * @param node 节点
         */
        @Override
        default void saveNode(T node) {
            if (node.getPid() == null) {
                node.setLevel(1);
                node.setPid(null);
                node.setTreeId(IdUtils.nextId());
            } else {
                T parent = getById(node.getPid());
                Assert.notNull(parent, "父节点不存在");
                node.setLevel(parent.getLevel() + 1);
                node.setTreeId(parent.getTreeId());
            }
            save(node);
        }

        /**
         * 删除节点
         *
         * @param id               节点 id
         * @param removeDescendant 是否删除子孙节点
         */
        @Override
        default void removeNode(Long id, boolean removeDescendant) {
            List<Long> descendants = TreeNodeUtils.allIds(descendants(id));
            List<Long> ids = new ArrayList<>(descendants);
            ids.add(id);
            removeByIds(ids);
        }

        /**
         * 移动节点
         *
         * @param id        节点 id
         * @param targetPid 目标父节点 id
         */
        @Override
        @Transactional(rollbackFor = {Exception.class})
        default void moveNode(Long id, Long targetPid) {
            T node = getById(id);
            // 父节点未改变
            if (Objects.equals(node.getPid(), targetPid)) {
                return;
            }
            Long treeId;
            int oldLevel = node.getLevel();
            int newLevel;
            if (node.getPid() == null) {
                // 改为根节点
                treeId = IdUtils.nextId();
                newLevel = 1;
            } else {
                // 修改上级节点
                // 需要移动节点以及所有子节点
                T parent = getById(node.getPid());
                Assert.notNull(parent, "父节点不存在");
//                Assert.isTrue((Objects.equals(parent.getTreeId(), node.getTreeId())
//                        && parent.getLevel() > node.getLevel()), "不能以子孙节点作为自己的父节点");
                newLevel = parent.getLevel() + 1;
                treeId = parent.getTreeId();
            }
            int levelDistance = newLevel - oldLevel;
            Class<T> entityClass = EntityClassCache.entityClass(getClass());
            T nodeEntity = ReflectUtil.newInstance(entityClass);
            nodeEntity.setTreeId(treeId);
            nodeEntity.setLevel(newLevel);
            ColumnCache pidColumnCache = TreeNodeUtils.getColumnCache(entityClass, T::getPid);
            // 修改本身
            update(nodeEntity, Wrappers.lambdaUpdate(entityClass)
                    .eq(T::getId, id)
                    .setSql(targetPid == null, pidColumnCache.getColumnSelect() + " = null")
            );
            // 修改本身之前查出所有子孙节点
            List<Long> descendants = TreeNodeUtils.allIds(descendants(id));
            if (ParamUtils.isNotEmpty(descendants)) {
                T entity = ReflectUtil.newInstance(entityClass);
                entity.setTreeId(treeId);
                Long oldTreeId = node.getTreeId();
                ColumnCache levelColumnCache = TreeNodeUtils.getColumnCache(entityClass, T::getLevel);
                String level = levelColumnCache.getColumnSelect();
                // 修改所有子孙节点
                update(entity, Wrappers.lambdaUpdate(entityClass)
                        .eq(T::getTreeId, oldTreeId)
                        .gt(T::getLevel, oldLevel)
                        .in(T::getId, descendants)
                        .setSql(levelDistance > 0, level + " = " + level + " + " + levelDistance)
                        .setSql(levelDistance < 0, level + " = " + level + " - " + Math.abs(levelDistance))
                );
            }
        }
    }
}
