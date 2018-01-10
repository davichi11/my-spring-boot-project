package io.renren.common.xss

import java.io.IOException
import javax.servlet.*
import javax.servlet.http.HttpServletRequest

/**
 * XSS过滤
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-04-01 10:20
 */
class XssFilter : Filter {

    override fun init(config: FilterConfig) {}

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val xssRequest = XssHttpServletRequestWrapper(request as HttpServletRequest)
        chain.doFilter(xssRequest, response)
    }

    override fun destroy() {}

}