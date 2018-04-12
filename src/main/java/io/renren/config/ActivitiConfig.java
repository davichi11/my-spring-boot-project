package io.renren.config;

import io.renren.datasources.DynamicDataSourceConfig;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * activiti配置
 * @author ChunLiang Hu
 * @Company
 * @Project renren-fast
 * @Package io.renren.config
 * @Description TODO(描述)
 * @create 2018/3/13-10:34
 */
@Configuration
public class ActivitiConfig {
    @Autowired
    private PlatformTransactionManager platformTransactionManager;
    @Autowired
    private DynamicDataSourceConfig dynamicDataSourceConfig;
    @Bean
    public SpringProcessEngineConfiguration getProcessEngineConfiguration() {
        SpringProcessEngineConfiguration config = new SpringProcessEngineConfiguration();
        config.setDataSource(dynamicDataSourceConfig.firstDataSource());
        config.setTransactionManager(platformTransactionManager);
        config.setDatabaseType("mysql");
        config.setDatabaseSchemaUpdate("true");
        return config;
    }
}
