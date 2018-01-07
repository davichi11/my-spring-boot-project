package io.renren.modules.sys.controller

import com.google.code.kaptcha.Constants
import com.google.code.kaptcha.Producer
import io.renren.common.utils.Result
import io.renren.common.utils.ShiroUtils
import io.renren.config.OpenCaptcha
import io.renren.modules.sys.service.SysUserService
import io.renren.modules.sys.service.SysUserTokenService
import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.BooleanUtils
import org.apache.shiro.crypto.hash.Sha256Hash
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.io.IOException
import javax.imageio.ImageIO
import javax.servlet.ServletException
import javax.servlet.http.HttpServletResponse

/**
 * 登录相关
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年11月10日 下午1:15:31
 */
@RestController
class SysLoginController @Autowired constructor(private val producer: Producer, private val sysUserService: SysUserService,
                                                private val sysUserTokenService: SysUserTokenService, private val captcha: OpenCaptcha) {
    private val log = LoggerFactory.getLogger(SysLoginController::class.java)

    @RequestMapping("captcha.jpg")
    @Throws(ServletException::class, IOException::class)
    fun captcha(response: HttpServletResponse) {
        response.setHeader("Cache-Control", "no-store, no-cache")
        response.contentType = "image/jpeg"

        //生成文字验证码
        val text = producer.createText()
        //生成图片验证码
        val image = producer.createImage(text)
        //保存到shiro session
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text)

        val out = response.outputStream
        ImageIO.write(image, "jpg", out)
        IOUtils.closeQuietly(out)


    }

    /**
     * 登录
     */
    @RequestMapping(value = ["/sys/login"], method = [(RequestMethod.POST)])
    @Throws(IOException::class)
    fun login(username: String, password: String, captcha: String): Map<String, Any> {
        //是否开启验证码
        if (BooleanUtils.toBoolean(this.captcha.isOpen)) {
            val kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY)
            if (!captcha.equals(kaptcha, ignoreCase = true)) {
                return Result().error(msg = "验证码不正确")
            }
        }

        //用户信息
        val user = sysUserService.queryByUserName(username)

        //账号不存在、密码错误
        if (user.password != Sha256Hash(password, user.salt).toHex()) {
            return Result().error(msg = "账号或密码不正确")
        }

        //账号锁定
        if (user.status == 0) {
            return Result().error(msg = "账号已被锁定,请联系管理员")
        }

        //生成token，并保存到数据库
        return try {
            sysUserTokenService.createToken(user.userId!!)
        } catch (e: Exception) {
            log.error("生产token异常", e)
            Result().error(msg = "生产token异常")
        }

    }

}
