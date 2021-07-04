package com.fanxuankai.commons.util;

import cn.hutool.core.date.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类<p>
 * 基于{@link DateUtil}封装了一些常用的方法,也可以直接使用之<p>
 * 格式常量建议使用{@link DatePattern}<p>
 * <p>
 * 方法开头解释<p>
 * toText: 转字符串文本<p>
 * toDate: 转 Date 类<p>
 * toCalendar: 转 Calendar 类<p>
 * toTimestamp: 转时间戳<p>
 * toLocalDate: 转 LocalDate 类<p>
 * toLocalDateTime: 转 LocalDateTime 类<p>
 * get: 获取日期、年分、月分、天数等等<p>
 * between: 获取间隔月份、天数、小时、分、秒等等<p>
 * plus: 加上年份、月份、天数、小时、分、秒等等
 * minus: 减去年份、月份、天数、小时、分、秒等等
 *
 * @author fanxuankai
 */
public class DateUtils {
    /**
     * 标准日期时间格式 yyyy-MM-dd HH:mm:ss
     *
     * @param date date
     * @return 时间字符串
     */
    public static String toText(Date date) {
        return DateUtil.formatDateTime(date);
    }

    /**
     * 标准日期格式
     *
     * @param date date
     * @return string 文本
     */
    public static String toTextForNormalDate(Date date) {
        return DateUtil.format(date, DatePattern.NORM_DATE_PATTERN);
    }

    /**
     * 标准日期时间格式，精确到分
     *
     * @param date date
     * @return string 文本
     */
    public static String toTextForNormalDateTimeMin(Date date) {
        return DateUtil.format(date, DatePattern.NORM_DATETIME_MINUTE_PATTERN);
    }

    /**
     * 标准日期时间格式，精确到毫秒
     *
     * @param date date
     * @return string 文本
     */
    public static String toTextForNormalDateTimeMs(Date date) {
        return DateUtil.format(date, DatePattern.NORM_DATETIME_MS_PATTERN);
    }

    /**
     * 标准时间格式
     *
     * @param date date
     * @return string 文本
     */
    public static String toTextForNormalTime(Date date) {
        return DateUtil.format(date, DatePattern.NORM_TIME_PATTERN);
    }

    /**
     * 标准日期格式
     *
     * @param date date
     * @return string 文本
     */
    public static String toTextForChineseDate(Date date) {
        return DateUtil.format(date, DatePattern.CHINESE_DATE_PATTERN);
    }

    /**
     * 标准日期时间格式
     *
     * @param date date
     * @return string 文本
     */
    public static String toTextForChineseDateTime(Date date) {
        return DateUtil.format(date, DatePattern.CHINESE_DATE_TIME_PATTERN);
    }

    /**
     * 自定义格式化日期
     *
     * @param date   date
     * @param format 格式
     * @return string 文本
     */
    public static String toText(Date date, String format) {
        return DateUtil.format(date, format);
    }

    /**
     * LocalDate转化为指定格式字符串
     *
     * @param localDate localDate
     * @return 时间字符串
     */
    public static String toText(LocalDate localDate) {
        return LocalDateTimeUtil.formatNormal(localDate);
    }

    /**
     * LocalDateTime转化为指定格式字符串
     *
     * @param localDateTime LocalDateTime
     * @return 时间字符串
     */
    public static String toText(LocalDateTime localDateTime) {
        return LocalDateTimeUtil.formatNormal(localDateTime);
    }

    /**
     * LocalTime转化为指定格式字符串
     *
     * @param localTime localTime
     * @return 时间字符串
     */
    public static String toText(LocalTime localTime) {
        return TemporalAccessorUtil.format(localTime, DatePattern.NORM_TIME_PATTERN);
    }

    /**
     * LocalTime转化为指定格式字符串
     *
     * @param localTime localTime
     * @param format    格式
     * @return 时间字符串
     */
    public static String toText(LocalTime localTime, String format) {
        return TemporalAccessorUtil.format(localTime, format);
    }

    /**
     * 日期转文本时间
     *
     * @param calendar 日期
     * @return 文本时间
     */
    public static String toText(Calendar calendar) {
        return toText(calendar.getTime());
    }

    /**
     * 日期转文本时间
     *
     * @param calendar 日期
     * @param format   格式
     * @return 文本时间
     */
    public static String toText(Calendar calendar, String format) {
        return toText(calendar.getTime(), format);
    }

    /**
     * 将时间戳转换为文本时间
     *
     * @param timeStamp 时间戳
     * @return 文本时间
     */
    public static String toText(Long timeStamp) {
        return toText(new Date(timeStamp));
    }

    /**
     * 将时间戳转换为文本时间
     *
     * @param timeStamp 时间戳
     * @param format    格式
     * @return 文本时间
     */
    public static String toText(Long timeStamp, String format) {
        return toText(new Date(timeStamp), format);
    }

    /**
     * 获取时间对象
     *
     * @param date 文本格式时间
     * @return 时间对象
     */
    public static Date toDate(String date) {
        return DateUtil.parse(date);
    }

    /**
     * 获取时间对象
     *
     * @param date   文本格式时间
     * @param format format
     * @return 时间对象
     */
    public static Date toDate(String date, String format) {
        return DateUtil.parse(date, format);
    }

    /**
     * localDateTime转date
     *
     * @param dateTime local
     * @return date
     */
    public static Date toDate(LocalDateTime dateTime) {
        return DateUtil.date(dateTime);
    }

    /**
     * localDate转date
     *
     * @param localDate localDate
     * @return date
     */
    public static Date toDate(LocalDate localDate) {
        return DateUtil.date(localDate);
    }

    /**
     * 日期转时间
     *
     * @param calendar 日期
     * @param format   格式
     * @return 时间
     */
    public static Date toDate(Calendar calendar, String format) {
        return toDate(toText(calendar, format));
    }

    /**
     * date 转localDateTime
     *
     * @param date date
     * @return localDateTime
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * date 转localDate
     *
     * @param date date
     * @return localDate
     */
    public static LocalDate toLocalDate(Date date) {
        return LocalDateTimeUtil.of(date).toLocalDate();
    }

    /**
     * 文本日期转LocalDate
     *
     * @param date date
     * @return localDate
     */
    public static LocalDate toLocalDate(String date) {
        return LocalDateTimeUtil.parseDate(date);
    }

    /**
     * 文本日期转LocalDateTime
     *
     * @param dateTime dateTime
     * @return LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(String dateTime) {
        return LocalDateTimeUtil.parse(dateTime);
    }

    /**
     * 获得时间戳
     *
     * @param localDateTime lo
     * @return .
     */
    public static long toTimestamp(LocalDateTime localDateTime) {
        return LocalDateTimeUtil.toEpochMilli(localDateTime);
    }

    /**
     * Long类型时间戳转化为LocalDateTime
     *
     * @param timestamp 时间戳
     * @return LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(long timestamp) {
        return LocalDateTimeUtil.of(timestamp);
    }

    /**
     * 将文本时间转换为时间戳
     *
     * @param date date
     * @return 时间戳
     */
    public static Long toTimestamp(String date) {
        return toDate(date).getTime();
    }

    /**
     * 将文本时间转换为时间戳
     *
     * @param date   date
     * @param format 转换格式
     * @return 时间戳
     */
    public static Long toTimestamp(String date, String format) {
        return toDate(date, format).getTime();
    }

    /**
     * 文本时间转化为日期
     *
     * @param date   文本时间
     * @param format 转换格式
     * @return 日期
     */
    public static Calendar toCalendar(String date, String format) {
        return DateUtil.parseByPatterns(date, format);
    }

    /**
     * 获取日期时间当月的总天数
     *
     * @param date 时间对象
     * @return days
     */
    public static int getAllDaysOfMonth(Date date) {
        return DateUtil.endOfMonth(date).dayOfMonth();
    }

    /**
     * 获得指定日期的小时数部分<br>
     *
     * @param date          日期
     * @param is24HourClock 是否24小时制
     * @return 小时数
     */
    public static int getHourOfDay(Date date, boolean is24HourClock) {
        return DateUtil.hour(date, is24HourClock);
    }

    /**
     * 获得指定日期是星期几，1表示周日，2表示周一
     *
     * @param date 时间对象
     * @return days
     */
    public static int getDayOfWeek(Date date) {
        return DateUtil.dayOfWeek(date) - 1;
    }

    /**
     * 获得指定日期是这个日期所在月份的第几天
     *
     * @param date 时间对象
     * @return days
     */
    public static int getDayOfMonth(Date date) {
        return DateUtil.dayOfMonth(date);
    }

    /**
     * 获得指定日期是所在月份的第几周
     *
     * @param date 时间对象
     * @return days
     */
    public static int getWeekOfMonth(Date date) {
        return DateUtil.weekOfMonth(date);
    }

    /**
     * 获得指定日期是所在年份的第几周
     *
     * @param date 时间对象
     * @return days
     */
    public static int getWeekOfYear(Date date) {
        return DateUtil.weekOfYear(date);
    }

    /**
     * 获取日期时间的月份,1开头
     *
     * @param date 时间对象
     * @return month
     */
    public static int getMonth(Date date) {
        return DateUtil.month(date) + 1;
    }

    /**
     * 获取日期时间的年份，如2021-04-16，返回2017
     *
     * @param date 时间对象
     * @return years
     */
    public static int getYears(Date date) {
        return DateUtil.year(date);
    }

    /**
     * 获取某小时的开始时间
     *
     * @param date 日期
     * @return date
     */
    public static Date getBeginOfHour(Date date) {
        return DateUtil.beginOfHour(date);
    }

    /**
     * 获取某小时的结束时间
     *
     * @param date 日期
     * @return date
     */
    public static Date getEndOfHour(Date date) {
        return DateUtil.endOfHour(date);
    }

    /**
     * 获取某天的开始时间
     *
     * @param date 日期
     * @return date
     */
    public static Date getBeginOfDay(Date date) {
        return DateUtil.beginOfDay(date);
    }

    /**
     * 获取某天的结束时间
     *
     * @param date 日期
     * @return date
     */
    public static Date getEndOfDay(Date date) {
        return DateUtil.endOfDay(date);
    }

    /**
     * 获取某周的开始时间，周一定为一周的开始时间
     *
     * @param date 日期
     * @return date
     */
    public static Date getBeginOfWeek(Date date) {
        return DateUtil.beginOfWeek(date);
    }

    /**
     * 获取某周的结束时间，周日定为一周的结束
     *
     * @param date 日期
     * @return date
     */
    public static Date getEndOfWeek(Date date) {
        return DateUtil.endOfWeek(date);
    }

    /**
     * 获取某月的开始时间
     *
     * @param date 日期
     * @return date
     */
    public static Date getBeginOfMonth(Date date) {
        return DateUtil.beginOfMonth(date);
    }

    /**
     * 获取某月的结束时间
     *
     * @param date 日期
     * @return date
     */
    public static Date getEndOfMonth(Date date) {
        return DateUtil.endOfMonth(date);
    }

    /**
     * 获得指定日期是星期几，1表示周日，2表示周一
     *
     * @param date 日期
     * @return date
     */
    public static int getWeekValue(Date date) {
        return DateUtil.dayOfWeek(date);
    }

    /**
     * 获得指定日期是星期几
     *
     * @param date 日期
     * @return date
     */
    public static String getWeekChinese(Date date) {
        return DateUtil.dayOfWeekEnum(date).toChinese();
    }

    /**
     * 在日期上加上秒
     *
     * @param date 日期
     * @param n    数量
     * @return date
     */
    public static Date plusSeconds(Date date, int n) {
        return DateUtil.offset(date, DateField.SECOND, n);
    }

    /**
     * 在日期上加上分
     *
     * @param date 日期
     * @param n    数量
     * @return date
     */
    public static Date plusMinutes(Date date, int n) {
        return DateUtil.offset(date, DateField.MINUTE, n);
    }

    /**
     * 在日期上加上小时
     *
     * @param date 日期
     * @param n    数量
     * @return date
     */
    public static Date plusHours(Date date, int n) {
        return DateUtil.offset(date, DateField.HOUR, n);
    }

    /**
     * 在日期上加上天数
     *
     * @param date 日期
     * @param n    数量
     * @return date
     */
    public static Date plusDays(Date date, int n) {
        return DateUtil.offset(date, DateField.DAY_OF_MONTH, n);
    }

    /**
     * 在日期上加上天数
     *
     * @param date 日期
     * @param n    数量
     * @return date
     */
    public static Date plusWeeks(Date date, int n) {
        return DateUtil.offset(date, DateField.WEEK_OF_MONTH, n);
    }

    /**
     * 在日期上加上数个整月
     *
     * @param date 日期
     * @param n    数量
     * @return date
     */
    public static Date plusMonths(Date date, int n) {
        return DateUtil.offset(date, DateField.MONTH, n);
    }

    /**
     * 在日期上加上数个整年
     *
     * @param date 日期
     * @param n    数量
     * @return date
     */
    public static Date plusYears(Date date, int n) {
        return DateUtil.offset(date, DateField.YEAR, n);
    }

    /**
     * 在日期上减去秒
     *
     * @param date 日期
     * @param n    数量
     * @return date
     */
    public static Date minusSeconds(Date date, int n) {
        return DateUtil.offset(date, DateField.SECOND, -n);
    }

    /**
     * 在日期上减去分
     *
     * @param date 日期
     * @param n    数量
     * @return date
     */
    public static Date minusMinutes(Date date, int n) {
        return DateUtil.offset(date, DateField.MINUTE, -n);
    }

    /**
     * 在日期上减去小时
     *
     * @param date 日期
     * @param n    数量
     * @return date
     */
    public static Date minusHours(Date date, int n) {
        return DateUtil.offset(date, DateField.HOUR, -n);
    }

    /**
     * 在日期上减去天数
     *
     * @param date 日期
     * @param n    数量
     * @return date
     */
    public static Date minusDays(Date date, int n) {
        return DateUtil.offset(date, DateField.DAY_OF_MONTH, -n);
    }

    /**
     * 在日期上减去天数
     *
     * @param date 日期
     * @param n    数量
     * @return date
     */
    public static Date minusWeeks(Date date, int n) {
        return DateUtil.offset(date, DateField.WEEK_OF_MONTH, -n);
    }

    /**
     * 在日期上减去数个整月
     *
     * @param date 日期
     * @param n    数量
     * @return date
     */
    public static Date minusMonths(Date date, int n) {
        return DateUtil.offset(date, DateField.MONTH, -n);
    }

    /**
     * 在日期上减去数个整年
     *
     * @param date 日期
     * @param n    数量
     * @return date
     */
    public static Date minusYears(Date date, int n) {
        return DateUtil.offset(date, DateField.YEAR, -n);
    }

    /**
     * 获取两个日期相差的秒数
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 相差的天数
     */
    public static long betweenMillis(String startDate, String endDate) {
        return DateUtil.between(toDate(startDate), toDate(endDate), DateUnit.MS, false);
    }

    /**
     * 获取两个日期相差的秒数
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 相差的天数
     */
    public static long betweenSeconds(String startDate, String endDate) {
        return DateUtil.between(toDate(startDate), toDate(endDate), DateUnit.SECOND, false);
    }

    /**
     * 获取两个日期相差的分数
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 相差的天数
     */
    public static long betweenMinutes(String startDate, String endDate) {
        return DateUtil.between(toDate(startDate), toDate(endDate), DateUnit.MINUTE, false);
    }

    /**
     * 获取两个日期相差的小时
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 相差的天数
     */
    public static long betweenHours(String startDate, String endDate) {
        return DateUtil.between(toDate(startDate), toDate(endDate), DateUnit.HOUR, false);
    }

    /**
     * 获取两个日期相差的天数
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 相差的天数
     */
    public static long betweenDays(String startDate, String endDate) {
        return DateUtil.between(toDate(startDate), toDate(endDate), DateUnit.DAY, false);
    }

    /**
     * 获取两个日期相差的周数
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 相差的天数
     */
    public static long betweenWeeks(String startDate, String endDate) {
        return DateUtil.between(toDate(startDate), toDate(endDate), DateUnit.WEEK, false);
    }

    /**
     * 获取两个日期相差的秒数
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 相差的天数
     */
    public static long betweenMillis(Date startDate, Date endDate) {
        return DateUtil.between(startDate, endDate, DateUnit.MS, false);
    }

    /**
     * 获取两个日期相差的秒数
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 相差的天数
     */
    public static long betweenSeconds(Date startDate, Date endDate) {
        return DateUtil.between(startDate, endDate, DateUnit.SECOND, false);
    }

    /**
     * 获取两个日期相差的分数
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 相差的天数
     */
    public static long betweenMinutes(Date startDate, Date endDate) {
        return DateUtil.between(startDate, endDate, DateUnit.MINUTE, false);
    }

    /**
     * 获取两个日期相差的小时
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 相差的天数
     */
    public static long betweenHours(Date startDate, Date endDate) {
        return DateUtil.between(startDate, endDate, DateUnit.HOUR, false);
    }

    /**
     * 获取两个日期相差的天数
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 相差的天数
     */
    public static long betweenDays(Date startDate, Date endDate) {
        return DateUtil.between(startDate, endDate, DateUnit.DAY, false);
    }

    /**
     * 获取两个日期相差的周数
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 相差的天数
     */
    public static long betweenWeeks(Date startDate, Date endDate) {
        return DateUtil.between(startDate, endDate, DateUnit.WEEK, false);
    }

    /**
     * 获取两个日期（不含时分秒）相差的天数，包含今天
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 相差的天数
     */
    public static long betweenDaysIncludeToday(String startDate, String endDate) {
        return DateUtil.between(toDate(startDate), toDate(endDate), DateUnit.DAY, false) + 1;
    }

    /**
     * 获取两个日期（不含时分秒）相差的天数，包含今天
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 相差的天数
     */
    public static long betweenDaysIncludeToday(Date startDate, Date endDate) {
        return DateUtil.between(startDate, endDate, DateUnit.DAY, false) + 1;
    }
}
