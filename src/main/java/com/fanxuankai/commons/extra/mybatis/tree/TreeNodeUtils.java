package com.fanxuankai.commons.extra.mybatis.tree;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.support.ColumnCache;
import com.baomidou.mybatisplus.core.toolkit.support.LambdaMeta;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.fanxuankai.commons.util.Node;
import com.fanxuankai.commons.util.TreeUtils;
import org.apache.ibatis.reflection.property.PropertyNamer;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
class TreeNodeUtils extends TreeUtils {
    public static <T> ColumnCache getColumnCache(Class<T> entityClass, SFunction<T, ?> function) {
        Map<String, ColumnCache> columnMap = LambdaUtils.getColumnMap(entityClass);
        LambdaMeta pathResolve = LambdaUtils.extract(function);
        return columnMap.get(LambdaUtils.formatKey(PropertyNamer.methodToProperty(pathResolve.getImplMethodName())));
    }

    /**
     * 获取所有子孙 id
     *
     * @param descendants 子孙
     * @param <T>         节点类型
     * @return /
     */
    public static <T extends DefaultEntity> List<Long> allIds(List<Node<T>> descendants) {
        return flat(descendants).stream().map(DefaultEntity::getId).collect(Collectors.toList());
    }

    /**
     * 构建子孙对象
     *
     * @param id          节点 id
     * @param descendants 子孙
     * @param <T>         节点类型
     * @return /
     */
    public static <T extends DefaultEntity> List<Node<T>> buildDescendants(Long id, List<T> descendants) {
        if (CollectionUtil.isEmpty(descendants)) {
            return Collections.emptyList();
        }
        Map<Long, List<T>> groupedByPid = descendants.stream().collect(Collectors.groupingBy(T::getPid));
        return buildDescendants(id, descendants, groupedByPid);
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
    private static <T extends DefaultEntity> List<Node<T>> buildDescendants(Long id, List<T> descendants,
                                                                            Map<Long, List<T>> groupedByPid) {
        if (CollectionUtil.isEmpty(descendants)) {
            return Collections.emptyList();
        }
        List<T> children = groupedByPid.get(id);
        if (CollectionUtil.isEmpty(children)) {
            return Collections.emptyList();
        }
        return children.stream()
                .map(t -> new Node<T>(t, buildDescendants(t.getId(), groupedByPid.get(t.getId()), groupedByPid)))
                .collect(Collectors.toList());
    }
}