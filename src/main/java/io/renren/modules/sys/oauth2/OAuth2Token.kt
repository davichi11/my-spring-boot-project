package io.renren.modules.sys.oauth2


import org.apache.shiro.authc.AuthenticationToken

/**
 * token
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-05-20 13:22
 */
class OAuth2Token(private val token: String?) : AuthenticationToken {

    override fun getPrincipal(): String? {
        return token
    }

    override fun getCredentials(): Any? {
        return token
    }
}
