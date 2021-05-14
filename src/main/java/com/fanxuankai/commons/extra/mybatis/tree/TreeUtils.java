package com.fanxuankai.commons.extra.mybatis.tree;

import cn.hutool.core.collection.CollectionUtil;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
public class TreeUtils {
    /**
     * 构建祖先对象
     *
     * @param pid       父节点 id
     * @param ancestors 祖先
     * @param <T>       节点类型
     * @return /
     */
    public static <T extends TreeNode> Ancestor<T> buildAncestor(Long pid, List<T> ancestors) {
        if (CollectionUtil.isEmpty(ancestors)) {
            return null;
        }
        Map<Long, T> map = ancestors.stream().collect(Collectors.toMap(T::getId, Function.identity()));
        return buildAncestor(pid, map);
    }

    /**
     * 构建祖先对象
     *
     * @param pid 父节点 id
     * @param map key: id value: 节点
     * @param <T> 节点类型
     * @return /
     */
    private static <T extends TreeNode> Ancestor<T> buildAncestor(Long pid, Map<Long, T> map) {
        T parent = map.get(pid);
        if (parent == null) {
            return null;
        }
        return new Ancestor<>(parent, buildAncestor(parent.getPid(), map));
    }

    /**
     * 构建子孙对象
     *
     * @param id          节点 id
     * @param descendants 子孙
     * @param <T>         节点类型
     * @return /
     */
    public static <T extends TreeNode> List<Descendant<T>> buildDescendants(Long id, List<T> descendants) {
        if (CollectionUtil.isEmpty(descendants)) {
            return Collections.emptyList();
        }
        Map<Long, List<T>> groupedByPid = descendants.stream().collect(Collectors.groupingBy(T::getPid));
        return buildDescendants(id, descendants, groupedByPid);
    }

    /**
     * 获取所有子孙 id
     *
     * @param descendants 子孙
     * @param <T>         节点类型
     * @return /
     */
    public static <T extends TreeNode> List<Long> allIds(List<Descendant<T>> descendants) {
        return flat(descendants).stream().map(TreeNode::getId).collect(Collectors.toList());
    }

    /**
     * 获取所有子孙
     *
     * @param descendants 子孙
     * @param <T>         节点类型
     * @return /
     */
    public static <T extends TreeNode> List<T> flat(List<Descendant<T>> descendants) {
        if (CollectionUtil.isEmpty(descendants)) {
            return Collections.emptyList();
        }
        return descendants.stream().map(o -> {
            List<T> list = new ArrayList<>();
            list.add(o.getItem());
            list.addAll(flat(o.getDescendants()));
            return list;
        }).flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    /**
     * 构建子孙对象
     *
     * @param id           节点 id
     * @param descendants  子孙
     * @param groupedByPid key: 父节点 value: 子节点列表
     * @param <T>          节点类型
     * @return /
     */
    private static <T extends TreeNode> List<Descendant<T>> buildDescendants(Long id, List<T> descendants,
                                                                             Map<Long, List<T>> groupedByPid) {
        if (CollectionUtil.isEmpty(descendants)) {
            return Collections.emptyList();
        }
        List<T> children = groupedByPid.get(id);
        if (CollectionUtil.isEmpty(children)) {
            return Collections.emptyList();
        }
        return children.stream()
                .map(t -> new Descendant<T>(t, buildDescendants(t.getId(), groupedByPid.get(t.getId()), groupedByPid)))
                .collect(Collectors.toList());
    }
}
