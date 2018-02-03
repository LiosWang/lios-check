package com.lios.check.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class MobileUtil {


    private static final Pattern MobilePattern = Pattern.compile("^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|70|79)\\d{8}$");
    private static final Pattern CMMobilePattern = Pattern.compile("(^1(3[4-9]|4[7]|5[0-27-9]|7[58]|8[2-478])\\d{8}$)|(^1705\\d{7}$)");
    private static final Pattern CUMobilePattern = Pattern.compile("(^1(3[0-2]|4[5]|5[56]|7[56]|8[56]|6[6])\\d{8}$)|(^1709\\d{7}$)");
    private static final Pattern CTMobilePattern = Pattern.compile("(^1(33|53|73|77|75|8[019])\\d{8}$)|(^1700\\d{7}$)");
    private static final Pattern QQPattern = Pattern.compile("[1-9][0-9]{4,14}");

    /**
     * 手机号码验证
     * 考虑以后号码段会变化，所以15和18写的范围就比较大，可能有些号段现在还没有
     *
     * @param mobile 手机号码
     * @return true/false
     */
    public static boolean isMobile(String mobile) {
        return MobilePattern.matcher(mobile).matches() || CMMobilePattern.matcher(mobile).matches() || CUMobilePattern.matcher(mobile).matches() || CTMobilePattern.matcher(mobile).matches();

    }

    public static boolean isQQ(String qq) {
        return QQPattern.matcher(qq).matches();
    }

    public static String hidden(String mobile) {
        if (StringUtils.isNotEmpty(mobile)) {
            mobile = mobile.substring(0, 3) + "****" + mobile.substring(7, 11);
            return mobile;
        }
        return null;
    }


    public static String TowHidden(String mobile) {
        if (StringUtils.isNotEmpty(mobile)) {
            mobile = mobile.substring(0, 3) + "******" + mobile.substring(9, 11);
            return mobile;
        }
        return null;
    }


    /**
     * imei编号由15位数字组成，
     * 前6位(TAC)是型号核准号码，代表手机类型。
     * 接着2位(FAC)是最后装配号，代表产地。
     * 后6位(SNR)是串号，代表生产顺序号。
     * 最后1位 (SP)是检验码。
     * <p>
     * 检验码计算：
     * (1).将偶数位数字分别乘以2，分别计算个位数和十位数之和
     * (2).将奇数位数字相加，再加上上一步算得的值
     * (3).如果得出的数个位是0则校验位为0，否则为10减去个位数
     *
     * @author sonzer
     */
    public static String creatImeiCode() {
        //864710 02 131998 2
        String imeiString = "86471002" + com.lios.check.utils.StringUtils.randNum(6);//前14位
        char[] imeiChar = imeiString.toCharArray();
        int resultInt = 0;
        for (int i = 0; i < imeiChar.length; i++) {
            int a = Integer.parseInt(String.valueOf(imeiChar[i]));
            i++;
            final int temp = Integer.parseInt(String.valueOf(imeiChar[i])) * 2;
            final int b = temp < 10 ? temp : temp - 9;
            resultInt += a + b;
        }
        resultInt %= 10;
        resultInt = resultInt == 0 ? 0 : 10 - resultInt;
//        System.out.println("imei:"+imeiString+resultInt);  
        return imeiString + String.valueOf(resultInt);
    }

    /**
     * 规则化手机号
     *
     * @param phone
     * @return
     */
    public static String filterPhone(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return "";
        }
        String resultPhone = phone;
        if (resultPhone.startsWith("86")) {
            resultPhone = resultPhone.substring(2, phone.length());
        } else if (phone.startsWith("*86") || phone.startsWith("+86")) {
            resultPhone = resultPhone.substring(3, phone.length());
        }
        resultPhone = resultPhone.replace("-", "");
        resultPhone = resultPhone.replace(" ", "");
        resultPhone = resultPhone.replace("*", "");
        return resultPhone;
    }

}
