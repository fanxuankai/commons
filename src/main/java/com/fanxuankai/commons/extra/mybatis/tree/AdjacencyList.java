package com.fanxuankai.commons.extra.mybatis.tree;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fanxuankai.commons.util.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
public class AdjacencyList {
    public interface Entity extends BaseEntity {
        /**
         * 父节点 id
         *
         * @return 如果没有父节点可以返回空
         */
        Long getPid();
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
         * 父节点(parent node)：B直接连到E与F且只差一个阶度，则B为E与F的父节点
         *
         * @param id 节点 id
         * @return T
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
            return list(Wrappers.lambdaQuery(entityClass())
                    .eq(T::getPid, id));
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
    }
}