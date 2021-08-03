package com.fanxuankai.commons.extra.mybatis.tree;

import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.ColumnCache;
import com.fanxuankai.commons.util.Node;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 默认树的通用 DAO
 *
 * @param <T> 实体类泛型
 * @author fanxuankai
 */
public interface DefaultTreeDao<T extends DefaultEntity> extends TreeDao<T> {
    // Query Operations

    /**
     * 祖先(ancestor)节点：A是所有节点的祖先，F是K与L的祖先。
     *
     * @param id 节点 id
     * @return /
     */
    @Override
    default List<T> ancestors(Long id) {
        Long pid = getById(id).getPid();
        if (pid == null) {
            return Collections.emptyList();
        }
        List<T> list = new ArrayList<>();
        while (pid != null) {
            T parent = getById(pid);
            if (parent == null) {
                break;
            }
            list.add(parent);
            pid = parent.getPid();
        }
        return list;
    }

    /**
     * 子孙(descendant)节点：所有节点是A的子孙，K与L是F的子孙。
     *
     * @param id 节点 id
     * @return /
     */
    @Override
    default List<Node<T>> descendants(Long id) {
        return children(id)
                .stream()
                .map(o -> new Node<>(o, descendants(o.getId())))
                .collect(Collectors.toList());
    }

    /**
     * 子孙(descendant)节点 id 列表
     *
     * @param id 节点 id
     * @return /
     */
    default List<Long> descendantIds(Long id) {
        return TreeNodeUtils.allIds(descendants(id));
    }

    /**
     * 父节点(parent node)：B直接连到E与F且只差一个阶度，则B为E与F的父节点
     *
     * @param id 节点 id
     * @return /
     */
    @Override
    default T parent(Long id) {
        Long pid = getById(id).getPid();
        if (pid == null) {
            return null;
        }
        return getById(pid);
    }

    /**
     * 子节点(children node)：B直接连到E与F且只差一个阶度，则E与F为B的子节点。
     *
     * @param id 节点 id
     * @return /
     */
    @Override
    default List<T> children(Long id) {
        return list(Wrappers.lambdaQuery(EntityClassCache.<T>entityClass(getClass())).eq(T::getPid, id));
    }

    /**
     * 是否有子节点，key: 节点 id value: 是否有子节点
     *
     * @param ids 节点列表
     * @return Map
     */
    default Map<Long, Boolean> hasChildren(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyMap();
        }
        List<Long> hasChildrenIds = list(Wrappers.lambdaQuery(EntityClassCache.<T>entityClass(getClass()))
                .in(T::getPid, ids))
                .stream()
                .map(T::getPid)
                .distinct()
                .collect(Collectors.toList());
        return ids.stream().collect(Collectors.toMap(Function.identity(), hasChildrenIds::contains));
    }

    /**
     * 兄弟节点(sibling node)：拥有同一父节点的子节点。如：E与F。
     *
     * @param id 节点 id
     * @return /
     */
    @Override
    default List<T> sibling(Long id) {
        T node = getById(id);
        return list(Wrappers.lambdaQuery(EntityClassCache.<T>entityClass(getClass()))
                .ne(T::getId, id)
                .eq(T::getPid, node.getPid()));
    }

    /**
     * 叶节点(leaf node)或终点节点(terminal node)：没有子节点的节点。如：J、K等。
     *
     * @param wrapper /
     * @return /
     */
    @Override
    default List<T> leaf(LambdaQueryWrapper<T> wrapper) {
        return list(wrapper)
                .stream()
                .filter(o -> children(o.getId()).isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * 非叶节点(non-leaf node)或非终点节点(non-terminal node)：有子节点的节点。 如：A、B、F等等。
     *
     * @param wrapper /
     * @return /
     */
    @Override
    default List<T> nonLeaf(LambdaQueryWrapper<T> wrapper) {
        return list(wrapper)
                .stream()
                .filter(o -> !children(o.getId()).isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * 根节点(root node)：没有父节点的节点，为树的源头。 如：A。
     *
     * @param wrapper /
     * @return /
     */
    @Override
    default List<T> roots(LambdaQueryWrapper<T> wrapper) {
        return list(wrapper.isNull(T::getPid));
    }

    /**
     * 删除节点
     *
     * @param id               节点 id
     * @param removeDescendant 是否删除子孙节点
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    default void removeNode(Long id, boolean removeDescendant) {
        if (removeDescendant) {
            List<Long> descendants = TreeNodeUtils.allIds(descendants(id));
            List<Long> ids = new ArrayList<>(descendants);
            ids.add(id);
            removeByIds(ids);
        } else {
            removeById(id);
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
    default void moveNode(Long id, Long targetPid) {
        Class<T> entityClass = EntityClassCache.entityClass(getClass());
        T entity = ReflectUtil.newInstance(entityClass);
        entity.setId(id);
        entity.setPid(targetPid);
        updateById(entity);
    }
}