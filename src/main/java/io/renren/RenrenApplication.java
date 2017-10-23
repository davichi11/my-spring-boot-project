package io.renren;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * spring_boot 启动类
 *
 * @author huchunliang
 */
@SpringBootApplication
public class RenrenApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(RenrenApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(RenrenApplication.class);
    }
}
