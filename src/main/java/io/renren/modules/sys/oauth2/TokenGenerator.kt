package io.renren.modules.sys.oauth2

import io.renren.common.exception.RRException
import java.security.MessageDigest
import java.util.*
import kotlin.experimental.and

/**
 * 生成token
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-05-20 14:41
 */
object TokenGenerator {

    private val HEX_CODE = "0123456789abcdef".toCharArray()

    fun generateValue(): String? {
        return generateValue(UUID.randomUUID().toString())
    }

    private fun toHexString(data: ByteArray?): String? {
        if (data == null) {
            return null
        }
        val r = StringBuilder(data.size * 2)
        for (b in data) {
            r.append(HEX_CODE[b.toInt() shr 4 and 0xF])
            r.append(HEX_CODE[(b and 0xF).toInt()])
        }
        return r.toString()
    }

    private fun generateValue(param: String): String? {
        try {
            val algorithm = MessageDigest.getInstance("MD5")
            algorithm.reset()
            algorithm.update(param.toByteArray())
            val messageDigest = algorithm.digest()
            return toHexString(messageDigest)
        } catch (e: Exception) {
            throw RRException("生成Token失败", e)
        }

    }
}
