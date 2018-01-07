package io.renren.datasources

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import java.util.*
import javax.sql.DataSource

/**
 * 动态数据源
 *
 * @author ChunLiang Hu
 * @Company
 * @Project renren-fast
 * @Package io.renren.datasources
 * @Description TODO(描述)
 * @create 2017/10/21-13:09
 */
class DynamicDataSource(defaultTargetDataSource: DataSource, targetDataSources: Map<String, DataSource>) : AbstractRoutingDataSource() {

    init {
        super.setDefaultTargetDataSource(defaultTargetDataSource)
        super.setTargetDataSources(HashMap<Any, Any>(targetDataSources))
        super.afterPropertiesSet()
    }

    override fun determineCurrentLookupKey(): Any? {
        return dataSource
    }

    companion object {
        private val CONTEXT_HOLDER = ThreadLocal<String>()

        var dataSource: String = ""
            set(dataSource) = CONTEXT_HOLDER.set(dataSource)
//            get() = CONTEXT_HOLDER.get()

        fun clearDataSource() {
            CONTEXT_HOLDER.remove()
        }
    }
}
