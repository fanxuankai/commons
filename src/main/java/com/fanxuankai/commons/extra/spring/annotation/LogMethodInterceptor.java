package com.fanxuankai.commons.extra.spring.annotation;

import cn.hutool.json.JSONUtil;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fanxuankai
 */
public class LogMethodInterceptor implements MethodInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogMethodInterceptor.class);

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        Log logAnnotation = method.getAnnotation(Log.class);
        return proceed(methodInvocation, logAnnotation);
    }

    private Object proceed(MethodInvocation methodInvocation, Log logAnnotation) throws Throwable {
        LogInfo logInfo = new LogInfo();
        StopWatch sw = new StopWatch();
        sw.start();
        Object proceed = methodInvocation.proceed();
        sw.stop();
        if (logAnnotation.executeTime()) {
            logInfo.timeMillis = sw.getTotalTimeMillis();
        }
        Method method = methodInvocation.getMethod();
        logInfo.className = method.getDeclaringClass().getName();
        logInfo.methodName = method.getName();
        if (logAnnotation.params()) {
            logInfo.setParams(methodInvocation);
        }
        if (logAnnotation.returnValue()) {
            logInfo.returnValue = proceed;
        }
        String msg;
        if (logAnnotation.prettyFormat()) {
            msg = JSONUtil.toJsonPrettyStr(logInfo);
        } else {
            msg = JSONUtil.toJsonStr(logInfo);
        }
        if (logAnnotation.debug()) {
            LOGGER.debug(msg);
        } else {
            LOGGER.info(msg);
        }
        return proceed;
    }

    private static final class LogInfo {
        private String className;
        private String methodName;
        private Map<String, Object> params;
        private Object returnValue;
        private long timeMillis;

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public Map<String, Object> getParams() {
            return params;
        }

        public void setParams(MethodInvocation methodInvocation) {
            Object[] arguments = methodInvocation.getArguments();
            Parameter[] parameters = methodInvocation.getMethod().getParameters();
            if (arguments != null && arguments.length > 0) {
                params = new HashMap<>(arguments.length);
                for (int i = 0; i < parameters.length; i++) {
                    params.put(parameters[i].getName(), arguments[i]);
                }
            }
        }

        public void setParams(Map<String, Object> params) {
            this.params = params;
        }

        public Object getReturnValue() {
            return returnValue;
        }

        public void setReturnValue(Object returnValue) {
            this.returnValue = returnValue;
        }

        public long getTimeMillis() {
            return timeMillis;
        }

        public void setTimeMillis(long timeMillis) {
            this.timeMillis = timeMillis;
        }
    }
}
