package com.fanxuankai.commons.core.util;

import com.fanxuankai.commons.domain.Result;
import com.fanxuankai.commons.domain.Status;

import static com.fanxuankai.commons.domain.SimpleStatus.SUCCESS;

/**
 * Result 工具类
 *
 * @author fanxuankai
 */
public class ResultUtils {
    private static final Result<?> SUCCESS_RESULT = newResult();
    private static final Result<?> FAILED_RESULT = newResult();

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
}
