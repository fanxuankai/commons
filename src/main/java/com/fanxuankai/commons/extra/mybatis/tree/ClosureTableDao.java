//package com.fanxuankai.commons.extra.mybatis.tree;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.fanxuankai.commons.extra.mybatis.base.BaseDao;
//import com.fanxuankai.commons.util.IdUtils;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * @author fanxuankai
// */
//public interface ClosureTableDao<T extends ClosureTable, C> extends BaseDao<T, C> {
//    // 查询
//
//    /**
//     * 祖先
//     *
//     * @param id 节点 id
//     * @return Ancestor
//     */
//    Ancestor<T> ancestor(Long id);
//
//    /**
//     * 子孙
//     *
//     * @param id 节点 id
//     * @return /
//     */
//    List<Descendant<T>> descendants(Long id);
//
//    /**
//     * 父节点
//     *
//     * @param id 节点 id
//     * @return /
//     */
//    default T parent(Long id) {
//        return getOne(Wrappers.lambdaQuery(entityClass())
//                        .eq(T::getDescendant, id)
//                        .eq(T::getDistance, 1)
//                , false);
//    }
//
//    /**
//     * 子节点
//     *
//     * @param id 节点 id
//     * @return /
//     */
//    default List<T> children(Long id) {
//        return list(Wrappers.lambdaQuery(entityClass())
//                .eq(T::getAncestor, id)
//                .eq(T::getDistance, 1));
//    }
//
//    /**
//     * 兄弟节点 拥有同一父节点的子节点
//     *
//     * @param id 节点 id
//     * @return /
//     */
//    List<T> sibling(Long id);
//
//    /**
//     * 叶节点 没有子节点的节点
//     *
//     * @param id 节点 id
//     * @return /
//     */
//    List<T> leaf(Long id);
//
//    /**
//     * 非叶节点 有子节点的节点
//     *
//     * @param id 节点 id
//     * @return /
//     */
//    List<T> nonLeaf(Long id);
//
//    /**
//     * 根节点 没有父节点的节点
//     *
//     * @param id 节点 id
//     * @return /
//     */
//    T root(Long id);
//
//    /**
//     * 分支度 指一个节点有几个子节点
//     *
//     * @param id 节点 id
//     * @return /
//     */
//    default int degree(Long id) {
//        return children(id).size();
//    }
//
//    /**
//     * 阶度 为树中的第几代，而根节点为第一代，阶度为1
//     *
//     * @param id 节点 id
//     * @return /
//     */
//    default int level(Long id) {
//        return list(Wrappers.lambdaQuery(entityClass())
//                .eq(T::getDescendant, id))
//                .stream()
//                .map(T::getDistance)
//                .max(Integer::compareTo)
//                .map(i -> i + 1)
//                .orElse(0);
//    }
//
//    /**
//     * 高度 指一节点往下走到叶节点的最长路径
//     *
//     * @param id 节点 id
//     * @return /
//     */
//    default int height(Long id) {
//        return list(Wrappers.lambdaQuery(entityClass())
//                .eq(T::getAncestor, id))
//                .stream()
//                .map(T::getDistance)
//                .max(Integer::compareTo)
//                .orElse(-1);
//    }
//
//    /**
//     * 深度 指从根节点到某一节点的最长路径
//     *
//     * @param id 节点 id
//     * @return /
//     */
//    default int depth(Long id) {
//        return level(id) - 1;
//    }
//
//    /**
//     * 根节点列表
//     *
//     * @param wrapper wrapper
//     * @return /
//     */
//    default List<T> roots(LambdaQueryWrapper<T> wrapper) {
//        if (wrapper == null) {
//            wrapper = Wrappers.lambdaQuery(entityClass())
//                    .isNull(T::getAncestor)
//                    .isNotNull(T::getDescendant);
//        }
//        return list(wrapper);
//    }
//
//    // 增删改
//
//    /**
//     * 插入新节点
//     *
//     * @param node 节点
//     */
//    default void insertNode(T node) {
//        if (node.getAncestor() == null) {
//            save(node);
//        } else {
//            List<T> all = list(Wrappers.lambdaQuery(entityClass()).eq(T::getDescendant,
//                    node.getAncestor()))
//                    .stream()
//                    .peek(t -> {
//                        t.setId(IdUtils.nextId());
//                        t.setDescendant(node.getDescendant());
//                        t.setDistance(t.getDistance() + 1);
//                    }).collect(Collectors.toList());
//            all.add(node);
//            saveBatch(all);
//        }
//    }
//
//    /**
//     * 删除节点
//     *
//     * @param id 节点 id
//     */
//    default void removeNode(Long id) {
//        remove(Wrappers.lambdaUpdate(entityClass())
//                .and(w -> w.eq(T::getDescendant, id)
//                        .or()
//                        .eq(T::getAncestor, id)));
//    }
//
//    /**
//     * 移动节点
//     *
//     * @param id        节点 id
//     * @param targetPid 目标父节点 id
//     */
//    void moveNode(Long id, Long targetPid);
//}