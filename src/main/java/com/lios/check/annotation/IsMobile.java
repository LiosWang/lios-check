package com.lios.check.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wenchao.wang
 * @description 手机号校验
 * @date 2018/1/17 下午11:16
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsMobile {
    String value() default "字段名称";
}
