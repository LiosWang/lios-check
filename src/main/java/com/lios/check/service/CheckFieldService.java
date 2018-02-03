package com.lios.check.service;
import com.lios.check.annotation.*;
import com.lios.check.consts.CheckFieldConst;
import com.lios.check.utils.IdCardUtil;
import com.lios.check.utils.MobileUtil;
import com.lios.check.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import com.lios.check.enums.CheckEnum;
/**
 * @author wenchao.wang
 * @description 参数信息校验
 * @date 2018/1/11 下午9:20
 * @see CheckField
 * @see CheckEnum
 * @see NotEmpty
 * @see IsNumber
 * @see IsIdCard
 * @see IsMobile
 * @see IsNumber
 * @see IsInt
 * @see EnumValue
 * @see AmountLimit
 */
public class CheckFieldService<T extends RuntimeException>{

    public void check(Object obj) throws Exception {
        Class<?> cls = obj.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            Object o = f.get(obj);
            if (isSimpleType(f)) {
                dispatcher(f, o);
            } else {
                if (Objects.isNull(o)) {
                    dispatcher(f, o);
                } else {
                    // TODO: 递归校验
                    check(o);
                }
            }
        }
    }

    private boolean isSimpleType(Field f) {
        List<Class> list = Arrays.asList(new Class[]{boolean.class, byte.class, double.class, float.class, int.class, long.class, short.class, String.class, Long.class, Double.class, Boolean.class, Integer.class, BigDecimal.class, Byte.class});
        for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
            if (f.getType().isAssignableFrom((Class<?>) iterator.next())) {
                return true;
            }
        }
        return false;
    }

    private void dispatcher(Field f, Object obj) {
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
    private void checkNotEmpty(Field f, Object obj) {
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
    private void checkMobile(Field f, Object obj) {
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
    private void checkIdCard(Field f, Object obj) {
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
    private void checkNumber(Field f, Object obj) {
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
    private void checkIsInt(Field f, Object obj) {
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
    private void checkIsAmountLimit(Field f, Object obj) {
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
    private void checkEnumValue(Field f, Object obj) {
        if (!Objects.isNull(obj)) {
            EnumValue enumValue = f.getAnnotation(EnumValue.class);
            String[] values = (enumValue.value()).split(",");
            if (!ArrayUtils.contains(values, obj)) {
                throw (T) (new RuntimeException(String.format(CheckFieldConst.ENUM_VALUE_IS_ERROR, enumValue.name(),enumValue.value())));
            }
        }
    }
}
