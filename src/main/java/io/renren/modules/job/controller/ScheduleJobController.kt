package io.renren.modules.job.controller

import com.github.pagehelper.PageHelper
import io.renren.common.annotation.SysLog
import io.renren.common.utils.Result
import io.renren.common.validator.ValidatorUtils
import io.renren.modules.job.entity.ScheduleJobEntity
import io.renren.modules.job.service.ScheduleJobService
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

/**
 * 定时任务
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年11月28日 下午2:16:40
 */
@RestController
@RequestMapping("/sys/schedule")
class ScheduleJobController @Autowired constructor(private val scheduleJobService: ScheduleJobService) {
    private val log = LoggerFactory.getLogger(ScheduleJobController::class.java)

    /**
     * 定时任务列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:schedule:list")
    fun list(@RequestParam params: Map<String, Any>): Result {
        //查询列表数据
        val pageInfo = PageHelper.startPage<Any>((params["page"] as String).toInt(), (params["limit"] as String).toInt())
                .doSelectPageInfo<ScheduleJobEntity> { scheduleJobService.queryList(params) }
        return Result().ok().put("page", pageInfo)
    }

    /**
     * 定时任务信息
     */
    @RequestMapping("/info/{jobId}")
    @RequiresPermissions("sys:schedule:info")
    fun info(@PathVariable("jobId") jobId: Long?): Result {
        val schedule = scheduleJobService.queryObject(jobId)

        return Result().ok().put("schedule", schedule)
    }

    /**
     * 保存定时任务
     */
    @SysLog("保存定时任务")
    @RequestMapping("/save")
    @RequiresPermissions("sys:schedule:save")
    fun save(@RequestBody scheduleJob: ScheduleJobEntity): Result {
        ValidatorUtils.validateEntity(scheduleJob)
        scheduleJob.createTime = LocalDateTime.now()
        try {
            scheduleJobService.save(scheduleJob)
        } catch (e: Exception) {
            log.error("更新定时任务异常", e)
            return Result().error(msg = "更新定时任务异常")
        }

        return Result().ok()
    }

    /**
     * 修改定时任务
     */
    @SysLog("修改定时任务")
    @RequestMapping("/update")
    @RequiresPermissions("sys:schedule:update")
    fun update(@RequestBody scheduleJob: ScheduleJobEntity): Result {
        ValidatorUtils.validateEntity(scheduleJob)

        try {
            scheduleJobService.update(scheduleJob)
        } catch (e: Exception) {
            log.error("更新定时任务异常", e)
            return Result().error(msg = "更新定时任务异常")
        }

        return Result().ok()
    }

    /**
     * 删除定时任务
     */
    @SysLog("删除定时任务")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:schedule:delete")
    fun delete(@RequestBody jobIds: Array<Long>): Result {
        try {
            scheduleJobService.deleteBatch(jobIds)
        } catch (e: Exception) {
            log.error("删除定时任务异常", e)
            return Result().error(msg = "删除定时任务异常")
        }

        return Result().ok()
    }

    /**
     * 立即执行任务
     */
    @SysLog("立即执行任务")
    @RequestMapping("/run")
    @RequiresPermissions("sys:schedule:run")
    fun run(@RequestBody jobIds: Array<Long>): Result {
        try {
            scheduleJobService.run(jobIds)
        } catch (e: Exception) {
            log.error("执行任务异常", e)
            return Result().error(msg = "执行任务异常")
        }

        return Result().ok()
    }

    /**
     * 暂停定时任务
     */
    @SysLog("暂停定时任务")
    @RequestMapping("/pause")
    @RequiresPermissions("sys:schedule:pause")
    fun pause(@RequestBody jobIds: Array<Long>): Result {
        try {
            scheduleJobService.pause(jobIds)
        } catch (e: Exception) {
            log.error("暂停任务异常", e)
            Result().error(msg = "暂停任务异常")
        }

        return Result().ok()
    }

    /**
     * 恢复定时任务
     */
    @SysLog("恢复定时任务")
    @RequestMapping("/resume")
    @RequiresPermissions("sys:schedule:resume")
    fun resume(@RequestBody jobIds: Array<Long>): Result {
        try {
            scheduleJobService.resume(jobIds)
        } catch (e: Exception) {
            log.error("恢复定时任务异常", e)
            return Result().error(msg = "恢复定时任务异常")
        }

        return Result().ok()
    }

}
