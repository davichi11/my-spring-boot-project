package io.renren.modules.sys.entity

/**
 * @author ChunLiang Hu
 * @Company
 * @Project renren-fast
 * @Package io.renren.modules.sys.entity
 * @Description 表数据
 * @create 2017/8/13-21:04
 */
data class TableEntity(

        /**
         * 表的名称
         */
        var tableName: String? = null,
        /**
         * 表的备注
         */
        var comments: String? = null,
        /**
         * 表的主键
         */
        var pk: ColumnEntity? = null,
        /**
         * 表的列名(不包含主键)
         */
        var columns: List<ColumnEntity>? = null,

        /**
         * 类名(第一个字母大写)，如：sys_user => SysUser
         */
        var upClassName: String? = null,
        /**
         * 类名(第一个字母小写)，如：sys_user => sysUser
         */
        var lowClassName: String? = null
)
