package com.fanxuankai.commons.domain;

import static com.fanxuankai.commons.domain.StatusEnum.SUCCESS;

/**
 * 处理结果
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
    /**
     * 空 Result
     */
    private static final Result<?> SUCCESS_RESULT = Result.newResult();
    private static final Result<?> FAILED_RESULT = Result.newResult();

    @SuppressWarnings("unchecked")
    public static <T> Result<T> ok() {
        return (Result<T>) SUCCESS_RESULT;
    }

    @SuppressWarnings("unchecked")
    public static <T> Result<T> fail() {
        return (Result<T>) FAILED_RESULT;
    }

    public static <T> Result<T> newResult() {
        return newResult(SUCCESS, null);
    }

    public static <T> Result<T> newResult(T data) {
        return newResult(SUCCESS, data);
    }

    public static <T> Result<T> newResult(Status status) {
        return newResult(status, null);
    }

    public static <T> Result<T> newResult(Status status, T data) {
        Result<T> result = new Result<>();
        result.setCode(status.getCode());
        result.setMessage(status.getMessage());
        result.setData(data);
        return result;
    }

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
