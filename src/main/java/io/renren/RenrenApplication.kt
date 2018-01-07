package io.renren

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 * spring_boot 启动类
 *
 * @author huchunliang
 */
@SpringBootApplication
class RenrenApplication

fun main(args: Array<String>) {
    SpringApplication.run(RenrenApplication::class.java, *args)
}
