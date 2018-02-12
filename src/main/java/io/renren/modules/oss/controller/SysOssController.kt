package io.renren.modules.oss.controller

import com.github.pagehelper.PageHelper
import com.google.gson.Gson
import io.renren.common.exception.RRException
import io.renren.common.utils.ConfigConstant
import io.renren.common.utils.Constant
import io.renren.common.utils.Result
import io.renren.common.validator.ValidatorUtils
import io.renren.common.validator.group.AliyunGroup
import io.renren.common.validator.group.QcloudGroup
import io.renren.common.validator.group.QiniuGroup
import io.renren.modules.oss.cloud.CloudStorageConfig
import io.renren.modules.oss.cloud.OSSFactory
import io.renren.modules.oss.entity.SysOssEntity
import io.renren.modules.oss.service.SysOssService
import io.renren.modules.sys.service.SysConfigService
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.*


/**
 * 文件上传
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-25 12:13:26
 */
@RestController
@RequestMapping("sys/oss")
class SysOssController @Autowired constructor(private val sysOssService: SysOssService, private val sysConfigService: SysConfigService) {
    private val log = LoggerFactory.getLogger(SysOssController::class.java)
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:oss:all")
    fun list(@RequestParam params: Map<String, Any>): Result {
        //查询列表数据
        val pageInfo = PageHelper.startPage<Any>((params["page"] as String).toInt(), (params["limit"] as String).toInt())
                .doSelectPageInfo<SysOssEntity> { sysOssService.queryList(params) }
        return Result().ok().put("page", pageInfo)
    }


    /**
     * 云存储配置信息
     */
    @RequestMapping("/config")
    @RequiresPermissions("sys:oss:all")
    fun config(): Result {
        val config = sysConfigService.getConfigObject(KEY, CloudStorageConfig::class.java)
        return Result().ok().put("config", config)
    }


    /**
     * 保存云存储配置信息
     */
    @RequestMapping("/saveConfig")
    @RequiresPermissions("sys:oss:all")
    fun saveConfig(@RequestBody config: CloudStorageConfig): Result {
        //校验类型
        ValidatorUtils.validateEntity(config)

        when (config.type) {
        //校验七牛数据
            Constant.CloudService.QINIU.value -> ValidatorUtils.validateEntity(config, QiniuGroup::class.java)
        //校验阿里云数据
            Constant.CloudService.ALIYUN.value -> ValidatorUtils.validateEntity(config, AliyunGroup::class.java)
        //校验腾讯云数据
            Constant.CloudService.QCLOUD.value -> ValidatorUtils.validateEntity(config, QcloudGroup::class.java)
        }

        try {
            sysConfigService.updateValueByKey(KEY, Gson().toJson(config))
        } catch (e: Exception) {
            log.error("保存云存储配置信息异常", e)
            return Result().error(msg = "保存云存储配置信息")
        }

        return Result().ok()
    }


    /**
     * 上传文件
     */
    @RequestMapping("/upload")
    @RequiresPermissions("sys:oss:all")
    @Throws(Exception::class)
    fun upload(@RequestParam("file") file: MultipartFile): Result {
        if (file.isEmpty) {
            throw RRException("上传文件不能为空")
        }

        //上传文件
        val url = OSSFactory.build()!!.upload(file.bytes)

        //保存文件信息
        val ossEntity = SysOssEntity()
        ossEntity.url = url
        ossEntity.createDate = Date()
        sysOssService.save(ossEntity)

        return Result().ok().put("url", url)
    }


    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:oss:all")
    fun delete(@RequestBody ids: Array<Long>): Result {
        try {
            sysOssService.deleteBatch(ids)
        } catch (e: Exception) {
            log.error("删除文件异常", e)
            return Result().error(msg = "删除文件异常")
        }

        return Result().ok()
    }

    companion object {

        private val KEY = ConfigConstant.CLOUD_STORAGE_CONFIG_KEY
    }

}
