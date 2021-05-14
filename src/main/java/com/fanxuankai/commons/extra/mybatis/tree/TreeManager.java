package com.fanxuankai.commons.extra.mybatis.tree;

import cn.hutool.core.text.StrPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
class TreeManager {
    /**
     * 叶节点或非叶节点
     *
     * @param id   节点 id
     * @param leaf true: 叶节点 false: 非叶节点
     * @return /
     */
    public static <T extends TreeNode> List<T> leaf(List<T> all, boolean leaf) {
        Set<Long> pidList = all.stream().map(T::getPid).collect(Collectors.toSet());
        return all.stream().filter(t -> {
            boolean contains = pidList.contains(t.getId());
            if (leaf) {
                return !contains;
            }
            return contains;
        }).collect(Collectors.toList());
    }

    /**
     * 更新子孙的路径
     *
     * @param parentPath  父节点路径
     * @param descendants 子孙
     * @param <T>         节点类型
     * @return key: id value: 路径
     */
    public static <T extends PathTreeNode> Map<Long, String> updatePath(String parentPath,
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
