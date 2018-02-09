package com.lios.check.handle;

import java.lang.reflect.Field;

/**
 * @author wenchao.wang
 * @description
 * @date 2018/2/9 下午3:32
 */
public interface Handle<T extends RuntimeException> {

    void handle(Field f, Object obj);

    /**
     * 校验非空 键值不存在、或空字符
     *
     * @param f
     * @param obj
     */
    void checkNotEmpty(Field f, Object obj);

    /**
     * 校验手机
     *
     * @param f
     * @param obj
     */
    void checkMobile(Field f, Object obj);

    /**
     * 校验身份证号
     *
     * @param f
     * @param obj
     */
    void checkIdCard(Field f, Object obj);

    /**
     * 校验是否为数字
     *
     * @param f
     * @param obj
     */
    void checkNumber(Field f, Object obj);

    /**
     * 检验是否为整型
     *
     * @param f
     * @param obj
     */
    void checkIsInt(Field f, Object obj);

    /**
     * 校验借款金额大小
     *
     * @param f
     * @param obj
     */
    void checkIsAmountLimit(Field f, Object obj);

    /**
     * 校验枚举值是否合法
     *
     * @param f
     * @param obj
     */
    void checkEnumValue(Field f, Object obj);
}
