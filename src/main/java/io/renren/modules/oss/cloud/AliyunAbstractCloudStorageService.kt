package io.renren.modules.oss.cloud

import com.aliyun.oss.OSSClient
import io.renren.common.exception.RRException

import java.io.ByteArrayInputStream
import java.io.InputStream

/**
 * 阿里云存储
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-26 16:22
 */
class AliyunAbstractCloudStorageService(config: CloudStorageConfig) : AbstractCloudStorageService() {
    private var client: OSSClient? = null

    init {
        this.config = config

        //初始化
        init()
    }

    private fun init() {
        client = OSSClient(config!!.aliyunEndPoint, config!!.aliyunAccessKeyId!!,
                config!!.aliyunAccessKeySecret!!)
    }

    override fun upload(data: ByteArray, path: String): String {
        return upload(ByteArrayInputStream(data), path)
    }

    override fun upload(inputStream: InputStream, path: String): String {
        try {
            client!!.putObject(config!!.aliyunBucketName, path, inputStream)
        } catch (e: Exception) {
            throw RRException("上传文件失败，请检查配置信息", e)
        }

        return config!!.aliyunDomain + "/" + path
    }

    override fun upload(data: ByteArray): String {
        return upload(data, getPath(config!!.aliyunPrefix!!))
    }

    override fun upload(inputStream: InputStream): String {
        return upload(inputStream, getPath(config!!.aliyunPrefix!!))
    }
}
