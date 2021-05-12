package com.fanxuankai.commons.extra.mybatis.tree;

/**
 * 树节点
 *
 * @author fanxuankai
 */
public interface TreeNode {
    /**
     * id
     *
     * @return /
     */
    Long getId();

    /**
     * set id
     *
     * @param id id
     */
    void setId(Long id);

    /**
     * 父节点 id
     *
     * @return 如果没有父节点可以返回空
     */
    Long getPid();

    /**
     * set 父节点 id
     *
     * @param pid 父节点 id
     */
    void setPid(Long pid);

    /**
     * 阶度
     *
     * @return 从 1 开始
     */
    Integer getLevel();

    /**
     * set 阶度
     *
     * @param level 阶度, 从 1 开始
     */
    void setLevel(Integer level);
}