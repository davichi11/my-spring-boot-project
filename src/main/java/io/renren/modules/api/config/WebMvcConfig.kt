package io.renren.modules.api.config

import io.renren.modules.api.interceptor.AuthorizationInterceptor
import io.renren.modules.api.resolver.LoginUserHandlerMethodArgumentResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

/**
 * MVC配置
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-04-20 22:30
 */
@Configuration
class WebMvcConfig @Autowired constructor(private val authorizationInterceptor: AuthorizationInterceptor, private val loginUserHandlerMethodArgumentResolver: LoginUserHandlerMethodArgumentResolver) : WebMvcConfigurerAdapter() {

    override fun addInterceptors(registry: InterceptorRegistry?) {
        registry!!.addInterceptor(authorizationInterceptor).addPathPatterns("/api/**")
    }

    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>?) {
        argumentResolvers!!.add(loginUserHandlerMethodArgumentResolver)
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry?) {
        registry!!.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/")

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/")

    }
}