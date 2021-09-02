package com.fanxuankai.commons.util;

/**
 * 枚举协议
 *
 * @author fanxuankai
 */
public interface EnumProtocol {
    /**
     * 编码
     *
     * @return /
     */
    Integer getCode();

    /**
     * 值
     *
     * @return /
     */
    String getValue();

    /**
     * 描述
     *
     * @return /
     */
    default String getDescription() {
        return null;
    }
}
