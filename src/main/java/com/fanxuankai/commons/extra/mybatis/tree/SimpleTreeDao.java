package com.fanxuankai.commons.extra.mybatis.tree;

import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fanxuankai.commons.util.IdUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
public interface SimpleTreeDao<T extends SimpleTreeNode, C> extends TreeDao<T, C> {
    /**
     * 祖先
     *
     * @param id 节点 id
     * @return Ancestor
     */
    @Override
    default Ancestor<T> ancestor(Long id) {
        T node = getById(id);
        List<T> nodes = list(Wrappers.lambdaQuery(entityClass())
                .eq(T::getTreeId, node.getTreeId())
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
                .eq(T::getTreeId, node.getTreeId())
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
        return list(Wrappers.lambdaQuery(entityClass())
                .ne(T::getId, id)
                .eq(T::getTreeId, node.getTreeId())
                .eq(T::getLevel, node.getLevel()));
    }

    /**
     * 叶节点 没有子节点的节点
     *
     * @param id 节点 id
     * @return /
     */
    @Override
    default List<T> leaf(Long id) {
        return leaf(id, true);
    }

    /**
     * 非叶节点 有子节点的节点
     *
     * @param id 节点 id
     * @return /
     */
    @Override
    default List<T> nonLeaf(Long id) {
        return leaf(id, false);
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
        return getOne(Wrappers.lambdaQuery(entityClass())
                .eq(T::getTreeId, node.getTreeId())
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
        long treeId;
        if (pid == null) {
            level = ROOT_LEVEL;
            treeId = IdUtils.nextId();
        } else {
            T parent = getById(pid);
            level = parent.getLevel() + 1;
            treeId = parent.getTreeId();
        }
        node.setLevel(level);
        node.setTreeId(treeId);
        save(node);
    }

    /**
     * 删除节点
     *
     * @param id 节点 id
     */
    @Override
    default void removeNode(Long id) {
        List<Long> ids = new ArrayList<>(TreeUtils.allIds(descendants(id)));
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
    default void moveNode(Long id, Long targetPid) {
        T node = getById(id);
        // 上级节点未改变
        if (Objects.equals(node.getPid(), targetPid)) {
            return;
        }
        Long treeId;
        int oldLevel = node.getLevel();
        int newLevel;
        if (targetPid == null) {
            // 改为根节点
            treeId = IdUtils.nextId();
            newLevel = ROOT_LEVEL;
        } else {
            // 修改上级节点
            // 需要移动节点以及所有子节点
            T parent = getById(targetPid);
            if (Objects.equals(parent.getTreeId(), node.getTreeId())
                    && parent.getLevel() > node.getLevel()) {
                throw new IllegalArgumentException("不能以子孙节点作为自己的父节点");
            }
            newLevel = parent.getLevel() + 1;
            treeId = parent.getTreeId();
        }
        int levelDistance = newLevel - oldLevel;
        T nodeEntity = ReflectUtil.newInstance(entityClass());
        nodeEntity.setTreeId(treeId);
        nodeEntity.setLevel(newLevel);
        nodeEntity.setPid(targetPid);
        // 修改本身之前查出所有子孙节点
        List<Long> allSubNodeIds = TreeUtils.allIds(descendants(id));
        // 修改本身
        update(nodeEntity, Wrappers.lambdaUpdate(entityClass())
                .eq(T::getId, id)
                .setSql(targetPid == null, "pid = null")
        );
        if (!CollectionUtils.isEmpty(allSubNodeIds)) {
            T entity = ReflectUtil.newInstance(entityClass());
            entity.setTreeId(treeId);
            Long oldTreeId = node.getTreeId();
            // 修改所有子孙节点
            update(entity, Wrappers.lambdaUpdate(entityClass())
                    .eq(T::getTreeId, oldTreeId)
                    .gt(T::getLevel, oldLevel)
                    .in(T::getId, allSubNodeIds)
                    .setSql(levelDistance > 0, "level = level + " + levelDistance)
                    .setSql(levelDistance < 0, "level = level - " + Math.abs(levelDistance))
            );
        }
    }

    /**
     * 叶节点或非叶节点
     *
     * @param id   节点 id
     * @param leaf true: 叶节点 false: 非叶节点
     * @return /
     */
    default List<T> leaf(Long id, boolean leaf) {
        T node = getById(id);
        List<T> all = loadAll(node.getTreeId());
        Set<Long> pidList = all.stream().map(T::getPid).collect(Collectors.toSet());
        return all.stream().filter(t -> {
            boolean contains = pidList.contains(t.getId());
            if (leaf) {
                return !contains;
            }
            return contains;
        }).collect(Collectors.toList());
    }

    /**
     * 加载树
     *
     * @param treeId 树 id
     * @return /
     */
    default List<T> loadAll(Long treeId) {
        return list(Wrappers.lambdaQuery(entityClass()).eq(T::getTreeId, treeId));
    }
}