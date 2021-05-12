package com.fanxuankai.commons.domain;

/**
 * 默认状态码
 *
 * @author fanxuankai
 */
public enum DefaultStatus implements Status {
    /**
     * /
     */
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    ;
    private final int code;
    private final String message;

    DefaultStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
