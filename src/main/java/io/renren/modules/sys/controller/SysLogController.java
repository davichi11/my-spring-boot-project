package io.renren.modules.sys.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.renren.common.utils.Result;
import io.renren.modules.sys.entity.SysLogEntity;
import io.renren.modules.sys.service.SysLogService;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


/**
 * 系统日志
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-08 10:40:56
 */
@Controller
@RequestMapping("/sys/log")
public class SysLogController {
    @Autowired
    private SysLogService sysLogService;

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions("sys:log:list")
    public Result list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        PageInfo<SysLogEntity> pageInfo = PageHelper.startPage(MapUtils.getInteger(params, "page"),
                MapUtils.getInteger(params, "limit")).doSelectPageInfo(() -> sysLogService.queryList(params));
        return Result.ok().put("page", pageInfo);
    }

}
