package io.renren

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

/**
 * swagger2 配置类
 * @author ChunLiang Hu
 * @Company
 * @Project renren-fast
 * @Package io.renren
 * @create 2017/10/25-19:48
 */
@Configuration
@EnableSwagger2
class Swagger2 {
    @Bean
    fun createRestApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("io.renren.modules.api.controller"))
                .paths(PathSelectors.any())
                .build()
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
                .title("API文档").description("api根地址：http://localhost:8088/").termsOfServiceUrl("http://localhost:8088/")
                .contact(Contact("ChunLiang Hu", "", "")).version("1.0")
                .build()
    }
}
