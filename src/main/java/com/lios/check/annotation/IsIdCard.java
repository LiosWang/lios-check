package com.lios.check.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wenchao.wang
 * @description 身份证号校验
 * @date 2018/1/17 下午11:17
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsIdCard {
    String value() default "字段名称";
}
