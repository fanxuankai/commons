package com.fanxuankai.commons.domain;

/**
 * 默认状态码
 *
 * @author fanxuankai
 */
public enum DefaultStatus implements Status {
    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
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
