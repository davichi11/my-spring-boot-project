package io.renren.modules.job.utils

import com.google.gson.Gson
import io.renren.common.utils.ApplicationContextHolder
import io.renren.modules.job.entity.ScheduleJobEntity
import io.renren.modules.job.entity.ScheduleJobLogEntity
import io.renren.modules.job.service.ScheduleJobLogService
import org.apache.commons.lang.StringUtils
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import org.slf4j.LoggerFactory
import org.springframework.scheduling.quartz.QuartzJobBean
import java.time.Instant
import java.time.LocalDateTime
import java.util.concurrent.Executors


/**
 * 定时任务
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年11月30日 下午12:44:21
 */
class ScheduleJob : QuartzJobBean() {
    private val log = LoggerFactory.getLogger(ScheduleJob::class.java)
    private val service = Executors.newSingleThreadExecutor()

    @Throws(JobExecutionException::class)
    override fun executeInternal(context: JobExecutionContext) {
        val jsonJob = context.mergedJobDataMap.getString(ScheduleJobEntity().JOB_PARAM_KEY)
        val scheduleJob = Gson().fromJson(jsonJob, ScheduleJobEntity::class.java)

        //获取spring bean
        val scheduleJobLogService = ApplicationContextHolder.getBean("scheduleJobLogService") as ScheduleJobLogService

        //数据库保存执行记录
        val jobLogEntity = ScheduleJobLogEntity()
        jobLogEntity.jobId = scheduleJob.jobId
        jobLogEntity.beanName = scheduleJob.beanName
        jobLogEntity.methodName = scheduleJob.methodName
        jobLogEntity.params = scheduleJob.params
        jobLogEntity.createTime = LocalDateTime.now()

        //任务开始时间
        val startTime = Instant.now().toEpochMilli()

        try {
            //执行任务
            log.info("任务准备执行，任务ID：" + scheduleJob.jobId!!)
            val task = ScheduleRunnable(scheduleJob.beanName!!,
                    scheduleJob.methodName!!, scheduleJob.params!!)
            val future = service.submit(task)

            future.get()

            //任务执行总时长
            val times = Instant.now().toEpochMilli() - startTime
            jobLogEntity.times = times.toInt()
            //任务状态    0：成功    1：失败
            jobLogEntity.status = 0

            log.info("任务执行完毕，任务ID：" + scheduleJob.jobId + "  总共耗时：" + times + "毫秒")
        } catch (e: Exception) {
            log.error("任务执行失败，任务ID：" + scheduleJob.jobId!!, e)

            //任务执行总时长
            val times = Instant.now().toEpochMilli() - startTime
            jobLogEntity.times = times.toInt()

            //任务状态    0：成功    1：失败
            jobLogEntity.status = 1
            jobLogEntity.error = StringUtils.substring(e.toString(), 0, 2000)
        } finally {
            try {
                scheduleJobLogService.save(jobLogEntity)
            } catch (e: Exception) {
                log.error("保存异常", e)
            }

        }
    }
}
