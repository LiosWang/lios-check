package com.lios.check.utils;

/**
 * @author wenchao.wang
 * @description
 * @date 2018/2/9 下午5:16
 */

import com.lios.check.consts.ConstArrays;
import com.lios.check.consts.ConstStrings;

import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public final class ArrayUtils {

    public static boolean isEmpty(Object[] objs) {
        return objs == null || objs.length == 0;
    }

    public static boolean isNotEmpty(Object[] objs) {
        return objs != null && objs.length > 0;
    }

    public static boolean isEmpty(byte[] bytes) {
        return bytes == null || bytes.length == 0;
    }

    public static boolean isEmpty(char[] chars) {
        return chars == null || chars.length == 0;
    }

    public static boolean isNotEmpty(byte[] bytes) {
        return bytes != null && bytes.length > 0;
    }

    public static boolean isEmpty(int[] ints) {
        return ints == null || ints.length == 0;
    }

    public static boolean isNotEmpty(int[] ints) {
        return ints != null && ints.length > 0;
    }

    public static boolean isEmpty(long[] longs) {
        return longs == null || longs.length == 0;
    }

    public static boolean isNotEmpty(long[] longs) {
        return longs != null && longs.length > 0;
    }

    public static <T> T[] emptyToDefault(T[] values, T[] def) {
        return values == null || values.length == 0 ? def : values;
    }

    public static byte[] emptyToDefault(byte[] values, byte[] def) {
        return values == null || values.length == 0 ? def : values;
    }

    public static short[] emptyToDefault(short[] values, short[] def) {
        return values == null || values.length == 0 ? def : values;
    }

    public static int[] emptyToDefault(int[] values, int[] def) {
        return values == null || values.length == 0 ? def : values;
    }

    public static long[] emptyToDefault(long[] values, long[] def) {
        return values == null || values.length == 0 ? def : values;
    }

    public static float[] emptyToDefault(float[] values, float[] def) {
        return values == null || values.length == 0 ? def : values;
    }

    public static double[] emptyToDefault(double[] values, double[] def) {
        return values == null || values.length == 0 ? def : values;
    }

    public static Byte[] boxing(byte[] bytes) {
        if (bytes != null && bytes.length > 0) {
            Byte[] objs = new Byte[bytes.length];
            for (int i = 0; i < bytes.length; i++) {
                objs[i] = bytes[i];
            }
            return objs;
        }
        return null;
    }

    public static Short[] boxing(short[] shorts) {
        if (shorts != null && shorts.length > 0) {
            Short[] objs = new Short[shorts.length];
            for (int i = 0; i < shorts.length; i++) {
                objs[i] = shorts[i];
            }
            return objs;
        }
        return null;
    }

    public static Integer[] boxing(int[] ints) {
        if (ints != null && ints.length > 0) {
            Integer[] objs = new Integer[ints.length];
            for (int i = 0; i < ints.length; i++) {
                objs[i] = ints[i];
            }
            return objs;
        }
        return null;
    }

    public static Long[] boxing(long[] longs) {
        if (longs != null && longs.length > 0) {
            Long[] objs = new Long[longs.length];
            for (int i = 0; i < longs.length; i++) {
                objs[i] = longs[i];
            }
            return objs;
        }
        return null;
    }

    public static Float[] boxing(float[] floats) {
        if (floats != null && floats.length > 0) {
            Float[] objs = new Float[floats.length];
            for (int i = 0; i < floats.length; i++) {
                objs[i] = floats[i];
            }
            return objs;
        }
        return null;
    }

    public static Double[] boxing(double[] doubles) {
        if (doubles != null && doubles.length > 0) {
            Double[] objs = new Double[doubles.length];
            for (int i = 0; i < doubles.length; i++) {
                objs[i] = doubles[i];
            }
            return objs;
        }
        return null;
    }

    public static String toString(Object[] objs) {
        if (objs != null && objs.length > 0) {
            StringBuilder buff = new StringBuilder(64);
            for (Object obj : objs) {
                if (obj != null) {
                    String str = obj.toString();
                    if (str != null && str.length() > 0) {
                        if (buff.length() > 0) {
                            buff.append(ConstStrings.COMMA);
                        }

                        buff.append(str);
                    }
                }
            }
            return buff.toString();
        }
        return ConstStrings.EMPTY;
    }

    public static String toString(int[] ints) {
        if (ints != null && ints.length > 0) {
            StringBuilder buff = new StringBuilder(64);
            for (int i : ints) {
                if (buff.length() > 0) {
                    buff.append(ConstStrings.COMMA);
                }

                buff.append(i);
            }
            return buff.toString();
        }
        return ConstStrings.EMPTY;
    }

    public static String toString(long[] longs) {
        if (longs != null && longs.length > 0) {
            StringBuilder buff = new StringBuilder(64);
            for (long l : longs) {
                if (buff.length() > 0) {
                    buff.append(ConstStrings.COMMA);
                }

                buff.append(l);
            }
            return buff.toString();
        }
        return ConstStrings.EMPTY;
    }

    public static int[] toInts(String str) {
        if (str != null && str.length() > 0) {
            StringTokenizer st = new StringTokenizer(str, ConstStrings.COMMA);
            int[] objs = new int[st.countTokens()];
            int i = 0;
            while (st.hasMoreTokens()) {
                try {
                    objs[i++] = Integer.parseInt(st.nextToken());
                } catch (Throwable e) {
                }
            }

            return objs;
        }
        return null;
    }

    public static Integer[] toIntegers(String str) {
        if (str != null && str.length() > 0) {
            StringTokenizer st = new StringTokenizer(str, ConstStrings.COMMA);
            Integer[] objs = new Integer[st.countTokens()];
            int i = 0;
            while (st.hasMoreTokens()) {
                try {
                    objs[i++] = Integer.parseInt(st.nextToken());
                } catch (Throwable e) {
                }
            }

            return objs;
        }
        return null;
    }

    public static Long[] toLongs(String str) {
        if (str != null && str.length() > 0) {
            StringTokenizer st = new StringTokenizer(str, ConstStrings.COMMA);
            Long[] objs = new Long[st.countTokens()];
            int i = 0;
            while (st.hasMoreTokens()) {
                try {
                    objs[i++] = Long.parseLong(st.nextToken());
                } catch (Throwable e) {
                }
            }

            return objs;
        }
        return null;
    }

    public static String[] toStrings(String str) {
        List<String> strs = CollectionUtils.toStrings(str);
        if (strs != null) {
            return strs.toArray(ConstArrays.EMPTY_STRING);
        }
        return null;
    }

    public static char[] subarray(char[] source, int from, int to) {
        if (source != null && from >= 0 && to <= source.length) {
            return Arrays.copyOfRange(source, from, to);
        }
        return ConstArrays.EMPTY_PRIMITIVE_CHARACTER;
    }

    public static int[] append(final int[] target, final int val) {
        if (target == null) {
            return new int[] { val };
        }

        int[] result = new int[target.length + 1];
        for (int i = 0; i < target.length; i++) {
            result[i] = target[i];
        }
        result[target.length] = val;
        return result;
    }

    public static int[] insert(int position, final int[] target, final int val) {
        if (target == null) {
            return new int[] { val };
        }

        if (position < 0 || position > target.length) {
            throw new ArrayIndexOutOfBoundsException();
        }

        int[] result = new int[target.length + 1];
        for (int i = 0; i < target.length; i++) {
            if (i < position) {
                result[i] = target[i];
            } else {
                result[i + 1] = target[i];
            }
        }
        result[position] = val;
        return result;
    }

    public static int[] append(int[] s1, int[] s2) {
        if (isEmpty(s1)) {
            return s2;
        }

        if (isEmpty(s2)) {
            return s1;
        }

        int[] s3 = new int[s1.length + s2.length];

        System.arraycopy(s1, 0, s3, 0, s1.length);
        System.arraycopy(s2, 0, s3, s1.length, s2.length);

        return s3;
    }
}
