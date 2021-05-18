package com.fanxuankai.commons.extra.mybatis.tree;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.text.StrPool;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
public class ClosureTableUtils {
    /**
     * 构建子孙对象
     *
     * @param id          节点
     * @param descendants 子孙
     * @param <T>         节点类型
     * @return /
     */
    public static <T extends ClosureTable.Entity> List<Descendant<T>> buildDescendants(Long id, List<T> descendants) {
        if (CollectionUtil.isEmpty(descendants)) {
            return Collections.emptyList();
        }
        Map<String, List<T>> groupedByPid =
                descendants.stream().collect(Collectors.groupingBy(o -> getParentIdentify(o.getAncestor(),
                        o.getDepth())));
        return buildDescendants(id, 1, 2, groupedByPid);
    }

    /**
     * 构建子孙对象
     *
     * @param ancestor     父节点
     * @param groupedByPid key: 父节点 value: 子节点列表
     * @param <T>          节点类型
     * @return /
     */
    private static <T extends ClosureTable.Entity> List<Descendant<T>> buildDescendants(Long ancestor,
                                                                                        int depth,
                                                                                        int level,
                                                                                        Map<String, List<T>> groupedByPid) {
        List<T> children = groupedByPid.get(getParentIdentify(ancestor, depth));
        if (CollectionUtil.isEmpty(children)) {
            return Collections.emptyList();
        }
        return children.stream()
                .map(o -> new Descendant<>(o, buildDescendants(ancestor, depth + 1, level + 1, groupedByPid), level))
                .collect(Collectors.toList());
    }

    /**
     * 获取父节点标识
     *
     * @param ancestor 祖先
     * @param depth    深度
     * @return /
     */
    public static String getParentIdentify(Long ancestor, int depth) {
        return ancestor + StrPool.UNDERLINE + depth;
    }
}
