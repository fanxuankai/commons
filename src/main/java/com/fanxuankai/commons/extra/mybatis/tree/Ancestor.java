package com.fanxuankai.commons.extra.mybatis.tree;

/**
 * 祖先
 *
 * @author fanxuankai
 */
public class Ancestor<E> {
    private final E item;
    private final Ancestor<E> parent;

    public Ancestor(E item, Ancestor<E> parent) {
        this.item = item;
        this.parent = parent;
    }

    public E getItem() {
        return item;
    }

    public Ancestor<E> getParent() {
        return parent;
    }
}
