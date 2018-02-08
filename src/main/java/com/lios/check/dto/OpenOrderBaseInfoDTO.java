package com.lios.check.dto;

import com.lios.check.annotation.*;
import com.lios.check.enums.GenderEnum;
import lombok.Data;

/**
 * @author wenchao.wang
 * @description
 * @date 2018/1/12 上午1:57
 * @see NotEmpty
 * @see IsInt
 * @see IsMobile
 * @see IsIdCard
 * @see IsNumber
 * @see EnumValue
 */
@Data
public class OpenOrderBaseInfoDTO {

    @NotEmpty(value = "user_Name")
    private String userName;

    @NotEmpty(value = "age")
    @IsInt(value = "age")
    private String age;

    @NotEmpty(value = "gender")
    @IsInt(value = "gender")
    @EnumValue(value = GenderEnum.class,name = "gender")
    private String gender;
    @IsInt(value = "user_education")
    private String userEducation;

    @NotEmpty(value = "city_code")
    private String cityCode;
    @IsInt(value = "user_marriage")
    private String userMarriage;

    @NotEmpty(value = "user_mobile")
    @IsMobile(value = "user_mobile")
    private String userMobile;

    @NotEmpty(value = "id_card")
    @IsIdCard(value = "id_card")
    private String idCard;

    @NotEmpty(value ="registed")
    private String registed;

    @NotEmpty(value = "hukou_type")
    @IsInt(value = "hukou_type")
    private String hukouType;

    @NotEmpty(value = "mobile_registed")
    private String mobileRegisted;

    @NotEmpty(value = "amount")
    @IsNumber(value = "amount")
    @AmountLimit(value = "amount")
    private String amount;

    @NotEmpty(value = "period")
    @IsInt(value = "period")
    private String period;

    @NotEmpty(value = "unit")
    @IsInt(value = "unit")
    private String unit;

    @NotEmpty(value = "is_op_type")
    @IsInt(value = "is_op_type")
    private String isOpType;

    @NotEmpty(value = "money_use")
    @IsInt(value = "money_use")
    private String moneyUse;
    @IsInt(value = "residence_type")
    private String residenceType;

    private String liveAddress;
    @IsInt(value = "live_time")
    private String liveTime;

    @NotEmpty(value = "career_type")
    @IsInt(value = "career_type")
    private String careerType;
}
