package com.lios.check.service;

import com.lios.check.annotation.*;
import com.lios.check.enums.CheckEnum;
import com.lios.check.handle.CheckHandle;
import com.lios.check.handle.Handle;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

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
 * @see
 * @see AmountLimit
 */
public class CheckFieldService<T extends RuntimeException> {

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

    private void dispatcher(Field f, Object obj){
        Handle<T> handle = new CheckHandle<T>();
        handle.handle(f, obj);
    }
}
