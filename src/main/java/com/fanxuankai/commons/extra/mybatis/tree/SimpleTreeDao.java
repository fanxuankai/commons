package com.fanxuankai.commons.extra.mybatis.tree;

import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public class SimpleTreeDao<T extends SimpleTreeNode, M extends BaseMapper<T>, C>
        extends AbstractTreeDao<T, M, C>
        implements TreeDao<T, C> {
    @Override
    public Ancestor<T> ancestor(Long id) {
        T node = getById(id);
        List<T> nodes = list(Wrappers.lambdaQuery(entityClass)
                .eq(T::getTreeId, node.getTreeId())
                .lt(T::getLevel, node.getLevel()));
        return TreeUtils.buildAncestor(node.getPid(), nodes);
    }

    @Override
    public List<Descendant<T>> descendants(Long id) {
        T node = getById(id);
        List<T> nodes = list(Wrappers.lambdaQuery(entityClass)
                .eq(T::getTreeId, node.getTreeId())
                .gt(T::getLevel, node.getLevel()));
        return TreeUtils.buildDescendants(id, nodes);
    }

    @Override
    public List<T> sibling(Long id) {
        T node = getById(id);
        return list(Wrappers.lambdaQuery(entityClass)
                .ne(T::getId, id)
                .eq(T::getTreeId, node.getTreeId())
                .eq(T::getLevel, node.getLevel()));
    }

    @Override
    public List<T> leaf(Long id) {
        return leaf(id, true);
    }

    @Override
    public List<T> nonLeaf(Long id) {
        return leaf(id, false);
    }

    @Override
    public T root(Long id) {
        T node = getById(id);
        return getOne(Wrappers.lambdaQuery(entityClass)
                .eq(T::getTreeId, node.getTreeId())
                .eq(T::getLevel, 1), false);
    }

    @Override
    public void insertNode(T node) {
        Long pid = node.getPid();
        int level;
        long treeId;
        if (pid == null) {
            level = 1;
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

    @Override
    public void removeNode(Long id) {
        List<Long> ids = new ArrayList<>(TreeUtils.allIds(descendants(id)));
        ids.add(id);
        removeByIds(ids);
    }

    @Override
    public void moveNode(Long id, Long targetPid) {
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
            newLevel = 1;
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
        T nodeEntity = ReflectUtil.newInstance(entityClass);
        nodeEntity.setTreeId(treeId);
        nodeEntity.setLevel(newLevel);
        nodeEntity.setPid(targetPid);
        // 修改本身之前查出所有子孙节点
        List<Long> allSubNodeIds = TreeUtils.allIds(descendants(id));
        // 修改本身
        update(nodeEntity, Wrappers.lambdaUpdate(entityClass)
                .eq(T::getId, id)
                .setSql(targetPid == null, "pid = null")
        );
        if (!CollectionUtils.isEmpty(allSubNodeIds)) {
            T entity = ReflectUtil.newInstance(entityClass);
            entity.setTreeId(treeId);
            Long oldTreeId = node.getTreeId();
            // 修改所有子孙节点
            update(entity, Wrappers.lambdaUpdate(entityClass)
                    .eq(T::getTreeId, oldTreeId)
                    .gt(T::getLevel, oldLevel)
                    .in(T::getId, allSubNodeIds)
                    .setSql(levelDistance > 0, "level = level + " + levelDistance)
                    .setSql(levelDistance < 0, "level = level - " + Math.abs(levelDistance))
            );
        }
    }

    private List<T> leaf(Long id, boolean leaf) {
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

    private List<T> loadAll(Long treeId) {
        return list(Wrappers.lambdaQuery(entityClass)
                .eq(T::getTreeId, treeId));
    }
}