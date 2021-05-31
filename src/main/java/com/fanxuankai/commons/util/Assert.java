package com.fanxuankai.commons.util;

import com.fanxuankai.commons.domain.Status;
import com.fanxuankai.commons.exception.BusinessException;

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
     * @param status 处理结果状态
     * @throws BusinessException 断言成功后抛出异常
     */
    public static void notEmpty(Object object, Status status) {
        if (ParamUtils.isEmpty(object)) {
            throw new BusinessException(status);
        }
    }

    /**
     * 非空断言
     *
     * @param object          检查的对象
     * @param code            代码
     * @param messageSupplier 错误信息
     * @throws BusinessException 断言成功后抛出异常
     */
    public static void notEmpty(Object object, int code, Supplier<String> messageSupplier) {
        if (ParamUtils.isEmpty(object)) {
            throw new BusinessException(newStatus(code, messageSupplier.get()));
        }
    }

    /**
     * 非空断言
     *
     * @param object 检查的对象
     * @param status 处理结果状态
     * @param params 参数
     * @throws BusinessException 断言成功后抛出异常
     */
    public static void notEmpty(Object object, Status status, Object... params) {
        if (ParamUtils.isEmpty(object)) {
            throw new BusinessException(status, params);
        }
    }

    /**
     * 非空断言
     *
     * @param object          检查的对象
     * @param code            代码
     * @param messageSupplier 错误信息
     * @param params          参数
     * @throws BusinessException 断言成功后抛出异常
     */
    public static void notEmpty(Object object, int code, Supplier<String> messageSupplier, Object... params) {
        if (ParamUtils.isEmpty(object)) {
            throw new BusinessException(newStatus(code, messageSupplier.get()), params);
        }
    }

    /**
     * 非 null 断言
     *
     * @param object 检查的对象
     * @param status 处理结果状态
     * @throws BusinessException 断言成功后抛出异常
     */
    public static void notNull(Object object, Status status) {
        if (object == null) {
            throw new BusinessException(status);
        }
    }

    /**
     * 非 null 断言
     *
     * @param object          检查的对象
     * @param code            代码
     * @param messageSupplier 错误信息
     * @throws BusinessException 断言成功后抛出异常
     */
    public static void notNull(Object object, int code, Supplier<String> messageSupplier) {
        if (object == null) {
            throw new BusinessException(newStatus(code, messageSupplier.get()));
        }
    }

    /**
     * 非 null 断言
     *
     * @param object 检查的对象
     * @param status 处理结果状态
     * @param params 参数
     * @throws BusinessException 断言成功后抛出异常
     */
    public static void notNull(Object object, Status status, Object... params) {
        if (object == null) {
            throw new BusinessException(status, params);
        }
    }

    /**
     * 非 null 断言
     *
     * @param object          检查的对象
     * @param code            代码
     * @param messageSupplier 错误信息
     * @param params          参数
     * @throws BusinessException 断言成功后抛出异常
     */
    public static void notNull(Object object, int code, Supplier<String> messageSupplier, Object... params) {
        if (object == null) {
            throw new BusinessException(newStatus(code, messageSupplier.get()), params);
        }
    }

    /**
     * 为 null 断言
     *
     * @param object 检查的对象
     * @param status 处理结果状态
     * @throws BusinessException 断言成功后抛出异常
     */
    public static void isNull(Object object, Status status) {
        if (object != null) {
            throw new BusinessException(status);
        }
    }

    /**
     * 为 null 断言
     *
     * @param object          检查的对象
     * @param code            代码
     * @param messageSupplier 错误信息
     * @throws BusinessException 断言成功后抛出异常
     */
    public static void isNull(Object object, int code, Supplier<String> messageSupplier) {
        if (object != null) {
            throw new BusinessException(newStatus(code, messageSupplier.get()));
        }
    }

    /**
     * 为 null 断言
     *
     * @param object 检查的对象
     * @param status 处理结果状态
     * @param params 参数
     * @throws BusinessException 断言成功后抛出异常
     */
    public static void isNull(Object object, Status status, Object... params) {
        isTrue(object != null, status, params);
    }

    /**
     * 为 null 断言
     *
     * @param object          检查的对象
     * @param code            代码
     * @param messageSupplier 错误信息
     * @param params          参数
     * @throws BusinessException 断言成功后抛出异常
     */
    public static void isNull(Object object, int code, Supplier<String> messageSupplier, Object... params) {
        if (object != null) {
            throw new BusinessException(newStatus(code, messageSupplier.get()), params);
        }
    }

    /**
     * 为真断言
     *
     * @param expression 表达式
     * @param status     处理结果状态
     * @throws BusinessException 断言成功后抛出异常
     */
    public static void isTrue(boolean expression, Status status) {
        if (!expression) {
            throw new BusinessException(status);
        }
    }

    /**
     * 为真断言
     *
     * @param expression      表达式
     * @param code            代码
     * @param messageSupplier 错误信息
     * @throws BusinessException 断言成功后抛出异常
     */
    public static void isTrue(boolean expression, int code, Supplier<String> messageSupplier) {
        if (!expression) {
            throw new BusinessException(newStatus(code, messageSupplier.get()));
        }
    }

    /**
     * 为真断言
     *
     * @param expression 表达式
     * @param status     处理结果状态
     * @param params     参数
     * @throws BusinessException 断言成功后抛出异常
     */
    public static void isTrue(boolean expression, Status status,
                              Object... params) {
        if (!expression) {
            throw new BusinessException(status, params);
        }
    }

    /**
     * 为真断言
     *
     * @param expression      表达式
     * @param code            代码
     * @param messageSupplier 错误信息
     * @param params          参数
     * @throws BusinessException 断言成功后抛出异常
     */
    public static void isTrue(boolean expression, int code, Supplier<String> messageSupplier,
                              Object... params) {
        if (!expression) {
            throw new BusinessException(newStatus(code, messageSupplier.get()), params);
        }
    }

    /**
     * 为假断言
     *
     * @param expression 表达式
     * @param status     处理结果状态
     * @throws BusinessException 断言成功后抛出异常
     */
    public static void isFalse(boolean expression, Status status) {
        if (expression) {
            throw new BusinessException(status);
        }
    }

    /**
     * 为假断言
     *
     * @param expression      表达式
     * @param code            代码
     * @param messageSupplier 错误信息
     * @throws BusinessException 断言成功后抛出异常
     */
    public static void isFalse(boolean expression, int code, Supplier<String> messageSupplier) {
        if (expression) {
            throw new BusinessException(newStatus(code, messageSupplier.get()));
        }
    }

    /**
     * 为假断言
     *
     * @param expression 表达式
     * @param status     处理结果状态
     * @param params     参数
     * @throws BusinessException 断言成功后抛出异常
     */
    public static void isFalse(boolean expression, Status status, Object... params) {
        if (expression) {
            throw new BusinessException(status, params);
        }
    }

    /**
     * 为假断言
     *
     * @param expression      表达式
     * @param code            代码
     * @param messageSupplier 错误信息
     * @param params          参数
     * @throws BusinessException 断言成功后抛出异常
     */
    public static void isFalse(boolean expression, int code, Supplier<String> messageSupplier,
                               Object... params) {
        if (expression) {
            throw new BusinessException(newStatus(code, messageSupplier.get()), params);
        }
    }

    /**
     * 非空断言
     *
     * @param object            检查的对象
     * @param exceptionSupplier 异常
     * @throws E 断言成功后抛出异常
     */
    public static <E extends Throwable> void notEmpty(Object object, Supplier<E> exceptionSupplier) throws E {
        if (ParamUtils.isEmpty(object)) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 非 null 断言
     *
     * @param object            检查的对象
     * @param exceptionSupplier 异常
     * @throws E 断言成功后抛出异常
     */
    public static <E extends Throwable> void notNull(Object object, Supplier<E> exceptionSupplier) throws E {
        if (object == null) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 为 null 断言
     *
     * @param object            检查的对象
     * @param exceptionSupplier 异常
     * @throws E 断言成功后抛出异常
     */
    public static <E extends Throwable> void isNull(Object object, Supplier<E> exceptionSupplier) throws E {
        if (object != null) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 为真断言
     *
     * @param expression        表达式
     * @param exceptionSupplier 异常
     * @throws E 断言成功后抛出异常
     */
    public static <E extends Throwable> void isTrue(boolean expression, Supplier<E> exceptionSupplier) throws E {
        if (!expression) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 为假断言
     *
     * @param expression        表达式
     * @param exceptionSupplier 异常
     * @throws E 断言成功后抛出异常
     */
    public static <E extends Throwable> void isFalse(boolean expression, Supplier<E> exceptionSupplier) throws E {
        if (expression) {
            throw exceptionSupplier.get();
        }
    }

    private static Status newStatus(int code, String message) {
        return new Status() {
            @Override
            public int getCode() {
                return code;
            }

            @Override
            public String getMessage() {
                return message;
            }
        };
    }
}
