package io.renren.handler;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 错误返回
 * @author ChunLiang Hu
 * @Company
 * @Project renren-fast
 * @Package io.renren.handler
 * @Description TODO(描述)
 * @create 2017/10/31-21:10
 */
@Component
@Slf4j
public class FailureHandler {
    public void httpFailureHandler(RoutingContext failureHandler) {
        Throwable failure = failureHandler.failure();
        JsonObject error = new JsonObject().put("code", 500).put("message", "Internal Server Error : " + failure.getLocalizedMessage());
        log.error("http error :", failure);
        failureHandler.response().putHeader("Content-Type", "application/json").end(error.toBuffer());
    }
}
