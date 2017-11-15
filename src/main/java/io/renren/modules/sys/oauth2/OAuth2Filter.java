package io.renren.modules.sys.oauth2;

import com.google.gson.Gson;
import io.renren.common.utils.Result;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * oauth2过滤器
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-05-20 13:00
 */
public class OAuth2Filter {

    protected AuthenticationToken createToken(HttpServerRequest request, HttpServerResponse response) throws Exception {
        //获取请求token
        String token = getRequestToken(request);

        if (StringUtils.isBlank(token)) {
            return null;
        }

        return new OAuth2Token(token);
    }

    protected boolean isAccessAllowed(HttpServerRequest request, HttpServerResponse response, Object mappedValue) {
        return false;
    }

    protected boolean onAccessDenied(HttpServerRequest request, HttpServerResponse response) throws Exception {
        //获取请求token，如果token不存在，直接返回401
        String token = getRequestToken(request);
        if (StringUtils.isBlank(token)) {
            String json = new Gson().toJson(Result.error(HttpStatus.SC_UNAUTHORIZED, "invalid token"));
            request.response().write(json).end();

            return false;
        }

        return executeLogin(request, response);
    }

    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, HttpServerRequest request, HttpServerResponse response) {
        try {
            //处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            Result result = Result.error(HttpStatus.SC_UNAUTHORIZED, throwable.getMessage());

            String json = new Gson().toJson(result);
            request.response().write(json).end();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return false;
    }

    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServerRequest httpRequest) {
        //从header中获取token
        String token = httpRequest.getHeader("token");

        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = httpRequest.getParam("token");
        }

        return token;
    }


}
