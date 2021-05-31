package com.fanxuankai.commons.extra.spring.util;

/**
 * @author fanxuankai
 */
public class TypeReference<T> {

    protected final Class<T> type;

    protected TypeReference() {
        this.type = GenericTypeUtils.getGenericType(getClass(), TypeReference.class, 0);
    }

    public Class<T> getType() {
        return type;
    }

}
