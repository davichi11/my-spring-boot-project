package io.renren.modules.sys.entity


import java.io.Serializable

/**
 * 菜单管理
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年9月18日 上午9:26:39
 */
data class SysMenuEntity(
        private val serialVersionUID: Long = 1L,

        /**
         * 菜单ID
         */
        var menuId: Long? = null,

        /**
         * 父菜单ID，一级菜单为0
         */
        var parentId: Long? = null,

        /**
         * 父菜单名称
         */
        var parentName: String? = null,

        /**
         * 菜单名称
         */
        var name: String? = null,

        /**
         * 菜单URL
         */
        var url: String? = null,

        /**
         * 授权(多个用逗号分隔，如：user:list,user:create)
         */
        var perms: String? = null,

        /**
         * 类型     0：目录   1：菜单   2：按钮
         */
        var type: Int? = null,

        /**
         * 菜单图标
         */
        var icon: String? = null,

        /**
         * 排序
         */
        var orderNum: Int? = null,
        /**
         * ztree属性
         */
        var open: Boolean? = null,

        var list: List<*>? = null

) : Serializable