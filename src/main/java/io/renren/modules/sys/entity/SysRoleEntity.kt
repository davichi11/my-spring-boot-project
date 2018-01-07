package io.renren.modules.sys.entity


import org.hibernate.validator.constraints.NotBlank
import org.springframework.format.annotation.DateTimeFormat
import java.io.Serializable
import java.time.LocalDateTime

/**
 * 角色
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年9月18日 上午9:27:38
 */
data class SysRoleEntity(

        /**
         * 角色ID
         */
        var roleId: Long? = null,

        /**
         * 角色名称
         */
        @NotBlank(message = "角色名称不能为空")
        var roleName: String? = null,

        /**
         * 备注
         */
        var remark: String? = null,

        /**
         * 创建者ID
         */
        var createUserId: Long? = null,

        var menuIdList: List<Long>? = null,

        /**
         * 创建时间
         */
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        var createTime: LocalDateTime? = null,

        private val serialVersionUID: Long = 1L

) : Serializable
