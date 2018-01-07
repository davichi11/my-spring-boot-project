package io.renren.common.utils

import org.apache.http.HttpStatus
import java.util.*

/**
 * 返回数据
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年10月27日 下午9:59:27
 */
class Result : HashMap<String, Any>() {
    init {
        put("code", 0)
    }

    override fun put(key: String, value: Any): Result {
        super.put(key, value)
        return this
    }

    fun error(code: Int = HttpStatus.SC_INTERNAL_SERVER_ERROR, msg: String = "未知异常，请联系管理员"): Result {
        val result = Result()
        result.put("code", code)
        result.put("msg", msg)
        return result
    }

    fun ok(msg: String = ""): Result {
        val result = Result()
        result.put("msg", msg)
        return result
    }

    fun ok(map: Map<String, Any>): Result {
        val result = Result()
        result.putAll(map)
        return result
    }


}
