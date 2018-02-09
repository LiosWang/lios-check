package com.lios.check.handle;

import com.lios.check.annotation.*;
import com.lios.check.consts.CheckFieldConst;
import com.lios.check.interfaces.CheckEnumInterface;
import com.lios.check.utils.IdCardUtil;
import com.lios.check.utils.MobileUtil;
import com.lios.check.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author wenchao.wang
 * @description
 * @date 2018/2/9 下午3:34
 */
public class CheckHandle<T extends RuntimeException> implements Handle {
    public CheckHandle(){

    }
    @Override
    public void handle(Field f, Object obj) {
        if (f.isAnnotationPresent(NotEmpty.class)) {
            checkNotEmpty(f, obj);
        }
        if (f.isAnnotationPresent(IsMobile.class)) {
            checkMobile(f, obj);
        }
        if (f.isAnnotationPresent(IsIdCard.class)) {
            checkIdCard(f, obj);
        }
        if (f.isAnnotationPresent(IsNumber.class)) {
            checkNumber(f, obj);
        }
        if (f.isAnnotationPresent(IsInt.class)) {
            checkIsInt(f, obj);
        }
        if (f.isAnnotationPresent(AmountLimit.class)) {
            checkIsAmountLimit(f, obj);
        }
        if (f.isAnnotationPresent(EnumValue.class)) {
            checkEnumValue(f, obj);
        }
    }

    /**
     * 校验非空 键值不存在、或空字符
     *
     * @param f
     * @param obj
     */
    @Override
    public void checkNotEmpty(Field f, Object obj) {
        NotEmpty notEmpty = f.getAnnotation(NotEmpty.class);
        // TODO: 键值不存在
        if (Objects.isNull(obj)) {
            throw (T) new RuntimeException(String.format(CheckFieldConst.TOTAST_NULL, notEmpty.value()));
        }
        // TODO: 空字符
        if (StringUtils.isEmpty(obj.toString())) {
            throw new RuntimeException(String.format(CheckFieldConst.TOTAST, notEmpty.value()));
        }
    }

    /**
     * 校验手机
     *
     * @param f
     * @param obj
     */
    @Override
    public void checkMobile(Field f, Object obj) {
        if (!MobileUtil.isMobile(obj.toString())) {
            IsMobile isMobile = f.getAnnotation(IsMobile.class);
            throw (T) new RuntimeException(String.format(CheckFieldConst.MOBILE_IS_NOT_VALID, isMobile.value()));
        }
    }

    /**
     * 校验身份证号
     *
     * @param f
     * @param obj
     */
    @Override
    public void checkIdCard(Field f, Object obj) {
        if (!IdCardUtil.isIDCard(obj.toString())) {
            IsIdCard isIdCard = f.getAnnotation(IsIdCard.class);
            throw (T) new RuntimeException(String.format(CheckFieldConst.ID_CARD_IS_NOT_VALID, isIdCard.value()));
        }
    }

    /**
     * 校验是否为数字
     *
     * @param f
     * @param obj
     */
    @Override
    public void checkNumber(Field f, Object obj) {
        if (!Objects.isNull(obj) && !StringUtils.isNumeric(obj.toString())) {
            IsNumber isNumber = f.getAnnotation(IsNumber.class);
            throw (T) new RuntimeException(String.format(CheckFieldConst.MUST_BE_IS_NUMBER, isNumber.value()));
        }
    }

    /**
     * 检验是否为整型
     *
     * @param f
     * @param obj
     */
    @Override
    public void checkIsInt(Field f, Object obj) {
        if (!Objects.isNull(obj) && !StringUtils.isInt(obj.toString())) {
            IsInt isInt = f.getAnnotation(IsInt.class);
            throw (T) (new RuntimeException(String.format(CheckFieldConst.MUST_BE_IS_INT, isInt.value())));
        }
    }

    /**
     * 校验借款金额大小
     *
     * @param f
     * @param obj
     */
    @Override
    public void checkIsAmountLimit(Field f, Object obj) {
        if (!Objects.isNull(obj)) {
            int compare = new BigDecimal(String.valueOf(obj)).compareTo(new BigDecimal(String.valueOf(1000)));
            if (Objects.equals(-1, compare)) {
                AmountLimit amountLimit = f.getAnnotation(AmountLimit.class);
                throw (T) (new RuntimeException(String.format(CheckFieldConst.AMOUNT_IS_NOT_ALLOW, amountLimit.value())));
            }
        }
    }

    /**
     * 校验枚举值是否合法
     *
     * @param f
     * @param obj
     */
    @Override
    public void checkEnumValue(Field f, Object obj) {
        if (!Objects.isNull(obj)) {
            EnumValue enumValue = f.getAnnotation(EnumValue.class);
            StringBuilder builder = new StringBuilder();
            for (CheckEnumInterface c : enumValue.value().getEnumConstants()) {
                builder.append(c.getValue() + ",");
            }
            if (!ArrayUtils.contains(builder.toString().split(","), obj)) {
                throw (T) (new RuntimeException(String.format(CheckFieldConst.ENUM_VALUE_IS_ERROR, enumValue.name(), builder.deleteCharAt(builder.length() - 1))));
            }
        }
    }
}
