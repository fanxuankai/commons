package com.fanxuankai.commons.util;

import com.fanxuankai.commons.domain.Result;
import com.fanxuankai.commons.domain.Status;

import java.util.Objects;

import static com.fanxuankai.commons.domain.DefaultStatus.FAILED;
import static com.fanxuankai.commons.domain.DefaultStatus.SUCCESS;

/**
 * Result 工具类
 *
 * @author fanxuankai
 */
public class ResultUtils {
    private static final Result<?> SUCCESS_RESULT = newResult(SUCCESS);
    private static final Result<?> FAILED_RESULT = newResult(FAILED);

    /**
     * 操作成功
     *
     * @param <T> 响应体类型
     * @return /
     */
    @SuppressWarnings("unchecked")
    public static <T> Result<T> ok() {
        return (Result<T>) SUCCESS_RESULT;
    }

    /**
     * 操作成功
     *
     * @param data 响应体
     * @param <T>  响应体类型
     * @return /
     */
    public static <T> Result<T> ok(T data) {
        return newResult(SUCCESS, data);
    }

    /**
     * 操作失败
     *
     * @param <T> 响应体类型
     * @return /
     */
    @SuppressWarnings("unchecked")
    public static <T> Result<T> fail() {
        return (Result<T>) FAILED_RESULT;
    }

    /**
     * 操作失败
     *
     * @param message 错误信息
     * @param <T>     响应体类型
     * @return /
     */
    public static <T> Result<T> fail(String message) {
        return newResult(new Status() {
            @Override
            public Integer getCode() {
                return FAILED.getCode();
            }

            @Override
            public String getMessage() {
                return message;
            }
        });
    }

    /**
     * 操作结果
     *
     * @param status 响应状态
     * @param <T>    响应体类型
     * @return /
     */
    public static <T> Result<T> newResult(Status status) {
        return newResult(status, null);
    }

    /**
     * 操作结果
     *
     * @param status 响应状态
     * @param data   响应体
     * @param <T>    响应体类型
     * @return /
     */
    public static <T> Result<T> newResult(Status status, T data) {
        Result<T> result = new Result<>();
        result.setStatus(status.getCode());
        result.setMessage(status.getMessage());
        result.setData(data);
        return result;
    }

    /**
     * 判断是否操作成功
     *
     * @param result 操作结果
     * @param <T>    响应体类型
     * @return /
     */
    public static <T> boolean isSuccess(Result<T> result) {
        return Objects.equals(result.getStatus(), SUCCESS_RESULT.getStatus());
    }
}
