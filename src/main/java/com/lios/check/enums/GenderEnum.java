package com.lios.check.enums;

import com.lios.check.interfaces.CheckEnumInterface;

/**
 * @author wenchao.wang
 * @description
 * @date 2018/2/8 上午11:34
 * @see CheckEnumInterface
 */

public enum GenderEnum implements CheckEnumInterface {
    WOMAN(1,"女士"),
    MAN(2,"先生");

    private  Integer value;

    private  String desc;

    GenderEnum(Integer value, String desc){
        this.value = value;
        this.desc= desc;

    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    @Override
    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static String getDesc(Integer value){
        String desc = "";
        for (GenderEnum enums : GenderEnum.values()) {
            if (enums.getValue().intValue() == value.intValue()) {
                desc = enums.getDesc();
                break;
            }
        }

        return desc;

    }


}
