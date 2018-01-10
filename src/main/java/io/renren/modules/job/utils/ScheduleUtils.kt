package io.renren.modules.job.utils

import com.google.gson.Gson
import io.renren.common.exception.RRException
import io.renren.common.utils.Constant.ScheduleStatus
import io.renren.modules.job.entity.ScheduleJobEntity
import org.quartz.*

/**
 * 定时任务工具类
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年11月30日 下午12:44:59
 */
object ScheduleUtils {
    private val JOB_NAME = "TASK_"

    /**
     * 获取触发器key
     */
    private fun getTriggerKey(jobId: Long?): TriggerKey {
        return TriggerKey.triggerKey(JOB_NAME + jobId!!)
    }

    /**
     * 获取jobKey
     */
    private fun getJobKey(jobId: Long?): JobKey {
        return JobKey.jobKey(JOB_NAME + jobId!!)
    }

    /**
     * 获取表达式触发器
     */
    fun getCronTrigger(scheduler: Scheduler, jobId: Long?): CronTrigger? {
        try {
            return scheduler.getTrigger(getTriggerKey(jobId)) as? CronTrigger
        } catch (e: SchedulerException) {
            throw RRException("获取定时任务CronTrigger出现异常", e)
        }

    }

    /**
     * 创建定时任务
     */
    fun createScheduleJob(scheduler: Scheduler, scheduleJob: ScheduleJobEntity) {
        try {
            //构建job信息
            val jobDetail = JobBuilder.newJob(ScheduleJob::class.java).withIdentity(getJobKey(scheduleJob.jobId)).build()

            //表达式调度构建器
            val scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.cronExpression!!)
                    .withMisfireHandlingInstructionDoNothing()

            //按新的cronExpression表达式构建一个新的trigger
            val trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(scheduleJob.jobId))
                    .withSchedule(scheduleBuilder).build()

            //放入参数，运行时的方法可以获取
            jobDetail.jobDataMap.put(ScheduleJobEntity().JOB_PARAM_KEY, Gson().toJson(scheduleJob))

            scheduler.scheduleJob(jobDetail, trigger)

            //暂停任务
            if (scheduleJob.status == ScheduleStatus.PAUSE.value) {
                pauseJob(scheduler, scheduleJob.jobId)
            }
        } catch (e: Exception) {
            throw RRException("创建定时任务失败", e)
        }

    }

    /**
     * 更新定时任务
     */
    fun updateScheduleJob(scheduler: Scheduler, scheduleJob: ScheduleJobEntity) {
        try {
            val triggerKey = getTriggerKey(scheduleJob.jobId)

            //表达式调度构建器
            val scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.cronExpression!!)
                    .withMisfireHandlingInstructionDoNothing()

            var trigger: CronTrigger? = getCronTrigger(scheduler, scheduleJob.jobId) ?: return

            //按新的cronExpression表达式重新构建trigger
            trigger = trigger!!.triggerBuilder.withIdentity(triggerKey).withSchedule(scheduleBuilder).build()

            //参数
            trigger.jobDataMap.put(ScheduleJobEntity().JOB_PARAM_KEY, Gson().toJson(scheduleJob))

            scheduler.rescheduleJob(triggerKey, trigger)

            //暂停任务
            if (scheduleJob.status == ScheduleStatus.PAUSE.value) {
                pauseJob(scheduler, scheduleJob.jobId)
            }

        } catch (e: Exception) {
            throw RRException("更新定时任务失败", e)
        }

    }

    /**
     * 立即执行任务
     */
    @Throws(Exception::class)
    fun run(scheduler: Scheduler, scheduleJob: ScheduleJobEntity) {
        //参数
        val dataMap = JobDataMap()
        dataMap.put(ScheduleJobEntity().JOB_PARAM_KEY, Gson().toJson(scheduleJob))

        scheduler.triggerJob(getJobKey(scheduleJob.jobId), dataMap)
    }

    /**
     * 暂停任务
     */
    @Throws(Exception::class)
    fun pauseJob(scheduler: Scheduler, jobId: Long?) {
        scheduler.pauseJob(getJobKey(jobId))
    }

    /**
     * 恢复任务
     */
    @Throws(SchedulerException::class)
    fun resumeJob(scheduler: Scheduler, jobId: Long?) {
        scheduler.resumeJob(getJobKey(jobId))
    }

    /**
     * 删除定时任务
     */
    @Throws(SchedulerException::class)
    fun deleteScheduleJob(scheduler: Scheduler, jobId: Long?) {
        scheduler.deleteJob(getJobKey(jobId))
    }
}
