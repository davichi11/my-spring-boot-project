package io.renren.datasources.annotation


/**
 * 多数据源注解
 *
 * @author ChunLiang Hu
 * @Company
 * @Project renren-fast
 * @Package io.renren.datasources.annotation
 * @Description TODO(描述)
 * @create 2017/10/21-13:05
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class DataSource(val name: String = "")
