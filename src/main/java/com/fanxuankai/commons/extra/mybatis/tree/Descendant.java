package com.fanxuankai.commons.extra.mybatis.tree;

import java.util.List;

/**
 * 子孙
 *
 * @author fanxuankai
 */
public class Descendant<E> {
    private final E item;
    private final List<Descendant<E>> descendants;

    public Descendant(E item, List<Descendant<E>> descendants) {
        this.item = item;
        this.descendants = descendants;
    }

    public E getItem() {
        return item;
    }

    public List<Descendant<E>> getDescendants() {
        return descendants;
    }
}
