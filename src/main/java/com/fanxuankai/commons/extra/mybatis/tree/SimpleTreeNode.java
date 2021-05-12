package com.fanxuankai.commons.extra.mybatis.tree;

/**
 * @author fanxuankai
 */
public interface SimpleTreeNode extends TreeNode {
    /**
     * 树 id
     *
     * @return /
     */
    Long getTreeId();

    /**
     * set 树 id
     *
     * @param treeId 树 id
     */
    void setTreeId(Long treeId);
}
