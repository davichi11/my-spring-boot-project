package io.renren.modules.api.controller;


import com.alibaba.fastjson.JSON;
import io.renren.common.controller.BaseController;
import io.renren.common.utils.Result;
import io.renren.common.validator.Assert;
import io.renren.modules.api.annotation.AuthIgnore;
import io.renren.modules.api.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.vertx.core.MultiMap;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * 注册
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-26 17:27
 */
@Slf4j
@RestController
//@RequestMapping("/api")
@Api("注册接口")
public class ApiRegisterController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private Router router;

    /**
     * 注册
     */
    @AuthIgnore
    @ApiOperation(value = "注册")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "mobile", value = "手机号", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "password", value = "密码", required = true)
    })
    public void register(RoutingContext routingContext) {
        MultiMap params = routingContext.request().params();
        String mobile = params.get("mobile");
        String password = params.get("password");
        Assert.isBlank(mobile, "手机号不能为空");
        Assert.isBlank(password, "密码不能为空");
        try {
            userService.save(mobile, password);
        } catch (Exception e) {
            log.error("注册异常", e);
            doError(routingContext, JSON.toJSONString(Result.error("注册异常")));
        }
        doSuccess(routingContext,JSON.toJSONString(Result.ok()));
    }

    @Override
    @PostConstruct
    protected void deploy() {
        this.router.get("/api/register").handler(this::register);
    }
}
