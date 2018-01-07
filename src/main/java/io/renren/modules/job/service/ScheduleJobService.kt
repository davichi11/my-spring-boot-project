package io.renren.modules.job.service

import io.renren.modules.job.entity.ScheduleJobEntity

/**
 * 定时任务
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年11月28日 上午9:55:32
 */
interface ScheduleJobService {

    /**
     * 根据ID，查询定时任务
     *
     * @param jobId
     * @return
     */
    fun queryObject(jobId: Long?): ScheduleJobEntity

    /**
     * 查询定时任务列表
     *
     * @param map
     * @return
     */
    fun queryList(map: Map<String, Any>): List<ScheduleJobEntity>

    /**
     * 查询总数
     *
     * @param map
     * @return
     */
    fun queryTotal(map: Map<String, Any>): Int

    /**
     * 保存定时任务
     *
     * @param scheduleJob
     * @throws Exception
     */
    @Throws(Exception::class)
    fun save(scheduleJob: ScheduleJobEntity)

    /**
     * 更新定时任务
     *
     * @param scheduleJob
     * @throws Exception
     */
    @Throws(Exception::class)
    fun update(scheduleJob: ScheduleJobEntity)

    /**
     * 批量删除定时任务
     *
     * @param jobIds
     * @throws Exception
     */
    @Throws(Exception::class)
    fun deleteBatch(jobIds: Array<Long>)

    /**
     * 批量更新定时任务状态
     *
     * @param jobIds
     * @param status
     * @throws Exception
     */
    @Throws(Exception::class)
    fun updateBatch(jobIds: Array<Long>, status: Int): Int

    /**
     * 立即执行
     *
     * @param jobIds
     */
    @Throws(Exception::class)
    fun run(jobIds: Array<Long>)

    /**
     * 暂停运行
     *
     * @param jobIds
     */
    @Throws(Exception::class)
    fun pause(jobIds: Array<Long>)

    /**
     * 恢复运行
     *
     * @param jobIds
     */
    @Throws(Exception::class)
    fun resume(jobIds: Array<Long>)
}
