package io.renren.modules.api.controller


import io.renren.common.utils.Result
import io.renren.common.validator.Assert
import io.renren.modules.api.annotation.AuthIgnore
import io.renren.modules.api.service.UserService
import io.swagger.annotations.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 注册
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-26 17:27
 */
@RestController
@RequestMapping("/api")
@Api("注册接口")
class ApiRegisterController @Autowired constructor(private val userService: UserService) {
    private val log = LoggerFactory.getLogger(ApiRegisterController::class.java)

    /**
     * 注册
     */
    @AuthIgnore
    @PostMapping("register")
    @ApiOperation(value = "注册")
    @ApiImplicitParams(ApiImplicitParam(paramType = "query", dataType = "string", name = "mobile", value = "手机号", required = true), ApiImplicitParam(paramType = "query", dataType = "string", name = "password", value = "密码", required = true))
    @ApiResponses(ApiResponse(code = 0, message = "注册成功"), ApiResponse(code = 500, message = "注册失败"))
    fun register(mobile: String, password: String): Result {
        Assert.isBlank(mobile, "手机号不能为空")
        Assert.isBlank(password, "密码不能为空")
        val userEntity = userService.queryByMobile(mobile)
        if (userEntity != null) {
            return Result().error(msg = "用户已注册")
        }
        try {
            userService.save(mobile, password)
        } catch (e: Exception) {
            log.error("注册异常", e)
            return Result().error(msg = "注册异常")
        }

        return Result().ok()
    }
}
