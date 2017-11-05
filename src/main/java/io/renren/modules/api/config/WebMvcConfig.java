package io.renren.modules.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * MVC配置
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-04-20 22:30
 */
@Configuration
@EnableWebFlux
public class WebMvcConfig implements WebFluxConfigurer {
//    @Autowired
//    private AuthorizationInterceptor authorizationInterceptor;
//    @Autowired
//    private LoginUserHandlerMethodArgumentResolver loginUserHandlerMethodArgumentResolver;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/api/**");
//    }
//
//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//        argumentResolvers.add(loginUserHandlerMethodArgumentResolver);
//    }
}