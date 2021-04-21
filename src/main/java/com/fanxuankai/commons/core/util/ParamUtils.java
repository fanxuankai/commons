package com.fanxuankai.commons.core.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 参数校验
 *
 * @author fanxuankai
 */
public class ParamUtils {
    private ParamUtils() {
    }

    /**
     * 判断参数是否为空
     *
     * @param param 参数
     * @return /
     */
    public static boolean isEmpty(Object param) {
        return ObjectUtil.isEmpty(param);
    }

    /**
     * 判断参数是否为空
     *
     * @param param        参数
     * @param runIfChecked 校验通过时执行任务
     */
    public static void isEmpty(Object param, Runnable runIfChecked) {
        if (ObjectUtil.isEmpty(param)) {
            runIfChecked.run();
        }
    }

    /**
     * 判断是否含有空元素
     *
     * @param param 参数
     * @return /
     */
    public static boolean hasEmptyElement(Iterable<?> param) {
        for (Object o : param) {
            if (isEmpty(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断参数是否为空
     *
     * @param params 多个参数
     * @return /
     */
    public static boolean hasEmpty(Object... params) {
        for (Object o : params) {
            if (isEmpty(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断参数是否不为空
     *
     * @param param 参数
     * @return /
     */
    public static boolean isNotEmpty(Object param) {
        return ObjectUtil.isNotEmpty(param);
    }

    /**
     * 判断参数是否不为空
     *
     * @param param        参数
     * @param runIfChecked 校验通过时执行任务
     */
    public static void isNotEmpty(Object param, Runnable runIfChecked) {
        if (ObjectUtil.isNotEmpty(param)) {
            runIfChecked.run();
        }
    }

    /**
     * 判断两个参数是否相等
     *
     * @param param      参数
     * @param otherParam 其它参数
     * @return /
     */
    public static boolean equals(Object param, Object otherParam) {
        return Objects.equals(param, otherParam);
    }

    /**
     * 去除空格
     *
     * @param param 参数
     * @return /
     */
    public static String trim(String param) {
        return StrUtil.trim(param);
    }

    /**
     * 去除集合中的空元素
     *
     * @param list 集合
     * @return /
     */
    public static <T> List<T> trim(List<T> list) {
        return list.stream().filter(ParamUtils::isNotEmpty).collect(Collectors.toList());
    }

    /**
     * 移除字符串的符号
     *
     * @param str    字符串
     * @param symbol 符号
     * @return 移除后
     */
    public static String removeSymbol(String str, String symbol) {
        if (isEmpty(str)) {
            return null;
        } else {
            str = removeStartSymbol(str, symbol);
            return removeEndSymbol(str, symbol);
        }
    }

    /**
     * 移除字符串开始的符号
     *
     * @param str    字符串
     * @param symbol 符号
     * @return 移除后的
     */
    public static String removeStartSymbol(String str, String symbol) {
        int strLen;
        if (isNotEmpty(str) && (strLen = str.length()) != 0) {
            int start = 0;
            if (isEmpty(symbol)) {
                return trim(str);
            } else {
                while (start != strLen && symbol.indexOf(str.charAt(start)) != -1) {
                    start++;
                }
            }
            return str.substring(start);
        }
        return trim(str);
    }

    /**
     * 移除字符串末尾的符号
     *
     * @param str    需要处理的字符串
     * @param symbol symbol 符号
     * @return 移除后的文本
     */
    public static String removeEndSymbol(String str, String symbol) {
        int end;
        if (isNotEmpty(str) && (end = str.length()) != 0) {
            if (isEmpty(symbol)) {
                return trim(str);
            }
            while (end != 0 && symbol.indexOf(str.charAt(end - 1)) != -1) {
                end--;
            }
            return str.substring(0, end);
        }
        return trim(str);
    }

    /**
     * 删除字符串里的符号
     *
     * @param str    字符串
     * @param symbol 符号,仅可使用一个符号
     * @return 删除符号后的字符串
     */
    public static String removeAllSymbol(String str, String symbol) {
        if (isEmpty(str) || symbol.length() > 1) {
            return str;
        }
        StringBuilder builder = new StringBuilder();
        String[] strings = str.split(symbol);
        if (isNotEmpty(strings)) {
            for (String s : strings) {
                builder.append(s);
            }
            return builder.toString();
        }
        return null;
    }

    /**
     * 判断数组里是否包含指定值
     *
     * @param arr 目标数组
     * @param val 值
     * @return 返回true为包含该值
     */
    public static boolean contains(String[] arr, String val) {
        if (arr == null) {
            return false;
        }
        for (String s : arr) {
            if (equals(s, val)) {
                return true;
            }
        }
        return false;
    }
}
