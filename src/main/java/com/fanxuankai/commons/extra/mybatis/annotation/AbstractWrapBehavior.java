package com.fanxuankai.commons.extra.mybatis.annotation;

/**
 * @author fanxuankai
 */
public abstract class AbstractWrapBehavior implements WrapBehavior {
    /**
     * 对应的处理方式
     *
     * @return /
     */
    protected abstract Query.Type getType();
}
