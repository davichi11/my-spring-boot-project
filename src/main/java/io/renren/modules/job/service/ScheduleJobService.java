package io.renren.modules.job.service;

import io.renren.modules.job.entity.ScheduleJobEntity;

import java.util.List;
import java.util.Map;

/**
 * 定时任务
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年11月28日 上午9:55:32
 */
public interface ScheduleJobService {

    /**
     * 根据ID，查询定时任务
     *
     * @param jobId
     * @return
     */
    ScheduleJobEntity queryObject(Long jobId);

    /**
     * 查询定时任务列表
     *
     * @param map
     * @return
     */
    List<ScheduleJobEntity> queryList(Map<String, Object> map);

    /**
     * 查询总数
     *
     * @param map
     * @return
     */
    int queryTotal(Map<String, Object> map);

    /**
     * 保存定时任务
     *
     * @param scheduleJob
     * @throws Exception
     */
    void save(ScheduleJobEntity scheduleJob) throws Exception;

    /**
     * 更新定时任务
     *
     * @param scheduleJob
     * @throws Exception
     */
    void update(ScheduleJobEntity scheduleJob) throws Exception;

    /**
     * 批量删除定时任务
     *
     * @param jobIds
     * @throws Exception
     */
    void deleteBatch(Long[] jobIds) throws Exception;

    /**
     * 批量更新定时任务状态
     *
     * @param jobIds
     * @param status
     * @throws Exception
     */
    int updateBatch(Long[] jobIds, int status) throws Exception;

    /**
     * 立即执行
     *
     * @param jobIds
     */
    void run(Long[] jobIds);

    /**
     * 暂停运行
     *
     * @param jobIds
     */
    void pause(Long[] jobIds);

    /**
     * 恢复运行
     *
     * @param jobIds
     */
    void resume(Long[] jobIds);
}
