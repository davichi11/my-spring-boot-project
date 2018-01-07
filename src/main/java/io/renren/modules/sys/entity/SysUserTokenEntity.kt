package io.renren.modules.sys.entity

import java.io.Serializable
import java.time.LocalDateTime


/**
 * 系统用户Token
 */
data class SysUserTokenEntity(
        private val serialVersionUID: Long = 1L,
        /**
         * 用户ID
         */
        var userId: Long? = null,
        /**
         * token
         */
        var token: String? = null,
        /**
         * 过期时间
         */
        var expireTime: LocalDateTime? = null,
        /**
         * 更新时间
         */
        var updateTime: LocalDateTime? = null
) : Serializable