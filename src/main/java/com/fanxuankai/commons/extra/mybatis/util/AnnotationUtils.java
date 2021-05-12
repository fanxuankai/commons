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
        OptionalUtils.of(schemaName)
                .ifPresent(value -> OptionalUtils.of(entityClass.getAnnotation(TableName.class))
                        .ifPresent(annotation -> AnnotationUtil.setValue(annotation, "schema", value)));
        OptionalUtils.of(tableName)
                .ifPresent(value -> OptionalUtils.of(entityClass.getAnnotation(TableName.class))
                        .ifPresent(annotation -> AnnotationUtil.setValue(annotation, "value", value)));
    }

    /**
     * 修改字段名
     *
     * @param entityClass 实体类
     * @param fieldMap    key: 实体类属性名 value: 数据库字段名,为空时不修改
     */
    public static void modifyFieldInfo(Class<?> entityClass, Map<String, String> fieldMap) {
        OptionalUtils.of(fieldMap)
                .ifPresent(map -> map.forEach((field, value) -> OptionalUtils.of(value)
                        // 如果 value 不为空
                        .ifPresent(s -> OptionalUtils.of(ReflectUtil.getField(entityClass, field))
                                // 获取 field 的注解
                                .map(f -> f.getAnnotation(TableField.class))
                                // 如果注解不为空
                                .ifPresent(annotation -> AnnotationUtil.setValue(annotation, "value", s)))

                ));
    }
}
