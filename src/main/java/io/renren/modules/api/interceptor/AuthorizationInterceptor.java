package io.renren.modules.api.interceptor;


import io.jsonwebtoken.Claims;
import io.renren.common.exception.RRException;
import io.renren.modules.api.annotation.AuthIgnore;
import io.renren.modules.api.config.JWTConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * 权限(Token)验证
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-23 15:38
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JWTConfig jwtConfig;

    public static final String LOGIN_USER_KEY = "LOGIN_USER_KEY";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AuthIgnore annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthIgnore.class);
        } else {
            return true;
        }

        //如果有@IgnoreAuth注解，则不验证token
        if (annotation != null) {
            return true;
        }

        //从header中获取token,如果header中不存在token，则从参数中获取token
        String token = StringUtils.isBlank(request.getHeader(jwtConfig.getHeader())) ?
                request.getParameter(jwtConfig.getHeader()) : request.getHeader(jwtConfig.getHeader());


        //token为空
        if (StringUtils.isBlank(token)) {
            throw new RRException("token不能为空");
        }

        Claims claims = jwtConfig.getClaimByToken(token);
        if (claims == null || jwtConfig.isTokenExpired(LocalDateTime.from(claims.getExpiration().toInstant()))) {
            throw new RRException(jwtConfig.getHeader() + "失效，请重新登录", HttpStatus.UNAUTHORIZED.value());
        }

        //设置userId到request里，后续根据userId，获取用户信息
        request.setAttribute(LOGIN_USER_KEY, NumberUtils.toLong(claims.getSubject()));

        return true;
    }
}
