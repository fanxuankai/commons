package com.fanxuankai.commons.extra.mybatis.annotation;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;

/**
 * @author fanxuankai
 */
public class GtWrapBehavior implements WrapBehavior {
    @Override
    public void wrap(AbstractWrapper<?, String, ?> wrapper, String column, Object val) {
        wrapper.gt(column, val);
    }

    @Override
    public Query.Type getType() {
        return Query.Type.GT;
    }
}
