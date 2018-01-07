package io.renren.modules.sys.entity


import java.io.Serializable

/**
 * 用户与角色对应关系
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年9月18日 上午9:28:39
 */
data class SysUserRoleEntity(
        private val serialVersionUID: Long = 1L,
        var id: Long? = null,

        /**
         * 用户ID
         */
        var userId: Long? = null,

        /**
         * 角色ID
         */
        var roleId: Long? = null
) : Serializable
