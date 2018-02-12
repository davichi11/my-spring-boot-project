@file:Suppress("unused")

package io.renren.common.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * 日期处理
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年12月21日 下午12:53:33
 */
object DateUtils {

    /**
     * 时间格式(yyyy-MM-dd)
     */
    private const val DATE_PATTERN = "yyyy-MM-dd"
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    private const val DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss"

    /**
     * 转换时间为字符串
     *
     * @param date
     * @param pattern 时间格式
     * @return
     */
    @JvmOverloads
    fun format(date: LocalDate, pattern: String = DATE_PATTERN): String {
        return date.format(DateTimeFormatter.ofPattern(pattern))
    }


    /**
     * 字符串转换为时间
     *
     * @param date
     * @param datePattern 时间格式
     * @return
     */
    @JvmOverloads
    fun stringFormatDate(date: String, datePattern: String = DATE_PATTERN): LocalDate {
        return if (date.isNotEmpty())
            LocalDate.parse(date, DateTimeFormatter.ofPattern(datePattern))
        else
            LocalDate.now()
    }

    /**
     * 转换时间戳为字符串
     *
     * @param dateTime
     * @param timePattern 时间戳格式
     * @return
     */
    @JvmOverloads
    fun formatDateTime(dateTime: LocalDateTime, timePattern: String = DATE_TIME_PATTERN): String {
        return dateTime.format(DateTimeFormatter.ofPattern(timePattern))
    }

    /**
     * 字符串转换为时间戳
     *
     * @param dateTime
     * @param timePattern 默认yyyy-MM-dd HH:mm:ss
     * @return
     */
    fun stringFormatDateTime(dateTime: String, timePattern: String = DATE_TIME_PATTERN): LocalDateTime {
        return if (dateTime.isNotEmpty())
            LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(timePattern))
        else
            LocalDateTime.now()
    }

}

