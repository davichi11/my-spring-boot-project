package io.renren.modules.sys.dao

import org.apache.ibatis.annotations.Mapper

/**
 * 代码生成器
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月19日 下午3:32:04
 */
@Mapper
interface SysGeneratorDao {
    /**
     * 查询所有表的结构
     *
     * @param map
     * @return
     */
    fun queryList(map: Map<String, Any>): List<Map<String, Any>>

    /**
     * 查询总记录数
     *
     * @param map
     * @return
     */
    fun queryTotal(map: Map<String, Any>): Int

    /**
     * 查询单个表的结构
     *
     * @param tableName
     * @return
     */
    fun queryTable(tableName: String): Map<String, String>

    /**
     * 查询单个表的列结构
     *
     * @param tableName
     * @return
     */
    fun queryColumns(tableName: String): List<Map<String, String>>
}
