package com.fanxuankai.commons.domain;

/**
 * 服务器内部错误状态码
 * 50000 至 59999
 *
 * @author fanxuankai
 */
public enum SystemStatus implements Status {
    /**
     * /
     */
    BUSY(50000, "系统忙请稍后重试"),
    NPE(50001, "空指针异常"),
    ;
    private final int code;
    private final String message;

    SystemStatus(int code, String message) {
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
