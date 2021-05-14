package com.fanxuankai.commons.extra.mybatis.tree;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fanxuankai.commons.extra.mybatis.base.BaseDao;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @author fanxuankai
 */
public interface TreeDao<T extends TreeNode, C> extends BaseDao<T, C> {
    int ROOT_LEVEL = 1;

    // 查询

    /**
     * 祖先
     *
     * @param id 节点 id
     * @return Ancestor
     */
    Ancestor<T> ancestor(Long id);

    /**
     * 子孙
     *
     * @param id 节点 id
     * @return /
     */
    List<Descendant<T>> descendants(Long id);

    /**
     * 父节点
     *
     * @param id 节点 id
     * @return T
     */
    default T parent(Long id) {
        T node = getById(id);
        if (node.getPid() == null) {
            return null;
        }
        return getById(node.getPid());
    }

    /**
     * 子节点
     *
     * @param id 节点 id
     * @return /
     */
    default List<T> children(Long id) {
        return list(Wrappers.lambdaQuery(entityClass()).eq(T::getPid, id));
    }

    /**
     * 兄弟节点 拥有同一父节点的子节点
     *
     * @param id 节点 id
     * @return /
     */
    List<T> sibling(Long id);

    /**
     * 叶节点 没有子节点的节点
     *
     * @param id 节点 id
     * @return /
     */
    List<T> leaf(Long id);

    /**
     * 非叶节点 有子节点的节点
     *
     * @param id 节点 id
     * @return /
     */
    List<T> nonLeaf(Long id);

    /**
     * 根节点 没有父节点的节点
     *
     * @param id 节点 id
     * @return /
     */
    T root(Long id);

    /**
     * 分支度 指一个节点有几个子节点
     *
     * @param id 节点 id
     * @return /
     */
    default int degree(Long id) {
        return children(id).size();
    }

    /**
     * 阶度 为树中的第几代，而根节点为第一代，阶度为1
     *
     * @param id 节点 id
     * @return /
     */
    default int level(Long id) {
        return getById(id).getLevel();
    }

    /**
     * 高度 指一节点往下走到叶节点的最长路径
     *
     * @param id 节点 id
     * @return /
     */
    default int height(Long id) {
        T node = getById(id);
        List<Descendant<T>> descendants = descendants(id);
        Optional<T> max = TreeUtils.flat(descendants)
                .stream()
                .max(Comparator.comparing(T::getLevel));
        return max.map(t -> t.getLevel() - node.getLevel()).orElse(0);
    }

    /**
     * 深度 指从根节点到某一节点的最长路径
     *
     * @param id 节点 id
     * @return /
     */
    default int depth(Long id) {
        return getById(id).getLevel() - 1;
    }

    /**
     * 根节点列表
     *
     * @param wrapper wrapper
     * @return /
     */
    default List<T> roots(LambdaQueryWrapper<T> wrapper) {
        if (wrapper == null) {
            return list(Wrappers.lambdaQuery(entityClass()).eq(T::getLevel, ROOT_LEVEL));
        } else {
            return list(wrapper.eq(T::getLevel, ROOT_LEVEL));
        }
    }

    // 增删改

    /**
     * 插入新节点
     *
     * @param node 节点
     */
    void insertNode(T node);

    /**
     * 删除节点
     *
     * @param id 节点 id
     */
    void removeNode(Long id);

    /**
     * 移动节点
     *
     * @param id        节点 id
     * @param targetPid 目标父节点 id
     */
    void moveNode(Long id, Long targetPid);
}