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
     * 计算高度
     *
     * @param descendants 子孙
     * @param <T>         实体类类型
     * @return /
     */
    public static <T> int calcHeight(List<Descendant<T>> descendants) {
        int level = 0;
        for (Descendant<T> descendant : descendants) {
            level = Math.max(level, maxLevel(descendant));
        }
        return level - 1;
    }

    /**
     * 获取最大阶度
     *
     * @param descendant 子孙
     * @param <T>        实体类类型
     * @return /
     */
    private static <T> int maxLevel(Descendant<T> descendant) {
        int level = descendant.getLevel();
        for (Descendant<T> e : descendant.getDescendants()) {
            level = Math.max(level, maxLevel(e));
        }
        return level;
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
    public static <T extends BaseEntity> List<Long> allIds(List<Descendant<T>> descendants) {
        return flat(descendants).stream().map(BaseEntity::getId).collect(Collectors.toList());
    }

    public static <T> ColumnCache getColumnCache(Class<T> entityClass, SFunction<T, String> function) {
        Map<String, ColumnCache> columnMap = LambdaUtils.getColumnMap(entityClass);
        SerializedLambda pathResolve = LambdaUtils.resolve(function);
        return columnMap.get(LambdaUtils.formatKey(PropertyNamer.methodToProperty(pathResolve.getImplMethodName())));
    }
}