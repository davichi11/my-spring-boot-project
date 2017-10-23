package io.renren.modules.job.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.renren.common.utils.Result;
import io.renren.modules.job.entity.ScheduleJobLogEntity;
import io.renren.modules.job.service.ScheduleJobLogService;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 定时任务日志
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年12月1日 下午10:39:52
 */
@RestController
@RequestMapping("/sys/scheduleLog")
public class ScheduleJobLogController {
    @Autowired
    private ScheduleJobLogService scheduleJobLogService;

    /**
     * 定时任务日志列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:schedule:log")
    public Result list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        PageInfo<ScheduleJobLogEntity> pageInfo = PageHelper.startPage(MapUtils.getInteger(params, "page"),
                MapUtils.getInteger(params, "limit")).doSelectPageInfo(() -> scheduleJobLogService.queryList(params));
        return Result.ok().put("page", pageInfo);
    }

    /**
     * 定时任务日志信息
     */
    @RequestMapping("/info/{logId}")
    public Result info(@PathVariable("logId") Long logId) {
        ScheduleJobLogEntity log = scheduleJobLogService.queryObject(logId);

        return Result.ok().put("log", log);
    }
}
