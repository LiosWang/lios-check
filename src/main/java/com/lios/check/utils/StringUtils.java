package com.lios.check.utils;
import com.lios.check.consts.ConstArrays;
import com.lios.check.consts.ConstCharacters;
import com.lios.check.consts.ConstStrings;
import org.apache.commons.lang3.ArrayUtils;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jared
 * @Description: String工具类
 * @date Nov 5, 2014 3:36:54 PM
 */
public class StringUtils {

    private static Random RANDOM = new Random(System.currentTimeMillis());

    /**
     * 字符串截取
     *
     * @param source 待截取字符串
     * @param size   保留截取长度
     * @param suffix 多余部门字符替换
     * @return
     */
    public static String truncate(String source, Integer size, String suffix) {
        if (source != null) {
            String s = substring(source, size == null ? source.length() : size);
            if (s.length() < source.length()) {
                return s + suffix;
            }
            return s;
        }
        return ConstStrings.EMPTY;
    }

    /**
     * 字符串截取
     *
     * @param source 源字符串
     * @param size   截取长度
     * @return
     */
    public static String substring(String source, int size) {
        return substring(source, 0, size);
    }

    /**
     * 字符串截取
     *
     * @param source 源字符串
     * @param offset 字符截取位子
     * @param size   截取长度
     * @return
     */
    public static String substring(String source, int offset, int size) {
        if (source == null || size < 1 || offset < 0) {
            return "";
        }

        if (source.length() <= (offset + size)) {
            return source;
        }

        return source.substring(offset, size);
    }

    /**
     * 字符串是否不为空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !StringUtils.isEmpty(str);
    }

    /**
     * 字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 字符串为null时赋""值，不为null则返回本身
     *
     * @param value 字符串
     * @return
     */
    public static String nullToEmpty(String value) {
        return value == null ? ConstStrings.EMPTY : value;
    }

    /**
     * 判断字符串不为空
     *
     * @param values 多字符串
     * @return
     */
    public static boolean isNotEmpty(String... values) {
        if (values != null && values.length > 0) {
            for (String value : values) {
                if (value == null || value.length() == 0) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 字符串为空时赋默认值，不为空则返回本身
     *
     * @param value 字符串
     * @param def   默认值
     * @return
     */
    public static String emptyToDefault(String value, String def) {
        return (value == null || value.length() == 0) ? def : value;
    }

    /**
     * 过滤表情等特殊符号
     *
     * @param source
     * @return
     */
    public static String escapeEmoji(String source) {
        if (StringUtils.isNotEmpty(source)) {
            StringBuilder buff = new StringBuilder(source.length());
            for (char codePoint : source.toCharArray()) {
                if (((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
                        || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                        || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)))) {
                    buff.append(codePoint);
                }
            }
            return buff.toString();
        }
        return source;
    }

    /**
     * 字符串转义
     *
     * @param s 源字符串
     * @param c 转义字符
     * @return
     */
    public static String decode(String s, char c) {
        if (s != null && s.length() > 0) {
            char[] cs = s.toCharArray();
            StringBuilder buff = new StringBuilder(cs.length);
            for (int i = 0, n = cs.length; i < n; i++) {
                if (!(cs[i] == ConstCharacters.BACKSLASH && i < n - 1 && cs[i + 1] == c)) {
                    buff.append(cs[i]);
                }
            }
            return buff.toString();
        }
        return s;
    }

    public static String decode(String s, char... chars) {
        if (s != null && s.length() > 0 && chars != null) {
            Arrays.sort(chars);
            char[] cs = s.toCharArray();
            StringBuilder buff = new StringBuilder(cs.length);
            for (int i = 0, n = cs.length; i < n; i++) {
                if (!(cs[i] == ConstCharacters.BACKSLASH && i < n - 1 && Arrays.binarySearch(chars, cs[i + 1]) >= 0)) {
                    buff.append(cs[i]);
                }
            }
            return buff.toString();
        }
        return s;
    }

    /**
     * 字符串加密
     *
     * @param s
     * @param chars
     * @return
     */
    public static String encode(String s, char... chars) {
        if (s != null && s.length() > 0 && chars != null) {
            Arrays.sort(chars);
            char[] cs = s.toCharArray();
            StringBuilder sb = new StringBuilder(cs.length + 10);
            for (int i = 0; i < cs.length; i++) {
                if (Arrays.binarySearch(chars, cs[i]) >= 0) {
                    sb.append(ConstCharacters.BACKSLASH);
                }
                sb.append(cs[i]);
            }
            return sb.toString();
        }
        return s;
    }

    /**
     * 生成随机字符串
     *
     * @param length 字符串长度
     * @return
     */
    public static String random(int length) {
        return random(length, ConstArrays.RANDOM_SEEDS);
    }

    /**
     * 生成随机字符串
     *
     * @param length  字符串长度
     * @param options 随机字符范围数组
     * @return
     */
    public static String random(final int length, char[] options) {
        if (length > 1) {
            int maxLength = 0;
            if (ArrayUtils.isEmpty(options)) {
                options = ConstArrays.BASE64;
                maxLength = 62;
            } else {
                maxLength = options.length;
            }
            char[] buff = new char[length];
            for (int i = 0; i < length; i++) {
                buff[i] = options[RANDOM.nextInt(maxLength)];
            }
            return new String(buff);
        }

        return ConstStrings.EMPTY;
    }

    /**
     * 字符串累加
     *
     * @param values
     * @return
     */
    public static String join(String... values) {
        return concate(values, ConstStrings.EMPTY);
    }

    /**
     * 字符串数组间添加固定字符串进行累加
     *
     * @param values    字符串数组
     * @param delimiter 字符串间隔间添加的字符
     * @return
     */
    public static String concate(String[] values, String delimiter) {
        if (values != null && values.length > 0) {
            if (delimiter == null) {
                delimiter = ConstStrings.EMPTY;
            }
            int length = 0;
            for (String value : values) {
                if (value != null) {
                    length += (value.length() + delimiter.length());
                }
            }

            StringBuilder buff = new StringBuilder(length);
            for (String value : values) {
                if (isNotEmpty(value)) {
                    if (buff.length() > 0) {
                        buff.append(delimiter);
                    }
                    buff.append(value);
                }
            }

            return buff.toString();
        }

        return ConstStrings.EMPTY;
    }

    /**
     * 判断字符串中第几位开始包含指定字符串
     *
     * @param source
     * @param sub
     * @param offset 第几位开始
     * @return
     */
    public static boolean contains(String source, String sub, int offset) {
        return isNotEmpty(source) && source.indexOf(sub, offset) >= offset;
    }

    /**
     * 字符串标准化处理
     *
     * @param value
     * @return
     */
    public static String normalize(String value) {
        if (isNotEmpty(value)) {
            return value.toLowerCase().trim();
        }

        return ConstStrings.EMPTY;
    }

    public static String sqlLike(String value) {
        if (StringUtils.isNotEmpty(value)) {
            StringBuilder builder = new StringBuilder();
            builder.append("%");
            builder.append(value);
            builder.append("%");
            return builder.toString();

        }

        return ConstStrings.EMPTY;
    }


    /**
     * 过滤非utf－8编码字符
     *
     * @param text
     * @return
     * @throws java.io.UnsupportedEncodingException
     */
    public static String filterOffUtf8Mb4(String text) {
        try {
            byte[] bytes = text.getBytes("UTF-8");
            ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
            int i = 0;
            while (i < bytes.length) {
                short b = bytes[i];
                if (b > 0) {
                    buffer.put(bytes[i++]);
                    continue;
                }
                b += 256;
                if ((b ^ 0xC0) >> 4 == 0) {
                    buffer.put(bytes, i, 2);
                    i += 2;
                } else if ((b ^ 0xE0) >> 4 == 0) {
                    buffer.put(bytes, i, 3);
                    i += 3;
                } else if ((b ^ 0xF0) >> 4 == 0) {
                    i += 4;
                }
            }
            buffer.flip();
            return new String(buffer.array(), "utf-8").trim();
        } catch (Exception e) {
            return ConstStrings.EMPTY;
        }
    }


    public static final char UNDERLINE = '_';

    /**
     * 驼峰转下划线写法
     *
     * @param param
     * @return
     */
    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线转驼峰写法
     *
     * @param param
     * @return
     */
    public static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 通过循环对象数组参数列表(中间用分隔符相隔)，获取新的字符串
     *
     * @param separator 分隔符 如："_"
     * @param arrays    如：String "abc", String "ddd"
     * @return 获取新的字符串 如： "abc_ddd"
     * @author liuting
     */
    public static String getStringByArraySplitSeparator(String separator, Object[] arrays) {
        if (isStringEmpty(separator) || arrays == null || arrays.length == 0) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder("");
        for (Object val : arrays) {
            if (val != null) {
                stringBuilder.append(val).append(separator);
            }
        }
        String targetVar = stringBuilder.toString();
        if (isStringEmpty(targetVar)) {
            return targetVar;
        }
        return targetVar.substring(0, stringBuilder.length() - separator.length());
    }

    public static boolean isStringEmpty(String str) {
        if (null == str || "".equals(str.trim())) {
            return true;
        }
        return false;
    }

    /**
     * map value是jsonstring 避免转化加转译字符
     *
     * @param map
     * @return
     */
    public static String translateMapToString(Map<String, String> map) {

        if (CollectionUtils.isEmpty(map)) {
            return "";
        }

        StringBuffer buffer = new StringBuffer();
        buffer.append("{");
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            buffer.append("\"").append(entry.getKey()).append("\":").append(entry.getValue()).append(",");
        }
        buffer.deleteCharAt(buffer.length() - 1).append("}");

        return buffer.toString();
    }

    /**
     * 判断字符串是为为整型,不能含小数
     *
     * @param str
     * @return
     */
    public static boolean isInt(String str) {
        if (str == null) {
            return false;
        }
        String regEx1 = "[\\-|\\+]?\\d+";
        Pattern p = Pattern.compile(regEx1);
        Matcher m = p.matcher(str);
        if (m.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 返回double类型的String值
     * 如小数部分为0则强转为int型String返回
     *
     * @param num
     * @return
     */
    public static String doubleDecimalStrValue(double num) {
        if (num % 1.0 == 0) {
            return String.valueOf((long) num);
        }
        return String.valueOf(num);
    }


    /**
     * 判断字符串是否是整形数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if (str == null || str.trim().equals("")) return false;
        Pattern pattern = Pattern.compile("[-]?[.0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }


    /**
     * 判断字符串是否是中文
     *
     * @param str
     * @return
     */
    public static boolean isChineseChar(String str) {
        if (str == null || str.trim().equals("")) return false;
        boolean temp = false;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            temp = true;
        }
        return temp;
    }

    /**
     * 字符串转为整型
     *
     * @param obj
     * @return
     */
    public static int strToInt(Object obj, int defaultNum) {
        if (obj == null) return defaultNum;
        String str = String.valueOf(obj);
        if (!isNumeric(str)) return defaultNum;
        try {
            return Integer.valueOf(str);
        } catch (Exception e) {
            return defaultNum;
        }
    }

    public static int strToInt(Object obj) {
        return strToInt(obj, 0);
    }

    /**
     * 通过正则表达式找到第一个匹配的字符串
     *
     * @param source
     * @param regStr
     * @return
     */
    public static String regFindFirst(String source, String regStr) {
        return regFindFirst(source, regStr, 1);
    }

    public static String regFindFirst(String source, String regStr, int groupIndex) {
//		final Pattern orderNumberPattern = Pattern.compile("(\\d+(\\.\\d+)?)");
        if (source == null || regStr == null) return "";
        try {
            final Pattern pattern = Pattern.compile(regStr);
            Matcher matcher = pattern.matcher(source);
            if (matcher.find()) {
                return matcher.group(groupIndex);
            }
        } catch (Exception e) {

        }
        return "";
    }

    /**
     * @param sourceDate
     * @param formateStr SimpleDateFormat repaymentDateF= new SimpleDateFormat("MMM dd, yyyy",Locale.ENGLISH);//英文日期
     *                   "yyyy.MM.dd G 'at' HH:mm:ss z"  2001.07.04 AD at 12:08:56 PDT
     *                   "EEE, MMM d, ''yy"  Wed, Jul 4, '01
     *                   "h:mm annotation"  12:08 PM
     *                   "hh 'o''clock' annotation, zzzz"  12 o'clock PM, Pacific Daylight Time
     *                   "K:mm annotation, z"  0:08 PM, PDT
     *                   "yyyyy.MMMMM.dd GGG hh:mm aaa"  02001.July.04 AD 12:08 PM
     *                   "EEE, d MMM yyyy HH:mm:ss Z"  Wed, 4 Jul 2001 12:08:56 -0700
     *                   "yyMMddHHmmssZ"  010704120856-0700
     *                   "yyyy-MM-dd'T'HH:mm:ss.SSSZ"  2001-07-04T12:08:56.235-0700
     * @return
     */
    public static Date strToDateForEn(String sourceDate, String formateStr) {

        SimpleDateFormat dateF = new SimpleDateFormat(formateStr, Locale.ENGLISH);
        try {
            return dateF.parse(sourceDate);
        } catch (Exception e) {

        }

        return null;

    }

    /**
     * 产品随机数字
     *
     * @param maxLen
     * @return
     */
    public static String randNum(int maxLen) {
        if (maxLen <= 0) maxLen = 4;
        StringBuilder retStr = new StringBuilder("");
        for (int i = 0; i < maxLen; i++) {
            int max = 9;
            int min = 0;
            java.util.Random random = new java.util.Random();

            int s = random.nextInt(max) % (max - min + 1) + min;
            retStr.append(String.valueOf(s));
        }

        return retStr.toString();

    }

    /**
     * 去除特殊字符分隔的空字符
     *
     * @param inStr
     * @param splitStr
     * @return
     */
    public static String trimSplit(String inStr, String splitStr) {
        if (inStr == null || inStr.trim().equals("")) return "";
        if (splitStr == null || splitStr.trim().equals("")) return inStr;
        if (inStr.indexOf(splitStr) < 0) return inStr;

        String[] tempArray = inStr.split(splitStr);
        return trimSplit(tempArray, splitStr);


    }

    public static String trimSplit(String[] inArray, String splitStr) {
        if (inArray == null) return "";
        if (splitStr == null || splitStr.trim().equals("")) splitStr = ",";
        StringBuilder tempBu = new StringBuilder();

        for (String temp : inArray) {
            if (temp.trim().equals("")) continue;
            if (tempBu.length() > 0) {
                tempBu.append(splitStr);
            }
            tempBu.append(temp);
        }

        return tempBu.toString();
    }

    /**
     * 数字金额大写转换，思想先写个完整的然后将如零拾替换成零
     * 要用到正则表达式
     */
    public static String digitUppercase(double n) {
        String fraction[] = {"角", "分"};
        String digit[] = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
        String unit[][] = {{"元", "万", "亿"},
                {"", "拾", "佰", "仟"}};

        String head = n < 0 ? "负" : "";
        n = Math.abs(n);

        String s = "";
        for (int i = 0; i < fraction.length; i++) {
            s += (digit[(int) (Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(零.)+", "");
        }
        if (s.length() < 1) {
            s = "整";
        }
        int integerPart = (int) Math.floor(n);

        for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
            String p = "";
            for (int j = 0; j < unit[1].length && n > 0; j++) {
                p = digit[integerPart % 10] + unit[1][j] + p;
                integerPart = integerPart / 10;
            }
            s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
        }
        return head + s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
    }

    public static void main(String[] args) {
        System.out.println(camelToUnderline("updatedAt"));
        System.out.println(underlineToCamel("updated_at"));
    }


}
