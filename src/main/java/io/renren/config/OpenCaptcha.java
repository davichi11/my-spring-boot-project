package io.renren.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project renren-fast
 * @Package io.renren.config
 * @Description 验证码开启开关配置
 * @create 2017/10/23-15:29
 */
@ConfigurationProperties(prefix = "renren.openKaptcha")
@Component
public class OpenCaptcha {
    /**
     * 是否开启验证码
     */
    private String isOpen;

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }
}
