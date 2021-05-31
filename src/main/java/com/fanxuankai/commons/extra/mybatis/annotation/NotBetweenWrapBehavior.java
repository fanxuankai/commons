package com.fanxuankai.commons.extra.mybatis.annotation;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author fanxuankai
 */
public class NotBetweenWrapBehavior implements WrapBehavior {
    @Override
    public void wrap(AbstractWrapper<?, String, ?> wrapper, String column, Object val) {
        if (val instanceof Collection) {
            List<?> betweenVal = new ArrayList<>((Collection<?>) val);
            wrapper.notBetween(column, betweenVal.get(0), betweenVal.get(1));
        }
    }

    @Override
    public Query.Type getType() {
        return Query.Type.NOT_BETWEEN;
    }
}
