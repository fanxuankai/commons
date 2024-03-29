package com.fanxuankai.commons.util;

import cn.hutool.core.collection.CollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 树工具类
 *
 * @author fanxuankai
 */
public class TreeUtils {
    /**
     * 计算高度
     *
     * @param node 节点
     * @param <T>  节点类型
     * @return /
     */
    public static <T> int calcHeight(Node<T> node) {
        if (node == null) {
            return -1;
        }
        if (node.getChildren().isEmpty()) {
            return 0;
        }
        int max = 0;
        for (int i = 0; i < node.getChildren().size(); i++) {
            max = Math.max(max, calcHeight(node.getChildren().get(i)));
        }
        return max + 1;
    }

    /**
     * 扁平化树节点
     *
     * @param nodes 树节点
     * @param <T>   实体类类型
     * @return /
     */
    public static <T> List<T> flat(List<Node<T>> nodes) {
        if (CollectionUtil.isEmpty(nodes)) {
            return Collections.emptyList();
        }
        return nodes.stream().map(o -> {
            List<T> list = new ArrayList<>();
            list.add(o.getItem());
            list.addAll(flat(o.getChildren()));
            return list;
        }).flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    /**
     * item 映射
     *
     * @param node   树节点
     * @param mapper 映射函数
     * @param <T>    映射前
     * @param <R>    映射后
     * @return 映射后的树节点
     */
    public static <T, R> Node<R> mapItem(Node<T> node, Function<T, R> mapper) {
        Node<R> rNode = new Node<>(mapper.apply(node.getItem()),
                OptionalUtils.ofNullable(node.getChildren())
                        .map(nodes -> nodes.stream()
                                .map(o -> mapItem(o, mapper))
                                .collect(Collectors.toList()))
                        .orElse(Collections.emptyList()));
        rNode.setHasChildren(node.getHasChildren());
        return rNode;
    }
}