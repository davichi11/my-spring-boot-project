package io.renren.common.aspect

import io.renren.common.exception.RRException
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

/**
 * redis切面处理类
 *
 * @author ChunLiang Hu
 * @Company
 * @Project renren-fast
 * @Package io.renren.common.aspect
 * @Description TODO(描述)
 * @create 2017/10/21-11:56
 */
@Aspect
@Configuration
class RedisAspect {
    private val log = LoggerFactory.getLogger(RedisAspect::class.java)
    /**
     * 是否开启redis缓存  true开启   false关闭
     */
    @Value("\${spring.redis.open: true}")
    private val open: Boolean = false

    @Around("execution(* io.renren.common.cache.RedisCache.*(..))")
    @Throws(Throwable::class)
    fun around(point: ProceedingJoinPoint): Any? {
        var result: Any? = null
        if (open) {
            try {
                result = point.proceed()
            } catch (e: Exception) {
                log.error("redis error", e)
                throw RRException("Redis服务异常")
            }

        }
        return result
    }
}
