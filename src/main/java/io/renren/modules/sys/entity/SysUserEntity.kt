package io.renren.modules.sys.entity

import io.renren.common.validator.group.AddGroup
import io.renren.common.validator.group.UpdateGroup
import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotBlank
import org.springframework.format.annotation.DateTimeFormat
import java.io.Serializable
import java.time.LocalDateTime

/**
 * 系统用户
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年9月18日 上午9:28:55
 */
data class SysUserEntity(


        /**
         * 用户ID
         */
        var userId: Long? = null,

        /**
         * 用户名
         */
        @NotBlank(message = "用户名不能为空", groups = [(AddGroup::class), (UpdateGroup::class)])
        var username: String? = null,


        /**
         * 密码
         */
        @NotBlank(message = "密码不能为空", groups = [(AddGroup::class)])
        var password: String? = null,

        /**
         * 盐
         */
        var salt: String? = null,


        /**
         * 邮箱
         */
        @NotBlank(message = "邮箱不能为空", groups = [(AddGroup::class), (UpdateGroup::class)])
        @Email(message = "邮箱格式不正确", groups = [(AddGroup::class), (UpdateGroup::class)])
        var email: String? = null,


        /**
         * 手机号
         */
        var mobile: String? = null,

        /**
         * 状态  0：禁用   1：正常
         */
        var status: Int? = null,


        /**
         * 角色ID列表
         */
        var roleIdList: List<Long>? = null,


        /**
         * 创建者ID
         */
        var createUserId: Long? = null,


        /**
         * 创建时间
         */
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        var createTime: LocalDateTime? = null,


        private val serialVersionUID: Long = 1L
) : Serializable
