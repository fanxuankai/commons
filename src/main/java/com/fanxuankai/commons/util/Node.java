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
     * 子节点
     */
    private List<Node<E>> children;
    /**
     * 是否有子节点
     */
    private Boolean hasChildren;

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

    public void setChildren(List<Node<E>> children) {
        this.children = children;
    }

    public Boolean getHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(Boolean hasChildren) {
        this.hasChildren = hasChildren;
    }
}
