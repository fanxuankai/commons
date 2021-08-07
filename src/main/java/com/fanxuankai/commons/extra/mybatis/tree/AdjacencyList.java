package com.fanxuankai.commons.extra.mybatis.tree;

/**
 * @author fanxuankai
 */
public class AdjacencyList {
    public interface Entity extends DefaultEntity {
    }

    public interface Dao<T extends Entity> extends DefaultTreeDao<T> {
    }
}