package com.fanxuankai.commons.extra.mybatis.tree;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fanxuankai.commons.util.Node;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/*
https://baike.baidu.com/item/%E6%A0%91%E7%8A%B6%E7%BB%93%E6%9E%84

                        阶度     高度    深度
          A             - 1     - 3     - 0
         /|\
       /  |  \
     /    |    \
    B     C     D       - 2     - 2     - 1
   / \    |    / \
  E   F   G   H   I     - 3     - 1     - 2
 /   / \     / \
J   K   L   M   N       - 4     - 0     - 3

根节点: A
叶节点: G I J K L M N
*/

/**
 * 树的通用 DAO
 *
 * @param <T> 实体类泛型
 * @author fanxuankai
 */
public interface IdentifyTreeDao<T extends BaseEntity> extends TreeDao<T> {
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
     * 兄弟节点(sibling node)：拥有同一父节点的子节点。如：E与F。
     *
     * @param id 节点 id
     * @return /
     */
    @Override
    default List<T> sibling(Long id) {
        T parent = parent(id);
        if (parent == null) {
            return Collections.emptyList();
        }
        List<T> list = children(parent.getId());
        list.removeIf(o -> Objects.equals(o.getId(), id));
        return list;
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

    // Modification Operations

    /**
     * 删除节点
     *
     * @param id               节点 id
     * @param removeDescendant 是否删除子孙节点
     */
    @Override
    default void removeNode(Long id, boolean removeDescendant) {
        removeById(id);
        if (removeDescendant) {
            children(id).forEach(o -> removeNode(o.getId(), true));
        }
    }
}