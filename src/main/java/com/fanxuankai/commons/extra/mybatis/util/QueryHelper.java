package com.fanxuankai.commons.extra.mybatis.util;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.*;
import com.baomidou.mybatisplus.core.toolkit.support.ColumnCache;
import com.fanxuankai.commons.extra.mybatis.annotation.CriteriaWrapper;
import com.fanxuankai.commons.extra.mybatis.annotation.Query;
import com.fanxuankai.commons.extra.mybatis.annotation.WrapBehaviorLoader;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Query 帮助类
 *
 * @author fanxuankai
 */
public class QueryHelper {
    /**
     * 查询条件类转 QueryWrapper
     *
     * @param entityClass 实体类
     * @param criteria    查询条件类
     * @param <T>         /
     * @param <C>         /
     * @return /
     */
    public static <T, C> QueryWrapper<T> getQueryWrapper(Class<T> entityClass, C criteria) {
        if (criteria == null) {
            return Wrappers.emptyWrapper();
        }
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        wrap(wrapper, entityClass, criteria);
        return wrapper;
    }

    /**
     * 查询条件类转 UpdateWrapper
     *
     * @param entityClass 实体类
     * @param criteria    查询条件类
     * @param <T>         /
     * @param <C>         /
     * @return /
     */
    public static <T, C> UpdateWrapper<T> getUpdateWrapper(Class<T> entityClass, C criteria) {
        UpdateWrapper<T> wrapper = new UpdateWrapper<>();
        if (criteria == null) {
            return wrapper;
        }
        wrap(wrapper, entityClass, criteria);
        return wrapper;
    }

    private static <T, C> void wrap(AbstractWrapper<T, String, ?> wrapper, Class<T> entityClass, C criteria) {
        Map<String, ColumnCache> columnMap = LambdaUtils.getColumnMap(entityClass);
        List<Field> fields = ReflectionKit.getFieldList(criteria.getClass());
        CriteriaWrapper criteriaWrapper = new CriteriaWrapper();
        for (Field field : fields) {
            field.setAccessible(true);
            Query q = field.getAnnotation(Query.class);
            if (q == null) {
                continue;
            }
            String propName = q.field();
            String fuzzy = q.fuzzy();
            String attributeName = StringUtils.isBlank(propName) ? field.getName() : propName;
            Object val = ReflectionKit.getMethodValue(criteria, field.getName());
            if (ObjectUtils.isEmpty(val)) {
                continue;
            }
            if (StringUtils.isNotBlank(fuzzy)) {
                wrapper.and(theWrapper -> {
                    String[] split = fuzzy.split(StringPool.COMMA);
                    String first = split[0];
                    ColumnCache columnCache = columnMap.get(LambdaUtils.formatKey(first));
                    theWrapper.like(columnCache.getColumn(), val);
                    for (int i = 1; i < split.length; i++) {
                        columnCache = columnMap.get(LambdaUtils.formatKey(split[i]));
                        theWrapper.or().like(columnCache.getColumn(), val);
                    }
                });
                continue;
            }
            criteriaWrapper.setWrapBehavior(WrapBehaviorLoader.get(q.type()));
            // 部分查询方式 field 为空
            criteriaWrapper.wrap(wrapper, Optional.ofNullable(columnMap.get(LambdaUtils.formatKey(attributeName)))
                    .map(ColumnCache::getColumn)
                    .orElse(null), val);
        }
    }
}
