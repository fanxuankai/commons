package com.fanxuankai.commons.extra.mybatis.tree;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.ColumnCache;
import com.fanxuankai.commons.util.Node;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
     * 父节点(parent node)：B直接连到E与F且只差一个阶度，则B为E与F的父节点
     *
     * @param id 节点 id
     * @return /
     */
    @Override
    default T parent(Long id) {
        T node = getById(id);
        return getById(node.getPid());
    }

    /**
     * 子节点(children node)：B直接连到E与F且只差一个阶度，则E与F为B的子节点。
     *
     * @param id 节点 id
     * @return /
     */
    @Override
    default List<T> children(Long id) {
        return list(Wrappers.lambdaQuery(entityClass()).eq(T::getPid, id));
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
        return list(Wrappers.lambdaQuery(entityClass())
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

    // Modification Operations

    /**
     * 删除节点
     *
     * @param id               节点 id
     * @param removeDescendant 是否删除子孙节点
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    default void removeNode(Long id, boolean removeDescendant) {
        removeById(id);
        if (removeDescendant) {
            children(id).forEach(o -> removeNode(o.getId(), true));
        } else {
            Class<T> entityClass = entityClass();
            ColumnCache pidColumnCache = TreeUtils.getColumnCache(entityClass, T::getPid);
            update(Wrappers.lambdaUpdate(entityClass).eq(T::getPid, id)
                    .setSql(pidColumnCache.getColumnSelect() + " = null"));
        }
    }
}