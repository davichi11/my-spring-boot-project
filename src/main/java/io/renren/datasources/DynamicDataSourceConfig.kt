package io.renren.datasources

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import javax.sql.DataSource

/**
 * 配置多数据源
 *
 * @author ChunLiang Hu
 * @Company
 * @Project renren-fast
 * @Package io.renren.datasources
 * @Description TODO(描述)
 * @create 2017/10/21-13:10
 */
@Configuration
class DynamicDataSourceConfig {
    @Bean
    @ConfigurationProperties("spring.datasource.druid.first")
    fun firstDataSource(): DataSource {
        return DruidDataSourceBuilder.create().build()
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.second")
    fun secondDataSource(): DataSource {
        return DruidDataSourceBuilder.create().build()
    }

    @Bean
    @Primary
    fun dataSource(firstDataSource: DataSource, secondDataSource: DataSource): DynamicDataSource {
        val targetDataSources = mutableMapOf<String, DataSource>()
        targetDataSources.put(DataSourceNames.FIRST, firstDataSource)
        targetDataSources.put(DataSourceNames.SECOND, secondDataSource)
        return DynamicDataSource(firstDataSource, targetDataSources)
    }
}
