package io.renren.common.utils;

import org.apache.commons.lang.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * 日期处理
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年12月21日 下午12:53:33
 */
public final class DateUtils {
    /**
     * 时间格式(yyyy-MM-dd)
     */
    private final static String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    private final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 使用默认的时间格式转换时间为字符串
     *
     * @param date
     * @return 转换后的字符串
     */
    public static String format(LocalDate date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 转换时间为字符串
     *
     * @param date
     * @param pattern 时间格式
     * @return
     */
    public static String format(LocalDate date, String pattern) {
        return Optional.ofNullable(date).map(localDate -> localDate.format(DateTimeFormatter.ofPattern(pattern))).orElse("");
    }

    /**
     * 使用默认的时间戳格式转换时间戳
     *
     * @param dateTime
     * @return 转换后的字符串
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return formatDateTime(dateTime, DATE_TIME_PATTERN);
    }

    /**
     * 转换时间戳为字符串
     *
     * @param dateTime
     * @param timePattern 时间戳格式
     * @return
     */
    public static String formatDateTime(LocalDateTime dateTime, String timePattern) {
        String finalTimePattern = Optional.ofNullable(timePattern).orElse(DATE_TIME_PATTERN);
        return Optional.ofNullable(dateTime).map(localDateTime -> localDateTime.format(DateTimeFormatter.ofPattern(finalTimePattern))).
                orElse("");
    }

    /**
     * 字符串转换为时间
     *
     * @param date
     * @param datePattern 时间格式
     * @return
     */
    public static LocalDate stringFormatDate(String date, String datePattern) {
        return StringUtils.isNotBlank(date) ? LocalDate.parse(date, DateTimeFormatter.ofPattern(datePattern)) :
                LocalDate.now();
    }

    /**
     * 使用默认的时间格式转换字符串为时间
     *
     * @param date
     * @return
     */
    public static LocalDate stringFormatDate(String date) {
        return stringFormatDate(date, DATE_PATTERN);
    }

    /**
     * 字符串转换为时间戳
     *
     * @param dateTiem
     * @param timePattern
     * @return
     */
    public static LocalDateTime stringFormatDateTime(String dateTiem, String timePattern) {
        return StringUtils.isNotBlank(dateTiem) ? LocalDateTime.parse(dateTiem, DateTimeFormatter.ofPattern(timePattern)) :
                LocalDateTime.now();
    }

    /**
     * 使用默认的时间格式转换字符串为时间戳
     *
     * @param dateTime
     * @return
     */
    public static LocalDateTime stringFormatDateTime(String dateTime) {
        return stringFormatDateTime(dateTime, DATE_TIME_PATTERN);
    }

}
