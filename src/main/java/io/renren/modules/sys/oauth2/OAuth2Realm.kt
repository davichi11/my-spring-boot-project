package io.renren.modules.sys.oauth2

import io.renren.modules.sys.entity.SysUserEntity
import io.renren.modules.sys.service.ShiroService
import org.apache.shiro.authc.*
import org.apache.shiro.authz.AuthorizationInfo
import org.apache.shiro.authz.SimpleAuthorizationInfo
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.subject.PrincipalCollection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.ZoneId

/**
 * 认证
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-05-20 14:00
 */
@Component
class OAuth2Realm @Autowired constructor(private val shiroService: ShiroService) : AuthorizingRealm() {

    override fun supports(token: AuthenticationToken?): Boolean {
        return token is OAuth2Token
    }

    /**
     * 授权(验证权限时调用)
     */
    override fun doGetAuthorizationInfo(principals: PrincipalCollection): AuthorizationInfo {
        val user = principals.primaryPrincipal as SysUserEntity
        val userId = user.userId

        //用户权限列表
        val permsSet = shiroService.getUserPermissions(userId!!)

        val info = SimpleAuthorizationInfo()
        info.stringPermissions = permsSet
        return info
    }

    /**
     * 认证(登录时调用)
     */
    @Throws(AuthenticationException::class)
    override fun doGetAuthenticationInfo(token: AuthenticationToken): AuthenticationInfo {
        val accessToken = token.principal as String

        //根据accessToken，查询用户信息
        val tokenEntity = shiroService.queryByToken(accessToken)
        val expireTime = tokenEntity.expireTime!!.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        //token失效
        if (tokenEntity.userId == null || expireTime < Instant.now().toEpochMilli()) {
            throw IncorrectCredentialsException("token失效，请重新登录")
        }

        //查询用户信息
        val user = shiroService.queryUser(tokenEntity.userId!!)
        //账号锁定
        if (user.status == 0) {
            throw LockedAccountException("账号已被锁定,请联系管理员")
        }

        return SimpleAuthenticationInfo(user, accessToken, name)
    }
}
