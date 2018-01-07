package io.renren.datasources.aspect

import io.renren.datasources.DataSourceNames
import io.renren.datasources.DynamicDataSource
import io.renren.datasources.annotation.DataSource
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.stereotype.Component

/**
 * 多数据源 切面类
 *
 * @author ChunLiang Hu
 * @Company
 * @Project renren-fast
 * @Package io.renren.datasources.aspect
 * @Description TODO(描述)
 * @create 2017/10/21-13:06
 */
@Aspect
@Component
class DataSourceAspect : Ordered {
    private val log = LoggerFactory.getLogger(DataSourceAspect::class.java)
    @Pointcut("@annotation(io.renren.datasources.annotation.DataSource)")
    fun dataSourcePointCut() {

    }

    @Around("dataSourcePointCut()")
    @Throws(Throwable::class)
    fun around(point: ProceedingJoinPoint): Any {
        val signature = point.signature as MethodSignature
        val method = signature.method

        val ds = method.getAnnotation(DataSource::class.java)
        if (ds == null) {
            DynamicDataSource.dataSource = DataSourceNames.FIRST
            log.debug("set datasource is " + DataSourceNames.FIRST)
        } else {
            DynamicDataSource.dataSource = ds.name
            log.debug("set datasource is " + ds.name)
        }

        try {
            return point.proceed()
        } finally {
            DynamicDataSource.clearDataSource()
            log.debug("clean datasource")
        }
    }

    override fun getOrder(): Int {
        return 1
    }
}
