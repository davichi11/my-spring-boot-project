package io.renren.modules.api.entity

import java.io.Serializable
import java.time.LocalDateTime


/**
 * 用户
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-23 15:22:06
 */
data class UserEntity(
        private val serialVersionUID: Long = 1L,
        /**
         * 用户ID
         */
        var userId: Long? = null,

        /**
         * 用户名
         */
        var username: String? = null,

        /**
         * 手机号
         */
        var mobile: String? = null,

        /**
         * 密码
         */
        @Transient
        var password: String? = null,

        /**
         * 创建时间
         */
        var createTime: LocalDateTime? = null

) : Serializable
