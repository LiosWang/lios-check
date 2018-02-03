package com.lios.check.annotation;

import com.lios.check.enums.CheckEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wenchao.wang
 * @description
 * @date 2018/1/11 下午9:19
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckField {

     CheckEnum value() default CheckEnum.OBJ;

}
