package com.fanxuankai.commons.extra.mybatis.tree;

/**
 * @author fanxuankai
 */
public interface PathTreeNode extends TreeNode {
    /**
     * 编码
     *
     * @return /
     */
    String getCode();

    /**
     * 路径
     *
     * @return /
     */
    String getPath();

    /**
     * set 路径
     *
     * @param path 路径
     */
    void setPath(String path);
}
