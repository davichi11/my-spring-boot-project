package io.renren.modules.sys.oauth2

import com.google.gson.Gson
import io.renren.common.utils.Result
import org.apache.commons.lang.StringUtils
import org.apache.http.HttpStatus
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.authc.AuthenticationToken
import org.apache.shiro.web.filter.authc.AuthenticatingFilter
import java.io.IOException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * oauth2过滤器
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-05-20 13:00
 */
class OAuth2Filter : AuthenticatingFilter() {

    @Throws(Exception::class)
    override fun createToken(request: ServletRequest, response: ServletResponse): AuthenticationToken? {
        //获取请求token
        val token = getRequestToken(request as HttpServletRequest)

        return if (StringUtils.isBlank(token)) {
            null
        } else OAuth2Token(token)

    }

    override fun isAccessAllowed(request: ServletRequest, response: ServletResponse, mappedValue: Any?): Boolean {
        return false
    }

    @Throws(Exception::class)
    override fun onAccessDenied(request: ServletRequest, response: ServletResponse): Boolean {
        //获取请求token，如果token不存在，直接返回401
        val token = getRequestToken(request as HttpServletRequest)
        if (StringUtils.isBlank(token)) {
            val httpResponse = response as HttpServletResponse
            val json = Gson().toJson(Result().error(HttpStatus.SC_UNAUTHORIZED, "invalid token"))
            httpResponse.writer.print(json)

            return false
        }

        return executeLogin(request, response)
    }

    override fun onLoginFailure(token: AuthenticationToken?, e: AuthenticationException?, request: ServletRequest?, response: ServletResponse?): Boolean {
        response!!.contentType = "application/json;charset=utf-8"
        try {
            //处理登录失败的异常
            val throwable = if (e!!.cause == null) e else e.cause
            val result = throwable?.message?.let { Result().error(HttpStatus.SC_UNAUTHORIZED, it) }

            val json = Gson().toJson(result)
            response.writer.print(json)
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
        return false
    }

    /**
     * 获取请求的token
     */
    private fun getRequestToken(httpRequest: HttpServletRequest): String {
        //从header中获取token
        var token = httpRequest.getHeader("token")

        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = httpRequest.getParameter("token")
        }

        return token
    }


}
