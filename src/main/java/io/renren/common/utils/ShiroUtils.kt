package io.renren.common.utils

import io.renren.common.exception.RRException
import io.renren.modules.sys.entity.SysUserEntity
import org.apache.shiro.SecurityUtils
import org.apache.shiro.session.Session
import org.apache.shiro.subject.Subject

/**
 * Shiro工具类
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年11月12日 上午9:49:19
 */
object ShiroUtils {

    val session: Session
        get() = SecurityUtils.getSubject().session

    val subject: Subject
        get() = SecurityUtils.getSubject()

    val userEntity: SysUserEntity
        get() = SecurityUtils.getSubject().principal as SysUserEntity

    val userId: Long?
        get() = userEntity.userId

    val isLogin: Boolean
        get() = SecurityUtils.getSubject().principal != null

    fun getCaptcha(key: String): String {
        val captcha = getSessionAttribute(key) ?: throw RRException("验证码已失效")
        session.removeAttribute(key)
        return captcha.toString()
    }

    fun setSessionAttribute(key: Any, value: Any) {
        session.setAttribute(key, value)
    }

    fun getSessionAttribute(key: Any): Any? {
        return session.getAttribute(key)
    }

    fun logout() {
        SecurityUtils.getSubject().logout()
    }


}
