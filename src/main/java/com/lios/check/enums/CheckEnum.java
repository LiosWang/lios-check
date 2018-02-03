package com.lios.check.enums;
/**
 * @author wenchao.wang
 * @description 拦截方法参数类型说明
 * @date 2018/1/18 上午11:16
 */
public enum CheckEnum {
    /**
     * 当处理的参数为JSONObject类型时,需要通过写POJO类
     * 序列化成对象,注意:
     * POJO对象中的属性值只能为String、具体定义的JavaBean类型,
     * 这样为了防止序列化成对象时出错，比如需要类型为int,
     * 前台传字符会导致序列化失败
     */
    JSON_OBJECT,
    /**
     * 用于贷超H5参数校验
     * 当处理的参数为POJO对象时,可直接处理即可,和具体对象
     * 无关
     */
    POJO_BORROWER,
    /**
     * java对象
     */
    POJO,
    /**
     * 默认类型
     */
    OBJ
}
