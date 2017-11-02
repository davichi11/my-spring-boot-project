package io.renren.common.controller;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project renren-fast
 * @Package io.renren.common.controller
 * @Description TODO(描述)
 * @create 2017/10/31-20:58
 */
@Component
@Slf4j
public abstract class BaseController {
    @Autowired
    protected Router router;

    protected void response(HttpServerResponse response, String json) {
        log.debug("response \t", json);
        response.putHeader("Content-Type", "application/json").end(Buffer.buffer(json));
    }

    /**
     * 返回成功
     *
     * @param routingContext
     * @param json
     */
    public void doSuccess(RoutingContext routingContext, String json) {
        routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusMessage("ok")
                .setStatusCode(200).end(json);
    }

    public void doError(RoutingContext routingContext, String errorMsg) {
        routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusMessage("error")
                .setStatusCode(500).end(errorMsg);
    }

    @PostConstruct
    protected void init() {
        this.deploy();
    }

    /**
     * 公用的deploy方法
     */
    protected abstract void deploy();
}
