package com.fanxuankai.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.BatchUpdateException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author fanxuankai
 */
public class ThrowableUtils {
    /**
     * 获取错误的堆栈信息
     *
     * @param throwable 异常
     */
    public static String getStackTrace(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        try (PrintWriter printWriter = new PrintWriter(stringWriter)) {
            throwable.printStackTrace(printWriter);
            return stringWriter.toString();
        }
    }

    /**
     * 检查异常原因
     *
     * @param throwable       异常
     * @param ignoreThrowable 忽略的异常
     */
    public static void checkException(Throwable throwable, Class<?>... ignoreThrowable) {
        if (throwable == null) {
            return;
        }
        if (ignoreThrowable == null) {
            throw new RuntimeException(throwable);
        }
        for (Class<?> aClass : ignoreThrowable) {
            if (aClass.isAssignableFrom(throwable.getClass())) {
                return;
            }
        }
        if (throwable.getCause() == null) {
            throw new RuntimeException(throwable);
        }
        checkException(throwable.getCause(), ignoreThrowable);
    }

    public static void main(String[] args) {
        checkException(new BatchUpdateException(new SQLIntegrityConstraintViolationException()),
                SQLIntegrityConstraintViolationException.class);
    }
}
