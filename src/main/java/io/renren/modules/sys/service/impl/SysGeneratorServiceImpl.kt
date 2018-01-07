package io.renren.modules.sys.service.impl


import io.renren.common.utils.GenUtils
import io.renren.modules.sys.dao.SysGeneratorDao
import io.renren.modules.sys.service.SysGeneratorService
import org.apache.commons.io.IOUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.util.zip.ZipOutputStream

/**
 * 代码生成器
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年10月31日 上午10:40:10
 */
@Service
class SysGeneratorServiceImpl @Autowired constructor(private val sysGeneratorDao: SysGeneratorDao) : SysGeneratorService {

    override fun queryList(map: Map<String, Any>): List<Map<String, Any>> {
        return sysGeneratorDao.queryList(map)
    }

    override fun queryTotal(map: Map<String, Any>): Int {
        return sysGeneratorDao.queryTotal(map)
    }

    override fun queryTable(tableName: String): Map<String, String> {
        return sysGeneratorDao.queryTable(tableName)
    }

    override fun queryColumns(tableName: String): List<Map<String, String>> {
        return sysGeneratorDao.queryColumns(tableName)
    }

    override fun generatorCode(tableNames: Array<String>): ByteArray {
        ByteArrayOutputStream().use { outputStream ->
            ZipOutputStream(outputStream).use { zip ->
                tableNames.forEach { tableName ->
                    //查询表信息
                    val table = queryTable(tableName)
                    //查询列信息
                    val columns = queryColumns(tableName)
                    //生成代码
                    GenUtils.generatorCode(table, columns, zip)
                }
                IOUtils.closeQuietly(zip)
                return outputStream.toByteArray()
            }
        }

    }
}
