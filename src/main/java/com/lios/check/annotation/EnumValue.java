package com.lios.check.annotation;

import com.lios.check.interfaces.CheckEnumInterface;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wenchao.wang
 * @description 枚举值判断
 * @date 2018/1/30 下午10:36
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumValue {
    Class<? extends CheckEnumInterface> value();
    String name() default "字段名称";
}
