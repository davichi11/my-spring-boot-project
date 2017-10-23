package io.renren.modules.api.controller;


import io.renren.common.utils.Result;
import io.renren.common.validator.Assert;
import io.renren.modules.api.annotation.AuthIgnore;
import io.renren.modules.api.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注册
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-26 17:27
 */
@Slf4j
@RestController
@RequestMapping("/api")
@Api("注册接口")
public class ApiRegisterController {
    @Autowired
    private UserService userService;

    /**
     * 注册
     */
    @AuthIgnore
    @PostMapping("register")
    @ApiOperation(value = "注册")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "mobile", value = "手机号", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "password", value = "密码", required = true)
    })
    public Result register(String mobile, String password) {
        Assert.isBlank(mobile, "手机号不能为空");
        Assert.isBlank(password, "密码不能为空");

        try {
            userService.save(mobile, password);
        } catch (Exception e) {
            log.error("注册异常", e);
            return Result.error("注册异常");
        }

        return Result.ok();
    }
}
