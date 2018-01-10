package io.renren.common.validator

import io.renren.common.exception.RRException

/**
 * 数据校验
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-23 15:50
 */
object Assert {

    fun isBlank(str: String, message: String) {
        if (str.isBlank()) {
            throw RRException(message)
        }
    }
}
