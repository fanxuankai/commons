package com.fanxuankai.commons.util;

import com.fanxuankai.commons.domain.DefaultStatus;
import com.fanxuankai.commons.domain.Status;
import com.fanxuankai.commons.exception.BizException;

import java.util.function.Supplier;

/**
 * 断言工具
 *
 * @author fanxuankai
 */
public class Assert {
    /**
     * 非空断言
     *
     * @param object 检查的对象
     * @param status 响应状态
     * @throws BizException 断言成功后抛出异常
     */
    public static void notEmpty(Object object, Status status) {
        if (ParamUtils.isEmpty(object)) {
            throw new BizException(status);
        }
    }

    /**
     * 非空断言
     *
     * @param object          检查的对象
     * @param messageSupplier 错误信息
     * @throws BizException 断言成功后抛出异常
     */
    public static void notEmpty(Object object, Supplier<String> messageSupplier) {
        notEmpty(object, DefaultStatus.FAILED.getCode(), messageSupplier);
    }

    /**
     * 非空断言
     *
     * @param object          检查的对象
     * @param code            代码
     * @param messageSupplier 错误信息
     * @throws BizException 断言成功后抛出异常
     */
    public static void notEmpty(Object object, int code, Supplier<String> messageSupplier) {
        if (ParamUtils.isEmpty(object)) {
            throw new BizException(newStatus(code, messageSupplier.get()));
        }
    }

    /**
     * 非空断言
     *
     * @param object 检查的对象
     * @param status 响应状态
     * @param params 参数
     * @throws BizException 断言成功后抛出异常
     */
    public static void notEmpty(Object object, Status status, Object... params) {
        if (ParamUtils.isEmpty(object)) {
            throw new BizException(status, params);
        }
    }

    /**
     * 非空断言
     *
     * @param object          检查的对象
     * @param messageSupplier 错误信息
     * @param params          参数
     * @throws BizException 断言成功后抛出异常
     */
    public static void notEmpty(Object object, Supplier<String> messageSupplier, Object... params) {
        notEmpty(object, DefaultStatus.FAILED.getCode(), messageSupplier, params);
    }

    /**
     * 非空断言
     *
     * @param object          检查的对象
     * @param code            代码
     * @param messageSupplier 错误信息
     * @param params          参数
     * @throws BizException 断言成功后抛出异常
     */
    public static void notEmpty(Object object, int code, Supplier<String> messageSupplier, Object... params) {
        if (ParamUtils.isEmpty(object)) {
            throw new BizException(newStatus(code, messageSupplier.get()), params);
        }
    }

    /**
     * 非 null 断言
     *
     * @param object 检查的对象
     * @param status 响应状态
     * @throws BizException 断言成功后抛出异常
     */
    public static void notNull(Object object, Status status) {
        if (object == null) {
            throw new BizException(status);
        }
    }

    /**
     * 非 null 断言
     *
     * @param object          检查的对象
     * @param messageSupplier 错误信息
     * @throws BizException 断言成功后抛出异常
     */
    public static void notNull(Object object, Supplier<String> messageSupplier) {
        notNull(object, DefaultStatus.FAILED.getCode(), messageSupplier);
    }

    /**
     * 非 null 断言
     *
     * @param object          检查的对象
     * @param code            代码
     * @param messageSupplier 错误信息
     * @throws BizException 断言成功后抛出异常
     */
    public static void notNull(Object object, int code, Supplier<String> messageSupplier) {
        if (object == null) {
            throw new BizException(newStatus(code, messageSupplier.get()));
        }
    }

    /**
     * 非 null 断言
     *
     * @param object 检查的对象
     * @param status 响应状态
     * @param params 参数
     * @throws BizException 断言成功后抛出异常
     */
    public static void notNull(Object object, Status status, Object... params) {
        if (object == null) {
            throw new BizException(status, params);
        }
    }

    /**
     * 非 null 断言
     *
     * @param object          检查的对象
     * @param messageSupplier 错误信息
     * @param params          参数
     * @throws BizException 断言成功后抛出异常
     */
    public static void notNull(Object object, Supplier<String> messageSupplier, Object... params) {
        notNull(object, DefaultStatus.FAILED.getCode(), messageSupplier, params);
    }

    /**
     * 非 null 断言
     *
     * @param object          检查的对象
     * @param code            代码
     * @param messageSupplier 错误信息
     * @param params          参数
     * @throws BizException 断言成功后抛出异常
     */
    public static void notNull(Object object, int code, Supplier<String> messageSupplier, Object... params) {
        if (object == null) {
            throw new BizException(newStatus(code, messageSupplier.get()), params);
        }
    }

    /**
     * 为 null 断言
     *
     * @param object 检查的对象
     * @param status 响应状态
     * @throws BizException 断言成功后抛出异常
     */
    public static void isNull(Object object, Status status) {
        if (object != null) {
            throw new BizException(status);
        }
    }

    /**
     * 为 null 断言
     *
     * @param object          检查的对象
     * @param messageSupplier 错误信息
     * @throws BizException 断言成功后抛出异常
     */
    public static void isNull(Object object, Supplier<String> messageSupplier) {
        isNull(object, DefaultStatus.FAILED.getCode(), messageSupplier);
    }

    /**
     * 为 null 断言
     *
     * @param object          检查的对象
     * @param code            代码
     * @param messageSupplier 错误信息
     * @throws BizException 断言成功后抛出异常
     */
    public static void isNull(Object object, int code, Supplier<String> messageSupplier) {
        if (object != null) {
            throw new BizException(newStatus(code, messageSupplier.get()));
        }
    }

    /**
     * 为 null 断言
     *
     * @param object 检查的对象
     * @param status 响应状态
     * @param params 参数
     * @throws BizException 断言成功后抛出异常
     */
    public static void isNull(Object object, Status status, Object... params) {
        isTrue(object != null, status, params);
    }

    /**
     * 为 null 断言
     *
     * @param object          检查的对象
     * @param messageSupplier 错误信息
     * @param params          参数
     * @throws BizException 断言成功后抛出异常
     */
    public static void isNull(Object object, Supplier<String> messageSupplier, Object... params) {
        isNull(object, DefaultStatus.FAILED.getCode(), messageSupplier, params);
    }

    /**
     * 为 null 断言
     *
     * @param object          检查的对象
     * @param code            代码
     * @param messageSupplier 错误信息
     * @param params          参数
     * @throws BizException 断言成功后抛出异常
     */
    public static void isNull(Object object, int code, Supplier<String> messageSupplier, Object... params) {
        if (object != null) {
            throw new BizException(newStatus(code, messageSupplier.get()), params);
        }
    }

    /**
     * 为真断言
     *
     * @param expression 表达式
     * @param status     响应状态
     * @throws BizException 断言成功后抛出异常
     */
    public static void isTrue(boolean expression, Status status) {
        if (!expression) {
            throw new BizException(status);
        }
    }

    /**
     * 为真断言
     *
     * @param expression      表达式
     * @param messageSupplier 错误信息
     * @throws BizException 断言成功后抛出异常
     */
    public static void isTrue(boolean expression, Supplier<String> messageSupplier) {
        isTrue(expression, DefaultStatus.FAILED.getCode(), messageSupplier);
    }

    /**
     * 为真断言
     *
     * @param expression      表达式
     * @param code            代码
     * @param messageSupplier 错误信息
     * @throws BizException 断言成功后抛出异常
     */
    public static void isTrue(boolean expression, int code, Supplier<String> messageSupplier) {
        if (!expression) {
            throw new BizException(newStatus(code, messageSupplier.get()));
        }
    }

    /**
     * 为真断言
     *
     * @param expression 表达式
     * @param status     响应状态
     * @param params     参数
     * @throws BizException 断言成功后抛出异常
     */
    public static void isTrue(boolean expression, Status status,
                              Object... params) {
        if (!expression) {
            throw new BizException(status, params);
        }
    }

    /**
     * 为真断言
     *
     * @param expression      表达式
     * @param messageSupplier 错误信息
     * @param params          参数
     * @throws BizException 断言成功后抛出异常
     */
    public static void isTrue(boolean expression, Supplier<String> messageSupplier, Object... params) {
        isTrue(expression, DefaultStatus.FAILED.getCode(), messageSupplier, params);
    }

    /**
     * 为真断言
     *
     * @param expression      表达式
     * @param code            代码
     * @param messageSupplier 错误信息
     * @param params          参数
     * @throws BizException 断言成功后抛出异常
     */
    public static void isTrue(boolean expression, int code, Supplier<String> messageSupplier, Object... params) {
        if (!expression) {
            throw new BizException(newStatus(code, messageSupplier.get()), params);
        }
    }

    /**
     * 为假断言
     *
     * @param expression 表达式
     * @param status     响应状态
     * @throws BizException 断言成功后抛出异常
     */
    public static void isFalse(boolean expression, Status status) {
        if (expression) {
            throw new BizException(status);
        }
    }

    /**
     * 为假断言
     *
     * @param expression      表达式
     * @param messageSupplier 错误信息
     * @throws BizException 断言成功后抛出异常
     */
    public static void isFalse(boolean expression, Supplier<String> messageSupplier) {
        isFalse(expression, DefaultStatus.FAILED.getCode(), messageSupplier);
    }

    /**
     * 为假断言
     *
     * @param expression      表达式
     * @param code            代码
     * @param messageSupplier 错误信息
     * @throws BizException 断言成功后抛出异常
     */
    public static void isFalse(boolean expression, int code, Supplier<String> messageSupplier) {
        if (expression) {
            throw new BizException(newStatus(code, messageSupplier.get()));
        }
    }

    /**
     * 为假断言
     *
     * @param expression 表达式
     * @param status     响应状态
     * @param params     参数
     * @throws BizException 断言成功后抛出异常
     */
    public static void isFalse(boolean expression, Status status, Object... params) {
        if (expression) {
            throw new BizException(status, params);
        }
    }

    /**
     * 为假断言
     *
     * @param expression      表达式
     * @param messageSupplier 错误信息
     * @param params          参数
     * @throws BizException 断言成功后抛出异常
     */
    public static void isFalse(boolean expression, Supplier<String> messageSupplier, Object... params) {
        isFalse(expression, DefaultStatus.FAILED.getCode(), messageSupplier, params);
    }

    /**
     * 为假断言
     *
     * @param expression      表达式
     * @param code            代码
     * @param messageSupplier 错误信息
     * @param params          参数
     * @throws BizException 断言成功后抛出异常
     */
    public static void isFalse(boolean expression, int code, Supplier<String> messageSupplier, Object... params) {
        if (expression) {
            throw new BizException(newStatus(code, messageSupplier.get()), params);
        }
    }

    /**
     * 非空断言
     *
     * @param object            检查的对象
     * @param exceptionSupplier 异常
     * @param <E>               异常类型
     * @throws E 断言成功后抛出异常
     */
    public static <E extends Throwable> void notEmptyWithThrowable(Object object, Supplier<E> exceptionSupplier) throws E {
        if (ParamUtils.isEmpty(object)) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 非 null 断言
     *
     * @param object            检查的对象
     * @param exceptionSupplier 异常
     * @param <E>               异常类型
     * @throws E 断言成功后抛出异常
     */
    public static <E extends Throwable> void notNullWithThrowable(Object object, Supplier<E> exceptionSupplier) throws E {
        if (object == null) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 为 null 断言
     *
     * @param object            检查的对象
     * @param exceptionSupplier 异常
     * @param <E>               异常类型
     * @throws E 断言成功后抛出异常
     */
    public static <E extends Throwable> void isNullWithThrowable(Object object, Supplier<E> exceptionSupplier) throws E {
        if (object != null) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 为真断言
     *
     * @param expression        表达式
     * @param exceptionSupplier 异常
     * @param <E>               异常类型
     * @throws E 断言成功后抛出异常
     */
    public static <E extends Throwable> void isTrueWithThrowable(boolean expression, Supplier<E> exceptionSupplier) throws E {
        if (!expression) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 为假断言
     *
     * @param expression        表达式
     * @param exceptionSupplier 异常
     * @param <E>               异常类型
     * @throws E 断言成功后抛出异常
     */
    public static <E extends Throwable> void isFalseWithThrowable(boolean expression, Supplier<E> exceptionSupplier) throws E {
        if (expression) {
            throw exceptionSupplier.get();
        }
    }

    private static Status newStatus(Integer code, String message) {
        return new Status() {
            @Override
            public Integer getCode() {
                return code;
            }

            @Override
            public String getMessage() {
                return message;
            }
        };
    }
}
