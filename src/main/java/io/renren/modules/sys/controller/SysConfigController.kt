package io.renren.modules.sys.controller

import com.github.pagehelper.PageHelper
import io.renren.common.annotation.SysLog
import io.renren.common.utils.Result
import io.renren.common.validator.ValidatorUtils
import io.renren.modules.sys.entity.SysConfigEntity
import io.renren.modules.sys.service.SysConfigService
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * 系统配置信息
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年12月4日 下午6:55:53
 */
@RestController
@RequestMapping("/sys/config")
class SysConfigController @Autowired constructor(private val sysConfigService: SysConfigService) {
    private val logger = LoggerFactory.getLogger(SysConfigController::class.java)



    /**
     * 所有配置列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:config:list")
    fun list(@RequestParam params: Map<String, Any>): Result {
        //查询列表数据
        val pageInfo = PageHelper.startPage<Any>((params["page"] as String).toInt(), (params["limit"] as String).toInt())
                .doSelectPageInfo<SysConfigEntity> { sysConfigService.queryList(params) }
        return Result().ok().put("page", pageInfo)
    }


    /**
     * 配置信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:config:info")
    fun info(@PathVariable("id") id: Long?): Result {
        val config = sysConfigService.queryObject(id)

        return Result().ok().put("config", config)
    }

    /**
     * 保存配置
     */
    @SysLog("保存配置")
    @RequestMapping("/save")
    @RequiresPermissions("sys:config:save")
    fun save(@RequestBody config: SysConfigEntity): Result {
        ValidatorUtils.validateEntity(config)

        try {
            sysConfigService.save(config)
        } catch (e: Exception) {
            logger.error("保存配置异常", e)
            return Result().error(msg = "保存配置异常")
        }

        return Result().ok()
    }

    /**
     * 修改配置
     */
    @SysLog("修改配置")
    @RequestMapping("/update")
    @RequiresPermissions("sys:config:update")
    fun update(@RequestBody config: SysConfigEntity): Result {
        ValidatorUtils.validateEntity(config)

        try {
            sysConfigService.update(config)
        } catch (e: Exception) {
            logger.error("修改配置异常", e)
            return Result().error(msg = "修改配置异常")
        }

        return Result().ok()
    }

    /**
     * 删除配置
     */
    @SysLog("删除配置")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:config:delete")
    fun delete(@RequestBody ids: Array<Long>): Result {
        try {
            sysConfigService.deleteBatch(ids)
        } catch (e: Exception) {
            logger.error("删除配置异常", e)
            return Result().error(msg = "删除配置异常")
        }

        return Result().ok()
    }

}
