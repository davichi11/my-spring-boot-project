package io.renren.modules.oss.cloud

import io.renren.common.utils.DateUtils
import java.io.InputStream
import java.time.LocalDate
import java.util.*

/**
 * 云存储(支持七牛、阿里云、腾讯云、又拍云)
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-25 14:58
 */
abstract class AbstractCloudStorageService {
    /**
     * 云存储配置信息
     */
    internal var config: CloudStorageConfig? = null

    /**
     * 文件路径
     *
     * @param prefix 前缀
     * @return 返回上传路径
     */
    fun getPath(prefix: String): String {
        //生成uuid
        val uuid = UUID.randomUUID().toString().replace("-".toRegex(), "")
        //文件路径
        var path = DateUtils.format(LocalDate.now(), "yyyyMMdd") + "/" + uuid

        if (prefix.isNotEmpty()) {
            path = prefix + "/" + path
        }

        return path
    }

    /**
     * 文件上传
     *
     * @param data 文件字节数组
     * @param path 文件路径，包含文件名
     * @return 返回http地址
     */
    abstract fun upload(data: ByteArray, path: String): String

    /**
     * 文件上传
     *
     * @param data 文件字节数组
     * @return 返回http地址
     */
    abstract fun upload(data: ByteArray): String

    /**
     * 文件上传
     *
     * @param inputStream 字节流
     * @param path        文件路径，包含文件名
     * @return 返回http地址
     */
    abstract fun upload(inputStream: InputStream, path: String): String

    /**
     * 文件上传
     *
     * @param inputStream 字节流
     * @return 返回http地址
     */
    abstract fun upload(inputStream: InputStream): String

}
