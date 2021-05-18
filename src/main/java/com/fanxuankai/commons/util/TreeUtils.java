package com.fanxuankai.commons.util;

import cn.hutool.core.collection.CollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
            return 0;
        }
        int max = 0;
        for (int i = 0; i < node.getChildren().size(); i++) {
            max = Math.max(max, calcHeight(node.getChildren().get(i)));
        }
        return max;
    }

    /**
     * 扁平化子孙节点
     *
     * @param nodes 子孙
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
}