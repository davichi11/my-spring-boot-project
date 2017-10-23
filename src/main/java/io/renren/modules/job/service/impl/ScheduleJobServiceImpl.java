package io.renren.modules.job.service.impl;

import io.renren.common.exception.RRException;
import io.renren.common.utils.Constant.ScheduleStatus;
import io.renren.modules.job.dao.ScheduleJobDao;
import io.renren.modules.job.entity.ScheduleJobEntity;
import io.renren.modules.job.service.ScheduleJobService;
import io.renren.modules.job.utils.ScheduleUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huchunliang
 */
@Service("scheduleJobService")
public class ScheduleJobServiceImpl implements ScheduleJobService {
    @Autowired
    private Scheduler scheduler;
    @Autowired
    private ScheduleJobDao schedulerJobDao;

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init() {
        List<ScheduleJobEntity> scheduleJobList = schedulerJobDao.queryList(new HashMap<>(16));
        //如果不存在，则创建
        scheduleJobList.forEach(scheduleJob -> {
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        });
    }

    @Override
    public ScheduleJobEntity queryObject(Long jobId) {
        return schedulerJobDao.queryObject(jobId);
    }

    @Override
    public List<ScheduleJobEntity> queryList(Map<String, Object> map) {
        return schedulerJobDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return schedulerJobDao.queryTotal(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(ScheduleJobEntity scheduleJob) throws Exception {
        scheduleJob.setCreateTime(LocalDateTime.now());
        scheduleJob.setStatus(ScheduleStatus.NORMAL.getValue());
        schedulerJobDao.save(scheduleJob);

        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ScheduleJobEntity scheduleJob) throws Exception {
        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);

        schedulerJobDao.update(scheduleJob);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] jobIds) throws Exception {
        Arrays.stream(jobIds).forEachOrdered(jobId -> {
            try {
                ScheduleUtils.deleteScheduleJob(scheduler, jobId);
            } catch (SchedulerException e) {
                throw new RRException("删除定时任务失败");
            }
        });

        //删除数据
        schedulerJobDao.deleteBatch(jobIds);
    }

    @Override
    public int updateBatch(Long[] jobIds, int status) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("list", jobIds);
        map.put("status", status);
        return schedulerJobDao.updateBatch(map);
    }

    @Override
    public void run(Long[] jobIds) {
        Arrays.stream(jobIds).forEach(jobId -> {
            try {
                ScheduleUtils.run(scheduler, queryObject(jobId));
            } catch (Exception e) {
                throw new RRException("立即执行任务失败");
            }
        });
    }

    @Override
    public void pause(Long[] jobIds) {
        Arrays.stream(jobIds).forEach(jobId -> {
            try {
                ScheduleUtils.pauseJob(scheduler, jobId);
            } catch (Exception e) {
                throw new RRException("暂停定时任务失败");
            }
        });

        updateBatch(jobIds, ScheduleStatus.PAUSE.getValue());
    }

    @Override
    public void resume(Long[] jobIds) {
        Arrays.stream(jobIds).forEach(jobId -> {
            try {
                ScheduleUtils.resumeJob(scheduler, jobId);
            } catch (SchedulerException e) {
                throw new RRException("恢复定时任务失败");
            }
        });

        updateBatch(jobIds, ScheduleStatus.NORMAL.getValue());
    }

}
