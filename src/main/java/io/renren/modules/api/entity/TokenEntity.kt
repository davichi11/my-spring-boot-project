package io.renren.modules.api.entity

import lombok.Data

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime


/**
 * 用户Token
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-23 15:22:07
 */
@Data
data class TokenEntity(
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
        var updateTime: LocalDate? = null
) : Serializable


