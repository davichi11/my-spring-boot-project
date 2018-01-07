package io.renren.modules.sys.service

/**
 * @author ChunLiang Hu
 * @Company
 * @Project renren-fast
 * @Package io.renren.modules.sys.service
 * @Description TODO(描述)
 * @create 2017/8/13-21:13
 */
interface SysGeneratorService {
    /**
     * 查询列表
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
     * 查询表名
     *
     * @param tableName
     * @return
     */
    fun queryTable(tableName: String): Map<String, String>

    /**
     * 查询列名
     *
     * @param tableName
     * @return
     */
    fun queryColumns(tableName: String): List<Map<String, String>>

    /**
     * 生成代码
     *
     * @param tableNames
     * @return
     */
    fun generatorCode(tableNames: Array<String>): ByteArray
}
