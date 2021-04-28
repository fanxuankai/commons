package com.fanxuankai.commons.core.util;

import cn.hutool.core.exceptions.ExceptionUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 * @deprecated {@link ExceptionUtil}
 */
public class ThrowableUtils {
    /**
     * 获取错误的堆栈信息
     *
     * @param throwable 异常
     * @return /
     * @see ExceptionUtil#stacktraceToString(Throwable)
     */
    public static String getStackTrace(Throwable throwable) {
        List<StringWriter> list = new ArrayList<>();
        while (throwable != null) {
            StringWriter stringWriter = new StringWriter();
            try (PrintWriter printWriter = new PrintWriter(stringWriter)) {
                throwable.printStackTrace(printWriter);
                list.add(stringWriter);
            }
            throwable = throwable.getCause();
        }
        return list.stream().map(Object::toString).collect(Collectors.joining());
    }

    /**
     * 检查异常原因
     *
     * @param throwable       异常
     * @param ignoreThrowable 忽略的异常
     * @see ExceptionUtil#isCausedBy(Throwable, Class[])
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
}
