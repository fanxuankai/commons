package com.fanxuankai.commons.extra.mybatis.annotation;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;

/**
 * @author fanxuankai
 */
public class IsNullWrapBehavior extends AbstractWrapBehavior {
    @Override
    public void wrap(AbstractWrapper<?, String, ?> wrapper, String column, Object val) {
        if (val instanceof Boolean) {
            wrapper.isNull((boolean) val, column);
        }
    }

    @Override
    protected Query.Type getType() {
        return Query.Type.IS_NULL;
    }
}
