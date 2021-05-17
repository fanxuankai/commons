package com.fanxuankai.commons.extra.mybatis.tree;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/*
https://baike.baidu.com/item/%E6%A0%91%E7%8A%B6%E7%BB%93%E6%9E%84

                        阶度
          A             - 1
         /|\
       /  |  \
     /    |    \
    B     C     D       - 2
   / \    |    / \
  E   F   G   H   I     - 3
 /   / \     / \
J   K   L   M   N       - 4

根节点: A
叶节点: G I J K L M N
*/

/**
 * 树的通用 DAO
 *
 * @param <T> 实体类泛型
 * @author fanxuankai
 */
public interface TreeDao<T extends Entity> extends IService<T> {
    /**
     * 获取实体类类型
     *
     * @return /
     */
    default Class<T> entityClass() {
        return EntityClassCache.entityClass(getClass());
    }

    // Query Operations

    /**
     * 祖先(ancestor)节点：A是所有节点的祖先，F是K与L的祖先。
     *
     * @param id 节点 id
     * @return /
     */
    List<T> ancestors(Long id);

    /**
     * 子孙(descendant)节点：所有节点是A的子孙，K与L是F的子孙。
     *
     * @param id 节点 id
     * @return /
     */
    List<Descendant<T>> descendants(Long id);

    /**
     * 父节点(parent node)：B直接连到E与F且只差一个阶度，则B为E与F的父节点
     *
     * @param id 节点 id
     * @return T
     */
    T parent(Long id);

    /**
     * 子节点(children node)：B直接连到E与F且只差一个阶度，则E与F为B的子节点。
     *
     * @param id 节点 id
     * @return /
     */
    List<T> children(Long id);

    /**
     * 兄弟节点(sibling node)：拥有同一父节点的子节点。如：E与F。
     *
     * @param id 节点 id
     * @return /
     */
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
    List<T> roots(LambdaQueryWrapper<T> wrapper);

    /**
     * 分支度(degree)：指一个节点有几个子节点。 如：A为3、B为2、C为1、M为0。
     *
     * @param id 节点 id
     * @return /
     */
    default int degree(Long id) {
        return children(id).size();
    }

    /**
     * 阶度(level)：为树中的第几代，而根节点为第一代，阶度为1。
     *
     * @param id 节点 id
     * @return /
     */
    default int level(Long id) {
        return ancestors(id).size() + 1;
    }

    /**
     * 高度(height)：指一节点往下走到叶节点的最长路径。 如：A为3、F为1、L为0。
     *
     * @param id 节点 id
     * @return /
     */
    default int height(Long id) {
        return TreeUtils.calcHeight(descendants(id));
    }

    /**
     * 深度(depth)：指从根节点到某一节点的最长路径。如：C为1、M为3。
     *
     * @param id 节点 id
     * @return /
     */
    default int depth(Long id) {
        return level(id) - 1;
    }

    // Modification Operations

    /**
     * 插入新节点
     *
     * @param node 节点
     */
    default void insertNode(T node) {
        save(node);
    }

    /**
     * 删除节点
     *
     * @param id               节点 id
     * @param removeDescendant 是否删除子孙节点
     */
    default void removeNode(Long id, boolean removeDescendant) {
        removeById(id);
        if (removeDescendant) {
            children(id).forEach(o -> removeNode(o.getId(), true));
        }
    }

    /**
     * 移动节点
     *
     * @param id        节点 id
     * @param targetPid 目标父节点 id
     */
    void moveNode(Long id, Long targetPid);
}