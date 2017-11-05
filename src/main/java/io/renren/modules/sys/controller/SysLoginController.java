package io.renren.modules.sys.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import io.renren.common.utils.Result;
import io.renren.common.utils.ShiroUtils;
import io.renren.config.OpenCaptcha;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;
import io.renren.modules.sys.service.SysUserTokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.ipc.netty.http.server.HttpServerResponse;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * 登录相关
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年11月10日 下午1:15:31
 */
@Slf4j
@RestController
public class SysLoginController {
    @Autowired
    private Producer producer;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserTokenService sysUserTokenService;
    @Autowired
    private OpenCaptcha captcha;


    @RequestMapping("captcha.jpg")
    public void captcha(HttpServerResponse response) throws IOException {
        response.addHeader("Cache-Control", "no-store, no-cache");
        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);
//
//        ServletOutputStream out = response.getOutputStream();
//        ImageIO.write(image, "jpg", out);
//        IOUtils.closeQuietly(out);


    }

    /**
     * 登录
     */
    @RequestMapping(value = "/sys/login", method = RequestMethod.POST)
    public Map<String, Object> login(String username, String password, String captcha) throws IOException {
        //是否开启验证码
        if (BooleanUtils.toBoolean(this.captcha.getIsOpen())) {
            String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
            if (!captcha.equalsIgnoreCase(kaptcha)) {
                return Result.error("验证码不正确");
            }
        }

        //用户信息
        SysUserEntity user = sysUserService.queryByUserName(username);

        //账号不存在、密码错误
        if (user == null || !user.getPassword().equals(new Sha256Hash(password, user.getSalt()).toHex())) {
            return Result.error("账号或密码不正确");
        }

        //账号锁定
        if (user.getStatus() == 0) {
            return Result.error("账号已被锁定,请联系管理员");
        }

        //生成token，并保存到数据库
        try {
            return sysUserTokenService.createToken(user.getUserId());
        } catch (Exception e) {
            log.error("生产token异常", e);
            return Result.error("生产token异常");
        }
    }

}
