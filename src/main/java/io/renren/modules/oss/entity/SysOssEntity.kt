package io.renren.modules.oss.entity

import java.io.Serializable
import java.util.*


/**
 * 文件上传
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-25 12:13:26
 */
data class SysOssEntity(
        private val serialVersionUID: Long = 1L,
        /**
         * 主键ID
         */
        var id: Long? = null,

        /**
         * URL地址
         */
        var url: String? = null,

        /**
         * 创建时间
         */
        var createDate: Date? = null

) : Serializable
