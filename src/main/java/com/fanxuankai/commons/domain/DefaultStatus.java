package com.fanxuankai.commons.domain;

/**
 * 默认状态码
 *
 * @author fanxuankai
 */
public enum DefaultStatus implements Status {
    /**
     * 请求已成功
     */
    SUCCESS(200, "请求已成功"),
    FAILED(500, "服务器未知错误"),
    ;
    private final Integer code;
    private final String message;

    DefaultStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
