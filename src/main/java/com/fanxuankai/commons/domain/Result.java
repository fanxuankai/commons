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
    private Integer status;
    /**
     * 提示信息
     */
    private String message;
    /**
     * 数据
     */
    private T data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "Result{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
