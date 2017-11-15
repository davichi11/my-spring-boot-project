package io.renren;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import io.renren.handler.FailureHandler;
import org.springframework.stereotype.Component;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project renren-fast
 * @Package io.renren
 * @Description TODO(描述)
 * @create 2017/10/31-21:08
 */
@Component
public class VertXServer extends AbstractVerticle {
    @Value(value = "${server.port}")
    private Integer port;

    @Autowired
    private Router router;
    @Autowired
    private HttpServer httpServer;

    @Autowired
    private FailureHandler failureHandler;

    @Override
    public void start() throws Exception {
        super.start();
        Router router = this.router;
        this.httpFailureHandler();
        this.routeStatic();
        this.vertx.createHttpServer().requestHandler(router::accept).listen(this.port);
    }

    private void xssHandler() {
        this.router.route().blockingHandler();
    }

    private void httpFailureHandler() {
        this.router.route().failureHandler(this.failureHandler::httpFailureHandler);
    }

    private void routeStatic() {
        this.router.route().handler(StaticHandler.create());
    }
}
