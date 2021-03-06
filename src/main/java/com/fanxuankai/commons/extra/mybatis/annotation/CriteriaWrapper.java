package com.fanxuankai.commons.extra.mybatis.annotation;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;

/**
 * 条件封装器
 *
 * @author fanxuankai
 */
public class CriteriaWrapper {
    private WrapBehavior wrapBehavior;

    /**
     * 封装
     *
     * @param wrapper 封装类
     * @param column  列
     * @param val     值
     */
    public void wrap(AbstractWrapper<?, String, ?> wrapper, String column, Object val) {
        if (wrapBehavior == null) {
            return;
        }
        wrapBehavior.wrap(wrapper, column, val);
    }

    public void setWrapBehavior(WrapBehavior wrapBehavior) {
        this.wrapBehavior = wrapBehavior;
    }
}
