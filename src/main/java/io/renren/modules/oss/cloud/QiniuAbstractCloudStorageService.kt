package io.renren.modules.oss.cloud

import com.qiniu.common.Zone
import com.qiniu.storage.Configuration
import com.qiniu.storage.UploadManager
import com.qiniu.util.Auth
import io.renren.common.exception.RRException
import org.apache.commons.io.IOUtils
import java.io.IOException
import java.io.InputStream

/**
 * 七牛云存储
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-25 15:41
 */
class QiniuAbstractCloudStorageService(config: CloudStorageConfig) : AbstractCloudStorageService() {
    private var uploadManager: UploadManager? = null
    private var token: String? = null

    init {
        this.config = config

        //初始化
        init()
    }

    private fun init() {
        uploadManager = UploadManager(Configuration(Zone.autoZone()))
        token = Auth.create(config!!.qiniuAccessKey, config!!.qiniuSecretKey!!).uploadToken(config!!.qiniuBucketName)
    }

    override fun upload(data: ByteArray, path: String): String {
        try {
            val res = uploadManager!!.put(data, path, token)
            if (!res.isOK) {
                throw RuntimeException("上传七牛出错：" + res.toString())
            }
        } catch (e: Exception) {
            throw RRException("上传文件失败，请核对七牛配置信息", e)
        }

        return config!!.qiniuDomain + "/" + path
    }

    override fun upload(inputStream: InputStream, path: String): String {
        try {
            val data = IOUtils.toByteArray(inputStream)
            return this.upload(data, path)
        } catch (e: IOException) {
            throw RRException("上传文件失败", e)
        }

    }

    override fun upload(data: ByteArray): String {
        return upload(data, getPath(config!!.qiniuPrefix!!))
    }

    override fun upload(inputStream: InputStream): String {
        return upload(inputStream, getPath(config!!.qiniuPrefix!!))
    }
}
