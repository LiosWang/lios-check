package com.lios.check.annotation;

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
    String value() default "枚举值value,以,分开,枚举值改变一定得及时更新";
    String name() default "字段名称";
}
