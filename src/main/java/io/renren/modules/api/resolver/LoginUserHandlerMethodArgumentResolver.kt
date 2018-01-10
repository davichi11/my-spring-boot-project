package io.renren.modules.api.resolver

import io.renren.modules.api.annotation.LoginUser
import io.renren.modules.api.entity.UserEntity
import io.renren.modules.api.interceptor.AuthorizationInterceptor
import io.renren.modules.api.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

/**
 * 有@LoginUser注解的方法参数，注入当前登录用户
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-23 22:02
 */
@Component
class LoginUserHandlerMethodArgumentResolver : HandlerMethodArgumentResolver {
    @Autowired
    private val userService: UserService? = null

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.parameterType.isAssignableFrom(UserEntity::class.java) && parameter.hasParameterAnnotation(LoginUser::class.java)
    }

    @Throws(Exception::class)
    override fun resolveArgument(parameter: MethodParameter, container: ModelAndViewContainer,
                                 request: NativeWebRequest, factory: WebDataBinderFactory): Any? {
        //获取用户ID
        val userId = request.getAttribute(AuthorizationInterceptor.LOGIN_USER_KEY, RequestAttributes.SCOPE_REQUEST) ?: return null
        //获取用户信息
        return userService!!.queryObject(userId as Long)
    }
}
