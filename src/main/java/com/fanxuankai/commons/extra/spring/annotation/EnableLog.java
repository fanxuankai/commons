package com.fanxuankai.commons.extra.spring.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启 Log 注解
 *
 * @author fanxuankai
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({LogPointcutAdvisor.class})
public @interface EnableLog {
}
