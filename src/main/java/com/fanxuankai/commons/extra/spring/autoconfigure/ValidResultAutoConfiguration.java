package com.fanxuankai.commons.extra.spring.autoconfigure;

import com.fanxuankai.commons.extra.spring.annotation.ValidResult;
import com.fanxuankai.commons.extra.spring.annotation.ValidResultMethodInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author fanxuankai
 */
public class ValidResultAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public ValidResultMethodInterceptor validResultMethodInterceptor() {
        return new ValidResultMethodInterceptor();
    }

    @Bean
    public Advisor validResultMethodInterceptorAnnotationPointcutAdvisor(ValidResultMethodInterceptor validResultMethodInterceptor) {
        return new DefaultPointcutAdvisor(AnnotationMatchingPointcut.forMethodAnnotation(ValidResult.class),
                validResultMethodInterceptor);
    }
}
