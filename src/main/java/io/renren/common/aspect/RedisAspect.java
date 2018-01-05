package io.renren.common.aspect;

import io.renren.common.exception.RRException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

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
@Slf4j
@Aspect
@Configuration
public class RedisAspect {
    /**
     * 是否开启redis缓存  true开启   false关闭
     */
    @Value("${spring.redis.open: true}")
    private boolean open;

    @Around("execution(* io.renren.common.cache.RedisCache.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        if (open) {
            try {
                result = point.proceed();
            } catch (Exception e) {
                log.error("redis error", e);
                throw new RRException("Redis服务异常");
            }
        }
        return result;
    }
}
