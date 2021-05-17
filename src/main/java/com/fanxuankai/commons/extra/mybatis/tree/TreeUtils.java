package com.fanxuankai.commons.extra.mybatis.tree;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.support.ColumnCache;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;
import org.apache.ibatis.reflection.property.PropertyNamer;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
public class TreeUtils {
    /**
     * 计算 height
     *
     * @param descendants 子孙
     * @param <T>         实体类类型
     * @return /
     */
    public static <T> int calcHeight(List<Descendant<T>> descendants) {
        List<List<T>> list = new ArrayList<>();
        for (Descendant<T> descendant : descendants) {
            list.add(flat(Collections.singletonList(descendant)));
        }
        return list.stream()
                .map(List::size)
                .max(Integer::compareTo)
                .orElse(0);
    }

    /**
     * 扁平化子孙节点
     *
     * @param descendants 子孙
     * @param <T>         实体类类型
     * @return /
     */
    public static <T> List<T> flat(List<Descendant<T>> descendants) {
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
     * 获取所有子孙 id
     *
     * @param descendants 子孙
     * @param <T>         节点类型
     * @return /
     */
    public static <T extends Entity> List<Long> allIds(List<Descendant<T>> descendants) {
        return flat(descendants).stream().map(Entity::getId).collect(Collectors.toList());
    }

    public static <T> ColumnCache getColumnCache(Class<T> entityClass, SFunction<T, String> function) {
        Map<String, ColumnCache> columnMap = LambdaUtils.getColumnMap(entityClass);
        SerializedLambda pathResolve = LambdaUtils.resolve(function);
        return columnMap.get(LambdaUtils.formatKey(PropertyNamer.methodToProperty(pathResolve.getImplMethodName())));
    }
}