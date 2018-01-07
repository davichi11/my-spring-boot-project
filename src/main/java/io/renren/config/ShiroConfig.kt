package io.renren.config

import io.renren.modules.sys.oauth2.OAuth2Filter
import io.renren.modules.sys.oauth2.OAuth2Realm
import org.apache.shiro.mgt.SecurityManager
import org.apache.shiro.session.mgt.SessionManager
import org.apache.shiro.spring.LifecycleBeanPostProcessor
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
import org.apache.shiro.spring.web.ShiroFilterFactoryBean
import org.apache.shiro.web.mgt.DefaultWebSecurityManager
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*
import javax.servlet.Filter

/**
 * Shiro配置
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-04-20 18:33
 */
@Configuration
class ShiroConfig {

    @Bean("sessionManager")
    fun sessionManager(): SessionManager {
        val sessionManager = DefaultWebSessionManager()
        sessionManager.isSessionValidationSchedulerEnabled = true
        sessionManager.isSessionIdUrlRewritingEnabled = false
        //sessionManager.setSessionIdCookieEnabled(false);
        return sessionManager
    }

    @Bean("securityManager")
    fun securityManager(oAuth2Realm: OAuth2Realm, sessionManager: SessionManager): SecurityManager {
        val securityManager = DefaultWebSecurityManager()
        securityManager.setRealm(oAuth2Realm)
        securityManager.sessionManager = sessionManager

        return securityManager
    }

    @Bean("shiroFilter")
    fun shirFilter(securityManager: SecurityManager): ShiroFilterFactoryBean {
        val shiroFilter = ShiroFilterFactoryBean()
        shiroFilter.securityManager = securityManager

        //oauth过滤
        val filters = HashMap<String, Filter>(16)
        filters.put("oauth2", OAuth2Filter())
        shiroFilter.filters = filters

        val filterMap = LinkedHashMap<String, String>()
        filterMap.put("/webjars/**", "anon")
        filterMap.put("/druid/**", "anon")
        filterMap.put("/api/**", "anon")

        //swagger配置
        filterMap.put("/swagger**", "anon")
        filterMap.put("/v2/api-docs", "anon")
        filterMap.put("/swagger-resources/configuration/ui", "anon")

        filterMap.put("/sys/login", "anon")
        filterMap.put("/**/*.css", "anon")
        filterMap.put("/**/*.js", "anon")
        filterMap.put("/**/*.html", "anon")
        filterMap.put("/fonts/**", "anon")
        filterMap.put("/plugins/**", "anon")
        filterMap.put("/favicon.ico", "anon")
        filterMap.put("/captcha.jpg", "anon")
        filterMap.put("/", "anon")
        filterMap.put("/**", "oauth2")
        shiroFilter.filterChainDefinitionMap = filterMap

        return shiroFilter
    }

    @Bean("lifecycleBeanPostProcessor")
    fun lifecycleBeanPostProcessor(): LifecycleBeanPostProcessor {
        return LifecycleBeanPostProcessor()
    }

    @Bean
    fun defaultAdvisorAutoProxyCreator(): DefaultAdvisorAutoProxyCreator {
        val proxyCreator = DefaultAdvisorAutoProxyCreator()
        proxyCreator.isProxyTargetClass = true
        return proxyCreator
    }

    @Bean
    fun authorizationAttributeSourceAdvisor(securityManager: SecurityManager): AuthorizationAttributeSourceAdvisor {
        val advisor = AuthorizationAttributeSourceAdvisor()
        advisor.securityManager = securityManager
        return advisor
    }

}
