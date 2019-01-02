package com.oristartech.marketing.components.config;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @Auther: hexu
 * @Date: 2018/10/15 16:38
 * @Description:
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CacheExpire {


    /**
     * expire time, default 30m
     */
    @AliasFor("expire")
    long value() default 60 * 30;

    /**
     * expire time, default 30m
     */
    @AliasFor("value")
    long expire() default 60 * 30;

}
