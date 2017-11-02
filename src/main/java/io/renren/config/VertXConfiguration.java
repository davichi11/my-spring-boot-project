package io.renren.config;

import io.renren.factory.VertXInitFactory;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project renren-fast
 * @Package io.renren.config
 * @Description TODO(描述)
 * @create 2017/10/31-21:21
 */
@Configuration
@Slf4j
public class VertXConfiguration {
    @Bean
    public Vertx vertx() {
        Vertx vertx = Vertx.vertx();
        log.info("vertx\t{}", vertx.toString());
        return vertx;
    }

    @Bean
    public Router router() {
        Vertx vertx = this.vertx();
        Router router = Router.router(vertx);
        router.route().handler(CookieHandler.create());
        router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));
        return router;
    }

    @Bean
    public HttpClient httpClient() {
        return this.vertx().createHttpClient();
    }

    @Bean
    public HttpServer httpServer() {
        return this.vertx().createHttpServer();
    }

    @Bean
    public VertXInitFactory vertxInitFactory() {
        return new VertXInitFactory();
    }
}
