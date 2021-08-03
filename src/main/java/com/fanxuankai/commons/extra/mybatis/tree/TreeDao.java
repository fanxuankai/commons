package com.fanxuankai.commons.extra.mybatis.tree;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fanxuankai.commons.util.Node;

import java.util.List;

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
public interface TreeDao<T> extends IService<T> {
    // Query Operations

    /**
     * 祖先(ancestor)节点：A是所有节点的祖先，F是K与L的祖先。
     *
     * @param id 节点 id
     * @return /
     */
    List<T> ancestors(Long id);

    /**
     * 加载树
     *
     * @param id 节点
     * @return /
     */
    default Node<T> tree(Long id) {
        T node = getById(id);
        List<Node<T>> descendants = descendants(id);
        return new Node<>(node, descendants);
    }

    /**
     * 子孙(descendant)节点：所有节点是A的子孙，K与L是F的子孙。
     *
     * @param id 节点 id
     * @return /
     */
    List<Node<T>> descendants(Long id);

    /**
     * 父节点(parent node)：B直接连到E与F且只差一个阶度，则B为E与F的父节点
     *
     * @param id 节点 id
     * @return /
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
    List<T> sibling(Long id);

    /**
     * 叶节点(leaf node)或终点节点(terminal node)：没有子节点的节点。如：J、K等。
     *
     * @param wrapper /
     * @return /
     */
    List<T> leaf(LambdaQueryWrapper<T> wrapper);

    /**
     * 非叶节点(non-leaf node)或非终点节点(non-terminal node)：有子节点的节点。 如：A、B、F等等。
     *
     * @param wrapper /
     * @return /
     */
    List<T> nonLeaf(LambdaQueryWrapper<T> wrapper);

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
        return TreeNodeUtils.calcHeight(tree(id));
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
    default void saveNode(T node) {
        save(node);
    }

    /**
     * 删除节点
     *
     * @param id               节点 id
     * @param removeDescendant 是否删除子孙节点
     */
    void removeNode(Long id, boolean removeDescendant);

    /**
     * 修改节点
     *
     * @param node 节点
     */
    default void updateNode(T node) {
        updateById(node);
    }

    /**
     * 修改节点
     *
     * @param node          节点
     * @param updateWrapper /
     */
    default void updateNode(T node, Wrapper<T> updateWrapper) {
        update(node, updateWrapper);
    }

    /**
     * 移动节点
     *
     * @param id        节点 id
     * @param targetPid 目标父节点 id
     */
    void moveNode(Long id, Long targetPid);
}