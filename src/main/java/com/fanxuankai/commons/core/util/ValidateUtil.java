package com.fanxuankai.commons.core.util;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;

import java.util.regex.Pattern;

/**
 * 格式校验
 *
 * @author fanxuankai
 */
public class ValidateUtil {
    /**
     * 中国电信号码格式验证 手机段： 133,153,180,181,189,177,1700,173
     **/
    private static final String CHINA_TELECOM_PATTERN = "(?:^(?:\\+86)?1(?:33|53|7[37]|8[019])\\d{8}$)|(?:^(?:\\+86)" +
            "?1700\\d{7}$)";
    /**
     * 中国联通号码格式验证 手机段：130,131,132,155,156,185,186,145,176,1707,1708,1709,175
     **/
    private static final String CHINA_UNICOM_PATTERN = "(?:^(?:\\+86)?1(?:3[0-2]|4[5]|5[56]|7[56]|8[56])\\d{8}$)|(?:^" +
            "(?:\\+86)?170[7-9]\\d{7}$)";
    /**
     * 中国移动号码格式验证 手机段：134,135,136,137,138,139,150,151,152,157,158,159,182,183,184,187,188,147,178,1705
     **/
    private static final String CHINA_MOBILE_PATTERN = "(?:^(?:\\+86)?1(?:3[4-9]|4[7]|5[0-27-9]|7[8]|8[2-478])" +
            "\\d{8}$)|" +
            "(?:^(?:\\+86)?1705\\d{7}$)";
    /**
     * 密码规则（6-16位字母、数字）
     */
    public static final String PASSWORD_PATTERN = "^[0-9A-Za-z]{6,16}$";
    /**
     * 年龄规则 1-120之间
     */
    public static final String AGE_PATTERN = "^(?:[1-9][0-9]?|1[01][0-9]|120)$";
    /**
     * QQ规则
     */
    public static final String QQ_PATTERN = "^[1-9][0-9]{4,13}$";
    /**
     * 整数规则
     */
    public static final String INTEGER_PATTERN = "^-?[0-9]+$";
    /**
     * 正整数规则
     */
    public static final String POSITIVE_INTEGER_PATTERN = "^\\+?[1-9][0-9]*$";

    /**
     * 验证是否为手机号码（中国）
     *
     * @param str string
     * @return boolean
     */
    public static boolean validateMobile(String str) {
        return Validator.isMobile(str);
    }

    /**
     * 验证是否是电信手机号,133、153、180、189、177
     *
     * @param str string
     * @return boolean
     */
    public static boolean isChinaTelecom(String str) {
        if (StrUtil.isEmpty(str)) {
            return Boolean.FALSE;
        }
        return str.matches(CHINA_TELECOM_PATTERN);
    }

    /**
     * 验证是否是联通手机号 130,131,132,155,156,185,186,145,176,1707,1708,1709,175
     *
     * @param str string
     * @return boolean
     */
    public static boolean isChinaUnicom(String str) {
        if (StrUtil.isEmpty(str)) {
            return Boolean.FALSE;
        }
        return str.matches(CHINA_UNICOM_PATTERN);
    }

    /**
     * 验证是否是移动手机号 134,135,136,137,138,139,150,151,152,157,158,159,182,183,184,187,188,147,178,1705
     *
     * @param str string
     * @return boolean
     */
    public static boolean isChinaMobile(String str) {
        if (StrUtil.isEmpty(str)) {
            return Boolean.FALSE;
        }
        return str.matches(CHINA_MOBILE_PATTERN);
    }

    /**
     * 验证密码格式  6-16 位字母、数字
     *
     * @param str string
     * @return boolean
     */
    public static boolean validatePwd(String str) {
        if (StrUtil.isEmpty(str)) {
            return Boolean.FALSE;
        }
        return Pattern.matches(PASSWORD_PATTERN, str);
    }

    /**
     * 验证是否为座机号码（中国）
     *
     * @param str string
     * @return boolean
     */
    public static boolean validateLandLine(String str) {
        return PhoneUtil.isTel(str);
    }

    /**
     * 验证是否为邮政编码（中国）
     *
     * @param str string
     * @return boolean
     */
    public static boolean isZipCode(String str) {
        return Validator.isZipCode(str);
    }

    /**
     * 验证是否为可用邮箱地址
     *
     * @param str string
     * @return boolean
     */
    public static boolean isEmail(String str) {
        return Validator.isEmail(str);
    }

    /**
     * 判断年龄，1-120之间
     *
     * @param str string
     * @return boolean
     */
    public static boolean isAge(String str) {
        if (StrUtil.isEmpty(str)) {
            return Boolean.FALSE;
        }
        return str.matches(AGE_PATTERN);
    }

    /**
     * 身份证验证
     *
     * @param str string
     * @return boolean
     */
    public static boolean isValidCard(String str) {
        return IdcardUtil.isValidCard(str);
    }

    /**
     * URL地址验证
     *
     * @param str string
     * @return boolean
     */
    public static boolean validateUrl(String str) {
        return HttpUtil.isHttp(str) || HttpUtil.isHttps(str);
    }

    /**
     * 验证QQ号
     *
     * @param str string
     * @return boolean
     */
    public static boolean validateQq(String str) {
        if (StrUtil.isEmpty(str)) {
            return Boolean.FALSE;
        }
        return str.matches(QQ_PATTERN);
    }

    /**
     * 验证是否都为汉字
     *
     * @param str string
     * @return boolean
     */
    public static boolean isChinese(String str) {
        return Validator.isChinese(str);
    }

    /**
     * 判断字符串是否全部为字母组成，包括大写和小写字母和汉字
     *
     * @param str string
     * @return boolean
     */
    public static boolean isLetter(String str) {
        return Validator.isLetter(str);
    }

    /**
     * 判断是否是整数，包括负数
     *
     * @param str string
     * @return boolean
     */
    public static boolean isInteger(String str) {
        if (StrUtil.isEmpty(str)) {
            return Boolean.FALSE;
        }
        return str.matches(INTEGER_PATTERN);
    }

    /**
     * 判断是否是大于0的正整数
     *
     * @param str string
     * @return boolean
     */
    public static boolean isPositiveInt(String str) {
        if (StrUtil.isEmpty(str)) {
            return Boolean.FALSE;
        }
        return str.matches(POSITIVE_INTEGER_PATTERN);
    }

}