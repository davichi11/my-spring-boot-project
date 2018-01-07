package io.renren.common.exception

import io.renren.common.utils.Result
import org.apache.shiro.authz.AuthorizationException
import org.slf4j.LoggerFactory
import org.springframework.dao.DuplicateKeyException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * 异常处理器
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年10月27日 下午10:16:19
 */
@RestControllerAdvice
class RRExceptionHandler {
    private val logger = LoggerFactory.getLogger(javaClass)

    /**
     * 自定义异常
     */
    @ExceptionHandler(RRException::class)
    fun handleRRException(e: RRException): Result {
        val result = Result()
        result.put("code", e.code)
        e.message?.let { result.put("msg", it) }
        return result
    }

    @ExceptionHandler(DuplicateKeyException::class)
    fun handleDuplicateKeyException(e: DuplicateKeyException): Result {
        logger.error(e.message, e)
        return Result().error(msg = "数据库中已存在该记录")
    }

    @ExceptionHandler(AuthorizationException::class)
    fun handleAuthorizationException(e: AuthorizationException): Result {
        logger.error(e.message, e)
        return Result().error(msg = "没有权限，请联系管理员授权")
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): Result {
        logger.error(e.message, e)
        return Result().error()
    }
}
