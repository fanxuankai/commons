package com.fanxuankai.commons.extra.mybatis.tree;

import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.support.ColumnCache;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;
import org.apache.ibatis.reflection.property.PropertyNamer;

import java.util.Map;

/**
 * @author fanxuankai
 */
public class TreeUtils extends com.fanxuankai.commons.util.TreeUtils {
    public static <T> ColumnCache getColumnCache(Class<T> entityClass, SFunction<T, ?> function) {
        Map<String, ColumnCache> columnMap = LambdaUtils.getColumnMap(entityClass);
        SerializedLambda pathResolve = LambdaUtils.resolve(function);
        return columnMap.get(LambdaUtils.formatKey(PropertyNamer.methodToProperty(pathResolve.getImplMethodName())));
    }
}