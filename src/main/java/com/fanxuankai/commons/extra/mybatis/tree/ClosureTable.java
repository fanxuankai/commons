package com.fanxuankai.commons.extra.mybatis.tree;

import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fanxuankai.commons.util.Node;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 闭包表
 *
 * @author fanxuankai
 */
public class ClosureTable {
    public interface Entity {
        /**
         * 祖先
         *
         * @return /
         */
        Long getAncestor();

        /**
         * 祖先
         *
         * @param ancestor /
         */
        void setAncestor(Long ancestor);

        /**
         * 后代
         *
         * @return /
         */
        Long getDescendant();

        /**
         * 后代
         *
         * @param descendant /
         */
        void setDescendant(Long descendant);

        /**
         * 后代与祖先的距离
         *
         * @return /
         */
        Integer getDistance();

        /**
         * (non-Javadoc)
         *
         * @param distance 后代与祖先的距离
         */
        void setDistance(Integer distance);
    }

    public interface Dao<T extends Entity> extends TreeDao<T> {
        /**
         * 祖先(ancestor)节点：A是所有节点的祖先，F是K与L的祖先。
         *
         * @param id 节点 id
         * @return /
         */
        @Override
        default List<T> ancestors(Long id) {
            List<T> list = list(Wrappers.lambdaQuery(entityClass())
                    .ne(T::getAncestor, id)
                    .eq(T::getDescendant, id));
            list.sort(Comparator.comparing(Entity::getDistance));
            return list;
        }

        /**
         * 加载树
         *
         * @param id 节点
         * @return /
         */
        @Override
        default Node<T> tree(Long id) {
            T node = getOne(Wrappers.lambdaQuery(entityClass())
                    .eq(T::getAncestor, id)
                    .eq(T::getDescendant, id)
                    .eq(T::getDistance, 0));
            List<Node<T>> descendants = descendants(id);
            return new Node<>(node, descendants);
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
                    .map(o -> new Node<>(o, descendants(o.getDescendant())))
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
            return getOne(Wrappers.lambdaQuery(entityClass())
                    .eq(T::getDescendant, id)
                    .eq(T::getDistance, 1), false);
        }

        /**
         * 子节点(children node)：B直接连到E与F且只差一个阶度，则E与F为B的子节点。
         *
         * @param id 节点 id
         * @return /
         */
        @Override
        default List<T> children(Long id) {
            return list(Wrappers.lambdaQuery(entityClass())
                    .eq(T::getAncestor, id)
                    .eq(T::getDistance, 1));
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
            List<T> list = children(parent.getAncestor());
            list.removeIf(o -> Objects.equals(o.getDescendant(), id));
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
            return list(wrapper.eq(T::getDistance, 0))
                    .stream()
                    .filter(o -> children(o.getDescendant()).isEmpty())
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
            return list(wrapper.eq(T::getDistance, 0))
                    .stream()
                    .filter(o -> !children(o.getDescendant()).isEmpty())
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
            return list(wrapper.isNull(T::getAncestor));
        }

        /**
         * 插入新节点
         *
         * @param node 节点
         * @param pid  父节点
         */
        @Override
        default void insertNode(T node, Long pid) {
            node.setAncestor(pid);
            if (node.getDescendant() == null) {
                throw new NullPointerException("descendant 不能为空");
            }
            if (Objects.equals(node.getAncestor(), node.getDescendant())) {
                throw new IllegalArgumentException("祖先节点与后代节点不能相同");
            }
            List<T> nodes = new ArrayList<>();
            nodes.add(node);
            if (node.getAncestor() == null) {
                T root = ReflectUtil.newInstance(entityClass());
                root.setDescendant(node.getDescendant());
                root.setDistance(-1);
                nodes.add(root);

                node.setAncestor(node.getDescendant());
                node.setDistance(0);
            } else {
                node.setDistance(1);
                T selfLink = ReflectUtil.newInstance(entityClass());
                selfLink.setAncestor(node.getDescendant());
                selfLink.setDescendant(node.getDescendant());
                selfLink.setDistance(0);
                nodes.add(selfLink);
                List<T> ancestors = ancestors(node.getAncestor());
                ancestors.forEach(o -> {
                    o.setDescendant(node.getDescendant());
                    o.setDistance(o.getDistance() + 1);
                });
                nodes.addAll(ancestors);
            }
            saveBatch(nodes);
        }

        /**
         * 移动节点
         *
         * @param id        节点 id
         * @param targetPid 目标父节点 id
         */
        @Override
        @Transactional(rollbackFor = Exception.class)
        default void moveNode(Long id, Long targetPid) {
            // 移除与旧祖先的关联关系
            remove(Wrappers.lambdaQuery(entityClass())
                    .gt(T::getDistance, 0)
                    .eq(T::getDescendant, id));

            // 复制父节点的祖先关联关系
            List<T> ancestors = ancestors(targetPid);
            ancestors.forEach(o -> {
                o.setDescendant(id);
                o.setDistance(o.getDistance() + 1);
            });

            // 创建与新祖先的关联关系
            T node = ReflectUtil.newInstance(entityClass());
            node.setAncestor(targetPid);
            node.setDescendant(id);
            node.setDistance(1);
            ancestors.add(node);
            saveBatch(ancestors);

            // 子节点跟随移动触发更新与祖先的关联关系
            children(id).forEach(o -> moveNode(o.getDescendant(), o.getAncestor()));
        }

        /**
         * 删除节点
         *
         * @param id               节点 id
         * @param removeDescendant 是否删除子孙节点
         */
        @Override
        @Transactional(rollbackFor = Exception.class)
        default void removeNode(Long id, boolean removeDescendant) {
            // 移除与祖先的关联关系
            remove(Wrappers.lambdaQuery(entityClass())
                    .eq(T::getDescendant, id));
            if (removeDescendant) {
                // 删除子节点
                children(id).forEach(o -> removeNode(o.getDescendant(), true));
            }
            // 移除与后代的关联关系
            remove(Wrappers.lambdaQuery(entityClass()).eq(T::getAncestor, id));
        }
    }
}