package com.fanxuankai.commons.extra.mybatis.annotation;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;

/**
 * @author fanxuankai
 */
public class OrderByAscWrapBehavior extends AbstractWrapBehavior {
    @Override
    public void wrap(AbstractWrapper<?, String, ?> wrapper, String column, Object val) {
        if (val instanceof Boolean) {
            wrapper.orderByAsc((boolean) val, column);
        }
    }

    @Override
    protected Query.Type getType() {
        return Query.Type.ORDER_BY_ASC;
    }
}
