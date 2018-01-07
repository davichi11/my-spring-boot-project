@file:Suppress("unused")

package io.renren.common.utils

import org.apache.commons.lang.StringUtils
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

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
    private val DATE_PATTERN = "yyyy-MM-dd"
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    private val DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss"

    /**
     * 转换时间为字符串
     *
     * @param date
     * @param pattern 时间格式
     * @return
     */
    @JvmOverloads
    fun format(date: LocalDate, pattern: String = DATE_PATTERN): String {
        return Optional.ofNullable(date).map { localDate -> localDate.format(DateTimeFormatter.ofPattern(pattern)) }.orElse("")
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
        val finalTimePattern = Optional.ofNullable(timePattern).orElse(DATE_TIME_PATTERN)
        return Optional.ofNullable(dateTime).map { localDateTime -> localDateTime.format(DateTimeFormatter.ofPattern(finalTimePattern)) }.orElse("")
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
        return if (StringUtils.isNotBlank(date))
            LocalDate.parse(date, DateTimeFormatter.ofPattern(datePattern))
        else
            LocalDate.now()
    }

    /**
     * 字符串转换为时间戳
     *
     * @param dateTime
     * @param timePattern
     * @return
     */
    fun stringFormatDateTime(dateTime: String, timePattern: String): LocalDateTime {
        return if (StringUtils.isNotBlank(dateTime))
            LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(timePattern))
        else
            LocalDateTime.now()
    }

    /**
     * 使用默认的时间格式转换字符串为时间戳
     *
     * @param dateTime
     * @return
     */
    fun stringFormatDateTime(dateTime: String): LocalDateTime {
        return stringFormatDateTime(dateTime, DATE_TIME_PATTERN)
    }

}

