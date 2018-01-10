package io.renren.common.xss

import org.apache.commons.lang.StringUtils

/**
 * SQL过滤
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-04-01 16:16
 */
object SQLFilter {

    /**
     * SQL注入过滤
     *
     * @param str 待验证的字符串
     */
    fun sqlInject(str: String): String? {
        var varStr = str
        if (StringUtils.isBlank(varStr)) {
            return null
        }
        //去掉'|"|;|\字符
        varStr = StringUtils.replace(varStr, "'", "")
        varStr = StringUtils.replace(varStr, "\"", "")
        varStr = StringUtils.replace(varStr, ";", "")
        varStr = StringUtils.replace(varStr, "\\", "")

        //转换成小写
        varStr = varStr.toLowerCase()

        //非法字符
        val keywords = arrayOf("master", "truncate", "insert", "select", "delete", "update", "declare", "alert", "drop")
        //判断是否包含非法字符
        return keywords.firstOrNull({ varStr.contains(it) })
    }
}
