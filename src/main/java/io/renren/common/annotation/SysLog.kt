package io.renren.common.annotation

/**
 * 系统日志注解
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017年3月8日 上午10:19:56
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class SysLog(val value: String = "")
