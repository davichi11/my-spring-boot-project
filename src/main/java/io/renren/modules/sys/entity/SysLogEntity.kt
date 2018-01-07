package io.renren.modules.sys.entity

import java.io.Serializable
import java.time.LocalDateTime


/**
 * 系统日志
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-08 10:40:56
 */
data class SysLogEntity(
        var id: Long? = null,
        /**
         * 用户名
         */
        var username: String? = null,
        /**
         * 用户操作
         */
        var operation: String? = null,
        /**
         * 请求方法
         */
        var method: String? = null,
        /**
         * 请求参数
         */
        var params: String? = null,
        /**
         * IP地址
         */
        var ip: String? = null,
        /**
         * 创建时间
         */
        var createDate: LocalDateTime? = null,
        private val serialVersionUID :Long = 1L
) : Serializable