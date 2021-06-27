package com.fanxuankai.commons.extra.mybatis.util;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fanxuankai.commons.util.OptionalUtils;

import java.util.Map;

/**
 * @author fanxuankai
 */
public class AnnotationUtils {
    /**
     * 修改表信息
     *
     * @param entityClass 实体类
     * @param schemaName  库名,为空时不修改
     * @param tableName   表名,为空时不修改
     */
    public static void modifyTableInfo(Class<?> entityClass, String schemaName, String tableName) {
        OptionalUtils.ofNullable(schemaName)
                .ifPresent(value -> OptionalUtils.ofNullable(entityClass.getAnnotation(TableName.class))
                        .ifPresent(annotation -> AnnotationUtil.setValue(annotation, "schema", value)));
        OptionalUtils.ofNullable(tableName)
                .ifPresent(value -> OptionalUtils.ofNullable(entityClass.getAnnotation(TableName.class))
                        .ifPresent(annotation -> AnnotationUtil.setValue(annotation, "value", value)));
    }

    /**
     * 修改字段名
     *
     * @param entityClass 实体类
     * @param fieldMap    key: 实体类属性名 value: 数据库字段名,为空时不修改
     */
    public static void modifyFieldInfo(Class<?> entityClass, Map<String, String> fieldMap) {
        OptionalUtils.ofNullable(fieldMap)
                .ifPresent(map -> map.forEach((field, value) -> OptionalUtils.ofNullable(value)
                        // 如果 value 不为空
                        .ifPresent(s -> OptionalUtils.ofNullable(ReflectUtil.getField(entityClass, field))
                                // 获取 field 的注解
                                .map(f -> f.getAnnotation(TableField.class))
                                // 如果注解不为空
                                .ifPresent(annotation -> AnnotationUtil.setValue(annotation, "value", s)))

                ));
    }
}
