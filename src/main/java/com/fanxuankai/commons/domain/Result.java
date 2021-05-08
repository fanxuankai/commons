package com.fanxuankai.commons.domain;

/**
 * HTTP 响应结果
 *
 * @author fanxuankai
 */
public class Result<T> {
    /**
     * 代码
     */
    private int code;
    /**
     * 提示信息
     */
    private String message;
    /**
     * 数据
     */
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
