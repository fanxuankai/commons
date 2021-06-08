package com.fanxuankai.commons.extra.spring.annotation;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

/**
 * @author fanxuankai
 */
@Component
public class LogPointcutAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {
    private final Advice advice;
    private final Pointcut pointcut;

    public LogPointcutAdvisor() {
        this.advice = new LogMethodInterceptor();
        this.pointcut = AnnotationMatchingPointcut.forMethodAnnotation(Log.class);
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (this.advice instanceof BeanFactoryAware) {
            ((BeanFactoryAware) this.advice).setBeanFactory(beanFactory);
        }
    }
}