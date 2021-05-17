//package com.fanxuankai.commons.extra.mybatis.tree;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//
//import java.util.Comparator;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * 闭包表
// *
// * @author fanxuankai
// */
//public class ClosureTable {
//    public interface Node extends Entity {
//        /**
//         * id
//         *
//         * @param id /
//         */
//        void setId(Long id);
//
//        /**
//         * 祖先
//         *
//         * @return /
//         */
//        Long getAncestor();
//
//        /**
//         * 祖先
//         *
//         * @param ancestor /
//         */
//        void setAncestor(Long ancestor);
//
//        /**
//         * 后代
//         *
//         * @return /
//         */
//        Long getDescendant();
//
//        /**
//         * 后代
//         *
//         * @param descendant /
//         */
//        void setDescendant(Long descendant);
//
//        /**
//         * 距离, 与祖先
//         *
//         * @return /
//         */
//        Integer getDistance();
//
//        /**
//         * 距离
//         *
//         * @param distance /
//         */
//        void setDistance(Integer distance);
//    }
//
//    public interface Dao<T extends ClosureTable.Node> extends TreeDao<T> {
//        /**
//         * 祖先(ancestor)节点：A是所有节点的祖先，F是K与L的祖先。
//         *
//         * @param id 节点 id
//         * @return /
//         */
//        @Override
//        default List<T> ancestors(Long id) {
//            List<T> list = list(Wrappers.lambdaQuery(entityClass())
//                    .ne(T::getAncestor, id)
//                    .eq(T::getDescendant, id));
//            list.sort(Comparator.comparing(Node::getDistance));
//            return list;
//        }
//
//        /**
//         * 子孙(descendant)节点：所有节点是A的子孙，K与L是F的子孙。
//         *
//         * @param id 节点 id
//         * @return /
//         */
//        @Override
//        default List<Descendant<T>> descendants(Long id) {
//            List<T> list = list(Wrappers.lambdaQuery(entityClass())
//                    .eq(T::getAncestor, id)
//                    .ne(T::getDescendant, id));
//            // todo 构建比较复杂
//            return children(id)
//                    .stream()
//                    .map(o -> new Descendant<>(o, descendants(o.getId())))
//                    .collect(Collectors.toList());
//        }
//
//        /**
//         * 父节点(parent node)：B直接连到E与F且只差一个阶度，则B为E与F的父节点
//         *
//         * @param id 节点 id
//         * @return T
//         */
//        @Override
//        default T parent(Long id) {
//            Long pid = getById(id).getPid();
//            if (pid == null) {
//                return null;
//            }
//            return getById(pid);
//        }
//
//        /**
//         * 子节点(children node)：B直接连到E与F且只差一个阶度，则E与F为B的子节点。
//         *
//         * @param id 节点 id
//         * @return /
//         */
//        @Override
//        default List<T> children(Long id) {
//            return list(Wrappers.lambdaQuery(entityClass())
//                    .eq(T::getPid, id));
//        }
//
//        /**
//         * 根节点(root node)：没有父节点的节点，为树的源头。 如：A。
//         *
//         * @param wrapper /
//         * @return /
//         */
//        @Override
//        default List<T> roots(LambdaQueryWrapper<T> wrapper) {
//            return list(wrapper.isNull(T::getPid));
//        }
//    }
//}