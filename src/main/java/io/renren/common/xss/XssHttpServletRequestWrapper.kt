package io.renren.common.xss

import org.apache.commons.io.IOUtils
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import java.io.ByteArrayInputStream
import java.io.IOException
import javax.servlet.ReadListener
import javax.servlet.ServletInputStream
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper

/**
 * XSS过滤处理
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-04-01 11:29
 */
class XssHttpServletRequestWrapper(
        /**
         * 没被包装过的HttpServletRequest（特殊场景，需要自己过滤）
         */
        /**
         * 获取最原始的request
         */
        val orgRequest: HttpServletRequest) : HttpServletRequestWrapper(orgRequest) {

    @Throws(IOException::class)
    override fun getInputStream(): ServletInputStream {
        //非json类型，直接返回
        if (!super.getHeader(HttpHeaders.CONTENT_TYPE).equals(MediaType.APPLICATION_JSON_VALUE, ignoreCase = true)) {
            return super.getInputStream()
        }

        //为空，直接返回
        var json = IOUtils.toString(super.getInputStream(), "utf-8")
        if (json.isBlank()) {
            return super.getInputStream()
        }

        //xss过滤
        json = xssEncode(json)
        val bis = ByteArrayInputStream(json.toByteArray(charset("utf-8")))
        return object : ServletInputStream() {
            override fun isFinished(): Boolean {
                return false
            }

            override fun isReady(): Boolean {
                return false
            }

            override fun setReadListener(readListener: ReadListener) {

            }

            override fun read(): Int {
                return bis.read()
            }
        }
    }

    override fun getParameter(name: String): String? {
        var value = super.getParameter(xssEncode(name))
        if (value.isNotBlank()) {
            value = xssEncode(value)
        }
        return value
    }

    override fun getParameterValues(name: String): Array<String>? {
        val parameters = super.getParameterValues(name)?: arrayOf()
        if (parameters.isEmpty()) {
            return null
        }
        return parameters.map { xssEncode(it) }.toTypedArray()
    }

    override fun getParameterMap(): Map<String, Array<String>> {
        val parameters = super.getParameterMap()
        parameters.forEach { _, value -> value.forEach({ this.xssEncode(it) }) }
        return LinkedHashMap(parameters)
    }

    override fun getHeader(name: String): String? {
        val value = super.getHeader(xssEncode(name))
        return if (value != null && value.isNotBlank()) xssEncode(value) else value
    }

    private fun xssEncode(input: String): String {
        return HTML_FILTER.filter(input)
    }

    companion object {
        /**
         * html过滤
         */
        private val HTML_FILTER = HTMLFilter()

        /**
         * 获取最原始的request
         */
        fun getOrgRequest(request: HttpServletRequest): HttpServletRequest {
            return (request as? XssHttpServletRequestWrapper)?.orgRequest ?: request

        }
    }


}
