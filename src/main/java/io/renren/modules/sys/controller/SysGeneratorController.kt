package io.renren.modules.sys.controller

import com.alibaba.fastjson.JSON
import com.github.pagehelper.PageHelper
import com.github.pagehelper.PageInfo
import io.renren.common.utils.DateUtils
import io.renren.common.utils.GenUtils
import io.renren.common.utils.Result
import io.renren.modules.sys.service.SysGeneratorService
import org.apache.commons.io.FileUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.File
import java.io.IOException
import java.time.LocalDateTime

/**
 * @author ChunLiang Hu
 * @Company
 * @Project renren-fast
 * @Package io.renren.modules.sys.controller
 * @Description TODO(描述)
 * @create 2017/8/13-21:24
 */
@RestController
@RequestMapping("/sys/generator")
class SysGeneratorController @Autowired constructor(private val sysGeneratorService: SysGeneratorService) {
    private val log = LoggerFactory.getLogger(SysGeneratorController::class.java)
    /**
     * 列表数据
     *
     * @param params
     * @return
     */
    @GetMapping("/list")
    fun list(@RequestParam params: Map<String, Any>): Result {

        val pageInfo: PageInfo<Map<String, Any>>
        try {
            pageInfo = PageHelper.startPage<Any>((params["page"] as String).toInt(), (params["limit"] as String).toInt())
                    .doSelectPageInfo { sysGeneratorService.queryList(params) }
        } catch (e: Exception) {
            log.error("查询异常", e)
            return Result().error(msg = "查询异常")
        }

        return Result().ok().put("page", pageInfo)

    }

    /**
     * 生成代码
     */
    @GetMapping("/code")
    @Throws(IOException::class)
    fun code(tables: String): Result {
        val tableNames = JSON.parseArray(tables).toArray()
        try {
            val data: ByteArray = sysGeneratorService.generatorCode(tableNames.map { it.toString() })
            val fileName = "gender" + DateUtils.formatDateTime(LocalDateTime.now(),"yyyyMMddHHmmss") + ".zip"
            FileUtils.writeByteArrayToFile(File(GenUtils.codePath + fileName), data)
        } catch (e: IOException) {
            log.error("文件写入异常", e)
            return Result().error(msg = "生成失败,文件写入异常")
        }

        return Result().ok()
    }
}
