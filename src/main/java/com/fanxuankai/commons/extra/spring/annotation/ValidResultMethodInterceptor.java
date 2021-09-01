package com.fanxuankai.commons.extra.spring.annotation;

import cn.hutool.json.JSONUtil;
import com.fanxuankai.commons.domain.Result;
import com.fanxuankai.commons.exception.BizException;
import com.fanxuankai.commons.util.ResultUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 方法增强具体实现
 *
 * @author fanxuankai
 */
public class ValidResultMethodInterceptor implements MethodInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidResultMethodInterceptor.class);

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        return enhanceProceed(methodInvocation);
    }

    /**
     * 增强处理
     *
     * @param methodInvocation /
     * @return /
     * @throws Throwable /
     */
    private Object enhanceProceed(MethodInvocation methodInvocation) throws Throwable {
        ValidResult annotation = methodInvocation.getMethod().getAnnotation(ValidResult.class);
        Object proceed = methodInvocation.proceed();
        if (proceed instanceof Result) {
            Result<?> result = (Result<?>) proceed;
            if (annotation.log()) {
                // 打印日志
                LOGGER.info("{}.{}: {}", methodInvocation.getMethod().getDeclaringClass().getName(),
                        methodInvocation.getMethod().getName(), JSONUtil.toJsonStr(result));
            }
            // 失败时抛异常
            if (!ResultUtils.isSuccess(result) && annotation.behaviorOnFail() == ValidResult.BehaviorOnFail.FAIL) {
                throw new BizException(result.toString());
            }
        }
        return proceed;
    }
}