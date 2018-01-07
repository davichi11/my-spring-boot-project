package io.renren.modules.oss.cloud

import io.renren.common.utils.ApplicationContextHolder
import io.renren.common.utils.ConfigConstant
import io.renren.common.utils.Constant
import io.renren.modules.sys.service.SysConfigService

/**
 * 文件上传Factory
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-26 10:18
 */
object OSSFactory {
    private var sysConfigService: SysConfigService? = null

    init {
        OSSFactory.sysConfigService = ApplicationContextHolder.getBean("sysConfigService") as SysConfigService
    }

    fun build(): AbstractCloudStorageService? {
        //获取云存储配置信息
        val config = sysConfigService!!.getConfigObject(ConfigConstant.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig::class.java)

        return when {
            config.type == Constant.CloudService.QINIU.value -> QiniuAbstractCloudStorageService(config)
            config.type == Constant.CloudService.ALIYUN.value -> AliyunAbstractCloudStorageService(config)
            config.type == Constant.CloudService.QCLOUD.value -> QcloudAbstractCloudStorageService(config)
            else -> null
        }

    }

}
