package io.renren.modules.sys.controller

import com.github.pagehelper.PageHelper
import io.renren.common.utils.Result
import io.renren.modules.sys.entity.SysLogEntity
import io.renren.modules.sys.service.SysLogService
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody


/**
 * 系统日志
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-08 10:40:56
 */
@Controller
@RequestMapping("/sys/log")
class SysLogController @Autowired constructor(private val sysLogService: SysLogService) {

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions("sys:log:list")
    fun list(@RequestParam params: Map<String, Any>): Result {
        //查询列表数据
        val pageInfo = PageHelper.startPage<Any>((params["page"] as String).toInt(), (params["limit"] as String).toInt())
                .doSelectPageInfo<SysLogEntity> { sysLogService.queryList(params) }
        return Result().ok().put("page", pageInfo)
    }

}
