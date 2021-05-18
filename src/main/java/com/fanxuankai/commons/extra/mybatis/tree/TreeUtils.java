package com.fanxuankai.commons.extra.mybatis.tree;

import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.support.ColumnCache;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;
import com.fanxuankai.commons.util.Node;
import org.apache.ibatis.reflection.property.PropertyNamer;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
public class TreeUtils extends com.fanxuankai.commons.util.TreeUtils {

    /**
     * 获取所有子孙 id
     *
     * @param nodes 子孙
     * @param <T>   节点类型
     * @return /
     */
    public static <T extends BaseEntity> List<Long> allIds(List<Node<T>> nodes) {
        return flat(nodes).stream().map(BaseEntity::getId).collect(Collectors.toList());
    }

    public static <T> ColumnCache getColumnCache(Class<T> entityClass, SFunction<T, String> function) {
        Map<String, ColumnCache> columnMap = LambdaUtils.getColumnMap(entityClass);
        SerializedLambda pathResolve = LambdaUtils.resolve(function);
        return columnMap.get(LambdaUtils.formatKey(PropertyNamer.methodToProperty(pathResolve.getImplMethodName())));
    }
}