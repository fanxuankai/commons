package com.fanxuankai.commons.extra.mybatis.tree;

/**
 * @author fanxuankai
 */
public interface DefaultEntity {
    /**
     * get 主键
     *
     * @return /
     */
    Long getId();

    /**
     * 父节点
     *
     * @return 如果没有父节点返回空
     */
    Long getPid();

    /**
     * (non-Javadoc)
     *
     * @param pid 父节点
     */
    void setPid(Long pid);
}
