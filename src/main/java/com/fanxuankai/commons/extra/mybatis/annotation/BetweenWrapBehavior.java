package com.fanxuankai.commons.extra.mybatis.annotation;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author fanxuankai
 */
public class BetweenWrapBehavior extends AbstractWrapBehavior {
    @Override
    public void wrap(AbstractWrapper<?, String, ?> wrapper, String column, Object val) {
        if (val instanceof Collection) {
            List<?> betweenVal = new ArrayList<>((Collection<?>) val);
            wrapper.between(column, betweenVal.get(0), betweenVal.get(1));
        }
    }

    @Override
    protected Query.Type getType() {
        return Query.Type.BETWEEN;
    }
}
