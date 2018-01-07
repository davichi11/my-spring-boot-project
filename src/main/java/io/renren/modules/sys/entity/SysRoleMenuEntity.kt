package io.renren.modules.sys.entity


import java.io.Serializable

/**
 * 角色与菜单对应关系
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年9月18日 上午9:28:13
 */
data class SysRoleMenuEntity(
        var id: Long? = null,

        /**
         * 角色ID
         */
        var roleId: Long? = null,

        /**
         * 菜单ID
         */
        var menuId: Long? = null,

        private val serialVersionUID: Long = 1L
) : Serializable
