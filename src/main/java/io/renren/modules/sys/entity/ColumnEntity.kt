package io.renren.modules.sys.entity

import java.io.Serializable

/**
 * @author ChunLiang Hu
 * @Company
 * @Project renren-fast
 * @Package io.renren.modules.sys.entity
 * @Description 列的属性
 * @create 2017/8/13-21:05
 */
data class ColumnEntity(
        private val serialVersionUID: Long = 1L,
        /**
         * 列名
         */
        var columnName: String = "",
        /**
         * 列名类型
         */
        var dataType: String = "",
        /**
         * 列名备注
         */
        var comments: String = "",
        /**
         * 属性名称(第一个字母大写)，如：user_name => UserName
         */
        var upAttrName: String = "",
        /**
         * 属性名称(第一个字母小写)，如：user_name => userName
         */
        var lowAttrName: String = "",
        /**
         * 属性类型
         */
        var attrType: String = "",
        /**
         * auto_increment
         */
        var extra: String = ""
) : Serializable
