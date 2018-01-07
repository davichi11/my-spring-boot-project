package io.renren.modules.job.service

import io.renren.modules.job.entity.ScheduleJobLogEntity

/**
 * 定时任务日志
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年12月1日 下午10:34:48
 */
interface ScheduleJobLogService {

    /**
     * 根据ID，查询定时任务日志
     *
     * @param jobId
     * @return
     */
    fun queryObject(jobId: Long?): ScheduleJobLogEntity

    /**
     * 查询定时任务日志列表
     *
     * @param map
     * @return
     */
    fun queryList(map: Map<String, Any>): List<ScheduleJobLogEntity>

    /**
     * 查询总数
     *
     * @param map
     * @return
     */
    fun queryTotal(map: Map<String, Any>): Int

    /**
     * 保存定时任务日志
     *
     * @param log
     * @throws Exception
     */
    @Throws(Exception::class)
    fun save(log: ScheduleJobLogEntity)

}
