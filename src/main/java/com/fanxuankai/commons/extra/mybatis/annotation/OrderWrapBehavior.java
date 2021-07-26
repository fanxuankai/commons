package com.fanxuankai.commons.extra.mybatis.annotation;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;

/**
 * @author fanxuankai
 */
public class OrderWrapBehavior implements WrapBehavior {
    @Override
    public void wrap(AbstractWrapper<?, String, ?> wrapper, String column, Object val) {
        String[] orderEntry = val.toString().split(StrPool.COMMA);
        for (int i = 0; i < orderEntry.length; ) {
            String col = orderEntry[i];
            String direction = orderEntry[i + 1];
            if (StrUtil.equals("asc", direction, true)) {
                wrapper.orderByAsc(col);
            } else if (StrUtil.equals("desc", direction, true)) {
                wrapper.orderByAsc(col);
            }
            i += 2;
        }
    }

    @Override
    public Query.Type getType() {
        return Query.Type.ORDER;
    }
}
