package com.lios.check.utils;

import com.lios.check.consts.ConstCharacters;
import com.lios.check.consts.ConstStrings;

import java.util.*;

/**
 * @author wenchao.wang
 * @description
 * @date 2018/2/9 下午5:14
 */
@SuppressWarnings("unchecked")
public final class CollectionUtils {

    /**
     * 判断collection对象是否不为空
     *
     * @param col
     *            collection对象
     * @return
     */
    public static boolean isNotEmpty(Collection<? extends Object> col) {
        return col != null && col.size() > 0;
    }

    /**
     * 判断collection对象是否为空
     *
     * @param col
     *            collection对象
     * @return
     */
    public static boolean isEmpty(Collection<? extends Object> col) {
        return !isNotEmpty(col);
    }

    /**
     * 判断map对象是否为空
     *
     * @param map
     *            map对象
     * @return
     */
    public static boolean isNotEmpty(Map<? extends Object, ? extends Object> map) {
        return map != null && map.size() > 0;
    }

    public static <T> T get(Map<String, Object> map, String key) {
        return get(map, key, null);
    }

    public static <T> T get(Map<String, Object> map, String key, T def) {
        if (map != null && key != null) {
            return (T) map.get(key);
        }
        return def;
    }

    public static boolean isEmpty(Map<? extends Object, ? extends Object> map) {
        return map == null || map.isEmpty();
    }

    public static List<String> toStrings(String str) {
        return toStrings(str, ConstCharacters.COMMA);
    }

    public static List<String> toStrings(String str, char delimeter) {
        if (str != null && str.length() > 0) {
            boolean should_decode = false;
            ArrayList<String> strs = new ArrayList<String>(16);
            int maxPosition = str.length() - 1, pos = -1, cur = -1;
            while (pos < maxPosition) {
                pos++;
                if (str.charAt(pos) == delimeter) {
                    if (pos != 0 && str.charAt(pos - 1) == ConstCharacters.BACKSLASH) {
                        should_decode = true;
                    } else {
                        if (should_decode) {
                            strs.add(StringUtils.decode(str.substring(cur + 1, pos), delimeter));
                            should_decode = false;
                        } else {
                            strs.add(str.substring(cur + 1, pos));
                        }
                        cur = pos;
                    }
                }
            }
            if (should_decode) {
                strs.add(StringUtils.decode(str.substring(cur + 1), delimeter));
            } else {
                strs.add(str.substring(cur + 1));
            }
            return strs;
        }
        return null;
    }

    public static <T> String toString(Collection<T> values, char delimeter) {
        if (values != null && values.size() > 0) {
            StringBuilder buff = new StringBuilder();
            for (T value : values) {
                if (value != null) {
                    String str = value.toString();
                    if (str != null && str.length() > 0) {
                        if (delimeter > 0 && buff.length() > 0) {
                            buff.append(delimeter);
                        }

                        buff.append(StringUtils.encode(str, delimeter));
                    }
                }
            }
            return buff.toString();
        }
        return ConstStrings.EMPTY;
    }

    public static <T> String toString(Collection<T> values) {
        return toString(values, ConstCharacters.COMMA);
    }

    public static Set<String> parseSet(String str) {
        return parseSet(str, false);
    }

    public static Set<String> parseSet(String str, boolean linked) {
        return parseSet(str, linked, true);
    }

    public static Set<String> parseSet(String str, boolean linked, boolean createNewIfEmpty) {
        if (StringUtils.isNotEmpty(str)) {
            String[] items = ArrayUtils.toStrings(str);
            if (ArrayUtils.isNotEmpty(items)) {
                Set<String> result = linked ? new LinkedHashSet<String>() : new HashSet<String>();
                for (String item : items) {
                    if (StringUtils.isNotEmpty(item)) {
                        result.add(item);
                    }
                }
                return result;
            }
        }

        if (createNewIfEmpty) {
            return linked ? new LinkedHashSet<String>() : new HashSet<String>();
        }

        return Collections.emptySet();
    }

    public static Set<Integer> parseIntSet(String str) {
        return parseIntSet(str, true);
    }

    public static Set<Integer> parseIntSet(String str, boolean linked) {
        return parseIntSet(str, linked, false);
    }

    public static Set<Integer> parseIntSet(String str, boolean linked, boolean createNewIfEmpty) {
        if (str != null) {
            Integer[] values = ArrayUtils.toIntegers(str);
            if (values != null) {
                Set<Integer> result = linked ? new LinkedHashSet<Integer>() : new HashSet<Integer>();
                for (Integer value : values) {
                    if (value != null) {
                        result.add(value);
                    }
                }
                return result;
            }
        }

        if (createNewIfEmpty) {
            return linked ? new LinkedHashSet<Integer>() : new HashSet<Integer>();
        }

        return Collections.emptySet();
    }

    public static <K, V> Map<K, V> toMap(K key, V value) {
        if (key != null) {
            Map<K, V> map = new HashMap<K, V>();
            map.put(key, value);
            return map;
        }
        return Collections.emptyMap();
    }

    public static Map<String, String> parseMap(String str) {
        return parseMap(str, false);
    }

    public static Map<String, String> parseMap(String str, boolean linked) {
        return parseMap(str, linked, true);
    }

    public static Map<String, String> parseMap(String str, boolean linked, boolean createNewIfEmpty) {
        if (StringUtils.isNotEmpty(str)) {
            String[] pairs = ArrayUtils.toStrings(str);
            if (ArrayUtils.isNotEmpty(pairs)) {
                Map<String, String> result = linked ? new LinkedHashMap<String, String>()
                        : new HashMap<String, String>();
                for (int i = 0; i < pairs.length; i++) {
                    try {
                        int index = pairs[i].indexOf("=");
                        if (index == -1) {
                            return result;
                        }
                        String key = StringUtils.decode(pairs[i].substring(0, index), ',', '=');
                        if (index == pairs[i].length() || index < 0) {
                            result.put(key, "");
                        } else {
                            result.put(key, pairs[i].substring(index + 1));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                return result;
            }
        }

        if (createNewIfEmpty) {
            return linked ? new LinkedHashMap<String, String>() : new HashMap<String, String>();
        }

        return Collections.emptyMap();
    }

    public static <T> String toString(Map<String, T> dictionary) {
        if (isNotEmpty(dictionary)) {
            final StringBuilder buff = new StringBuilder();
            for (Map.Entry<String, T> entry : dictionary.entrySet()) {
                try {
                    if (StringUtils.isNotEmpty(entry.getKey()) && entry.getValue() != null) {
                        if (buff.length() > 0) {
                            buff.append(",");
                        }
                        buff.append(StringUtils.encode(entry.getKey(), ',', '='));
                        buff.append("=");
                        buff.append(StringUtils.encode(entry.getValue().toString(), ',', '='));
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            return buff.toString();
        }

        return ConstStrings.EMPTY;
    }

    public static Set<Long> parseLongSet(String str) {
        return parseLongSet(str, true);
    }

    public static Set<Long> parseLongSet(String str, boolean linked) {
        return parseLongSet(str, linked, false);
    }

    public static Set<Long> parseLongSet(String str, boolean linked, boolean createNewIfEmpty) {
        if (StringUtils.isNotEmpty(str)) {
            Long[] values = ArrayUtils.toLongs(str);
            if (ArrayUtils.isNotEmpty(values)) {
                Set<Long> result = linked ? new LinkedHashSet<Long>() : new HashSet<Long>();
                for (Long value : values) {
                    if (value != null) {
                        result.add(value);
                    }
                }
                return result;
            }
        }

        if (createNewIfEmpty) {
            return linked ? new LinkedHashSet<Long>() : new HashSet<Long>();
        }

        return Collections.emptySet();
    }

    public static <T> Collection<T> intersect(Collection<T> set1, Collection<T> set2) {
        if (isEmpty(set1) || isEmpty(set2)) {
            return Collections.emptyList();
        }

        Set<T> intersection = new LinkedHashSet<T>();
        for (T item : set1) {
            if (item != null && set2.contains(item)) {
                intersection.add(item);
            }
        }
        return intersection;
    }

    public static <T> T getFirst(Collection<T> set) {
        if (isNotEmpty(set)) {
            for (T item : set) {
                if (item != null) {
                    return item;
                }
            }
        }
        return null;
    }

    public static Map<String, Object> normalize(Map<?, ?> map) {
        return (Map<String, Object>) map;
    }

    /**
     * Set转List
     *
     * @param set
     * @return
     */
    public static <T> List<T> setToList(Set<T> set) {
        if (isNotEmpty(set)) {
            List<T> list = new ArrayList<T>();
            for (T t : set) {
                list.add(t);
            }
            return list;
        }
        return Collections.emptyList();
    }

    public static String join(List<?> list, String sepe) {
        StringBuilder sb = new StringBuilder();
        if (isNotEmpty(list)) {
            for (Object o : list) {
                sb.append(o.toString()).append(sepe);
            }
            return sb.deleteCharAt(sb.length() - 1).toString();
        }
        return "";
    }
}
