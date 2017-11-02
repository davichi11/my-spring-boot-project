package io.renren.factory;

import io.renren.VertXServer;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project renren-fast
 * @Package io.renren.factory
 * @Description TODO(描述)
 * @create 2017/10/31-21:22
 */
@Slf4j
public class VertXInitFactory implements InitializingBean {

    @Autowired
    private VertXServer vertxServer;
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("init factory ... ");
        Vertx.vertx().deployVerticle(this.vertxServer , handler -> {
            if (handler.succeeded()) {
                log.info("deployVerticle succeefully");
            }else {
                log.error("deployVerticle fail : {}" , handler.cause().getLocalizedMessage());
            }
        });
    }
}
