package com.fanxuankai.commons.extra.mybatis.annotation;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;

/**
 * @author fanxuankai
 */
public class LikeWrapBehavior extends AbstractWrapBehavior {
    @Override
    public void wrap(AbstractWrapper<?, String, ?> wrapper, String column, Object val) {
        wrapper.like(column, val);
    }

    @Override
    protected Query.Type getType() {
        return Query.Type.LIKE;
    }
}
