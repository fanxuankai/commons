package com.fanxuankai.commons.extra.mybatis.tree;

import java.util.List;

/**
 * 子孙
 *
 * @author fanxuankai
 */
public class Descendant<E> {
    /**
     * 节点
     */
    private final E item;
    /**
     * 子孙
     */
    private final List<Descendant<E>> descendants;
    /**
     * 相对阶度
     */
    private final int level;

    public Descendant(E item, List<Descendant<E>> descendants, int level) {
        this.item = item;
        this.descendants = descendants;
        this.level = level;
    }

    public E getItem() {
        return item;
    }

    public List<Descendant<E>> getDescendants() {
        return descendants;
    }

    public int getLevel() {
        return level;
    }
}
