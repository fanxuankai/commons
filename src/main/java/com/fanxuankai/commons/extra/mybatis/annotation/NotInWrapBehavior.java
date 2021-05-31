package com.fanxuankai.commons.extra.mybatis.annotation;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;

import java.util.Collection;

/**
 * @author fanxuankai
 */
public class NotInWrapBehavior implements WrapBehavior {
    @Override
    public void wrap(AbstractWrapper<?, String, ?> wrapper, String column, Object val) {
        if (val instanceof Collection) {
            wrapper.notIn(column, (Collection<?>) val);
        }
    }

    @Override
    public Query.Type getType() {
        return Query.Type.NOT_IN;
    }
}
