package com.fanxuankai.commons.extra.mybatis.annotation;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;

/**
 * 条件封装行为
 *
 * @author fanxuankai
 */
public interface WrapBehavior {
    /**
     * 封装
     *
     * @param wrapper 封装类
     * @param column  列
     * @param val     值
     */
    void wrap(AbstractWrapper<?, String, ?> wrapper, String column, Object val);

    /**
     * 对应的处理方式
     *
     * @return /
     */
    Query.Type getType();
}
