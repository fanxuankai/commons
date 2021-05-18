package com.fanxuankai.commons.extra.mybatis.tree;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.text.StrPool;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
public class PathEnumerationsUtils {
    /**
     * 构建子孙对象
     *
     * @param node        节点
     * @param descendants 子孙
     * @param <T>         节点类型
     * @return /
     */
    public static <T extends PathEnumerations.Entity> List<Descendant<T>> buildDescendants(T node, List<T> descendants) {
        if (CollectionUtil.isEmpty(descendants)) {
            return Collections.emptyList();
        }
        Map<String, List<T>> groupedByPid =
                descendants.stream().collect(Collectors.groupingBy(PathEnumerationsUtils::getParentPath));
        return buildDescendants(node.getPath(), 2, groupedByPid);
    }

    /**
     * 构建子孙对象
     *
     * @param parentPath   父节点路径
     * @param groupedByPid key: 父节点 value: 子节点列表
     * @param <T>          节点类型
     * @return /
     */
    private static <T extends PathEnumerations.Entity> List<Descendant<T>> buildDescendants(String parentPath,
                                                                                            int level,
                                                                                            Map<String, List<T>> groupedByPid) {
        List<T> children = groupedByPid.get(parentPath);
        if (CollectionUtil.isEmpty(children)) {
            return Collections.emptyList();
        }
        return children.stream()
                .map(o -> new Descendant<>(o, buildDescendants(o.getPath(),
                        level + 1, groupedByPid), level))
                .collect(Collectors.toList());
    }

    /**
     * 获取父节点路径，如果为根节点返回 null
     *
     * @param node 节点
     * @param <T>  节点类型
     * @return /
     */
    public static <T extends PathEnumerations.Entity> String getParentPath(T node) {
        String path = StrPool.SLASH + node.getCode();
        if (Objects.equals(node.getPath(), path)) {
            return null;
        }
        int i = node.getPath().lastIndexOf(path);
        return node.getPath().substring(0, i);
    }

    /**
     * 更新子孙的路径
     *
     * @param parentPath  父节点路径
     * @param descendants 子孙
     * @param <T>         节点类型
     * @return key: id value: 路径
     */
    public static <T extends PathEnumerations.Entity> Map<Long, String> updatePath(String parentPath,
                                                                                   List<Descendant<T>> descendants) {
        Map<Long, String> map = new HashMap<>(16);
        for (Descendant<T> descendant : descendants) {
            T item = descendant.getItem();
            map.put(item.getId(), parentPath + StrPool.SLASH + item.getCode());
            map.putAll(updatePath(item.getPath(), descendant.getDescendants()));
        }
        return map;
    }
}
