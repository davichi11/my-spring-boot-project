package io.renren.datasources.annotation;

import java.lang.annotation.*;

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
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String name() default "";
}
