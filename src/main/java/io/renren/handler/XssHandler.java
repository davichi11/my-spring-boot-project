package io.renren.handler;

import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project renren-fast
 * @Package io.renren.handler
 * @Description TODO(描述)
 * @create 2017/11/6-10:06
 */
@Component
@Slf4j
public class XssHandler {
    public void xssHandler(RoutingContext routingContext) {
        if (routingContext.request().getHeader(HttpHeaders.CONTENT_TYPE).equals(MediaType.APPLICATION_JSON_VALUE)) {
            return;
        }

        if (routingContext.pathParams().isEmpty()) {
            return;
        }


    }
}
