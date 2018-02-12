package io.renren.modules.job.service.impl

import io.renren.common.exception.RRException
import io.renren.common.utils.Constant.ScheduleStatus
import io.renren.modules.job.dao.ScheduleJobDao
import io.renren.modules.job.entity.ScheduleJobEntity
import io.renren.modules.job.service.ScheduleJobService
import io.renren.modules.job.utils.ScheduleUtils
import org.quartz.Scheduler
import org.quartz.SchedulerException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*
import javax.annotation.PostConstruct

/**
 * @author huchunliang
 */
@Service("scheduleJobService")
class ScheduleJobServiceImpl @Autowired constructor(private val scheduler: Scheduler, private val schedulerJobDao: ScheduleJobDao) : ScheduleJobService {

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    fun init() {
        val scheduleJobList = schedulerJobDao.queryList(mapOf())
        //如果不存在，则创建
        scheduleJobList.forEach { scheduleJob ->
            val cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.jobId)
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob)
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob)
            }
        }
    }

    override fun queryObject(jobId: Long): ScheduleJobEntity {
        return schedulerJobDao.queryObject(jobId)
    }

    override fun queryList(map: Map<String, Any>): List<ScheduleJobEntity> {
        return schedulerJobDao.queryList(map)
    }

    override fun queryTotal(map: Map<String, Any>): Int {
        return schedulerJobDao.queryTotal(map)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun save(scheduleJob: ScheduleJobEntity) {
        scheduleJob.createTime = LocalDateTime.now()
        scheduleJob.status = ScheduleStatus.NORMAL.value
        schedulerJobDao.save(scheduleJob)

        ScheduleUtils.createScheduleJob(scheduler, scheduleJob)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun update(scheduleJob: ScheduleJobEntity) {
        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob)

        schedulerJobDao.update(scheduleJob)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun deleteBatch(jobIds: Array<Long>) {
        jobIds.forEach {
            try {
                ScheduleUtils.deleteScheduleJob(scheduler, it)
            } catch (e: SchedulerException) {
                throw RRException("删除定时任务失败")
            }
        }

        //删除数据
        schedulerJobDao.deleteBatch(jobIds)
    }

    override fun updateBatch(jobIds: Array<Long>, status: Int): Int {
        val map = HashMap<String, Any>(16)
        map["list"] = jobIds
        map["status"] = status
        return schedulerJobDao.updateBatch(map)
    }

    override fun run(jobIds: Array<Long>) {
        jobIds.forEach {
            try {
                ScheduleUtils.run(scheduler, queryObject(it))
            } catch (e: Exception) {
                throw RRException("立即执行任务失败")
            }
        }
    }

    override fun pause(jobIds: Array<Long>) {
        jobIds.forEach {
            try {
                ScheduleUtils.pauseJob(scheduler, it)
            } catch (e: Exception) {
                throw RRException("暂停定时任务失败")
            }
        }

        updateBatch(jobIds, ScheduleStatus.PAUSE.value)
    }

    override fun resume(jobIds: Array<Long>) {
        jobIds.forEach {
            try {
                ScheduleUtils.resumeJob(scheduler, it)
            } catch (e: SchedulerException) {
                throw RRException("恢复定时任务失败")
            }
        }

        updateBatch(jobIds, ScheduleStatus.NORMAL.value)
    }

}
