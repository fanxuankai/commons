package com.fanxuankai.commons.util;

import java.util.List;

/**
 * 树节点
 *
 * @author fanxuankai
 */
public class Node<E> {
    /**
     * 节点
     */
    private final E item;
    /**
     * 子孙
     */
    private final List<Node<E>> children;

    public Node(E item, List<Node<E>> children) {
        this.item = item;
        this.children = children;
    }

    public E getItem() {
        return item;
    }

    public List<Node<E>> getChildren() {
        return children;
    }
}
