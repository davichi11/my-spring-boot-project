package io.renren.config

import io.renren.common.xss.XssFilter
import org.apache.shiro.web.servlet.ShiroFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.servlet.DispatcherType

/**
 * Filter配置
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-04-21 21:56
 */
@Configuration
class FilterConfig {

    @Bean
    fun shiroFilterRegistration(): FilterRegistrationBean<ShiroFilter> {
        val registration = FilterRegistrationBean<ShiroFilter>()
//        registration.filter = DelegatingFilterProxy("shiroFilter")
        //该值缺省为false，表示生命周期由SpringApplicationContext管理，设置为true则表示由ServletContainer管理
        registration.addInitParameter("targetFilterLifecycle", "true")
        registration.isEnabled = true
        registration.order = Integer.MAX_VALUE - 1
        registration.addUrlPatterns("/*")
        return registration
    }

    @Bean
    fun xssFilterRegistration(): FilterRegistrationBean<XssFilter> {
        val registration = FilterRegistrationBean<XssFilter>()
        registration.setDispatcherTypes(DispatcherType.REQUEST)
        registration.filter = XssFilter()
        registration.addUrlPatterns("/*")
        registration.setName("xssFilter")
        registration.order = Integer.MAX_VALUE
        return registration
    }
}
