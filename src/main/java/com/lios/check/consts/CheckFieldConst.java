package com.lios.check.consts;



/**
 * @author wenchao.wang
 * @description
 * @date 2018/1/12 下午4:56
 */
public class CheckFieldConst {

    /********************************* 检验类型 *************************************/

    public static final String CHECK_MOBILE = "校验手机";

    public static final String CHECK_IDCARD = "校验身份证";

    public static final String CHECK_NUMBER = "校验是否为数字";

    public static final String CHECK_INT = "校验是否为整数";


    /********************************* 检验提示 *************************************/

    public static final String TOTAST = "%s不能为空!";

    public static final String TOTAST_NULL = "%s为必传参数,请检查后重试!";

    public static final String MOBILE_IS_NOT_VALID = "%s用户手机号不合法!";

    public static final String ID_CARD_IS_NOT_VALID = "%s用户身份证不合法!";

    public static final String MUST_BE_IS_NUMBER = "%s必须为数字,不能含有汉子、字母或其他特殊字符!";

    public static final String MUST_BE_IS_INT = "%s必须为整数";

    public static final String AMOUNT_IS_NOT_ALLOW = "%s不能低于1000";

    public static final String ENUM_VALUE_IS_ERROR = "%s字段取值错误,枚举值应为%s";

}
