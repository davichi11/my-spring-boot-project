package io.renren.modules.job.utils;

import com.google.gson.Gson;
import io.renren.common.utils.SpringContextUtils;
import io.renren.modules.job.entity.ScheduleJobEntity;
import io.renren.modules.job.entity.ScheduleJobLogEntity;
import io.renren.modules.job.service.ScheduleJobLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * 定时任务
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年11月30日 下午12:44:21
 */
@Slf4j
public class ScheduleJob extends QuartzJobBean {
    private ExecutorService service = Executors.newSingleThreadExecutor();

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        String jsonJob = context.getMergedJobDataMap().getString(ScheduleJobEntity.JOB_PARAM_KEY);
        ScheduleJobEntity scheduleJob = new Gson().fromJson(jsonJob, ScheduleJobEntity.class);

        //获取spring bean
        ScheduleJobLogService scheduleJobLogService = (ScheduleJobLogService) SpringContextUtils.getBean("scheduleJobLogService");

        //数据库保存执行记录
        ScheduleJobLogEntity jobLogEntity = new ScheduleJobLogEntity();
        jobLogEntity.setJobId(scheduleJob.getJobId());
        jobLogEntity.setBeanName(scheduleJob.getBeanName());
        jobLogEntity.setMethodName(scheduleJob.getMethodName());
        jobLogEntity.setParams(scheduleJob.getParams());
        jobLogEntity.setCreateTime(LocalDateTime.now());

        //任务开始时间
        long startTime = System.currentTimeMillis();

        try {
            //执行任务
            log.info("任务准备执行，任务ID：" + scheduleJob.getJobId());
            ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanName(),
                    scheduleJob.getMethodName(), scheduleJob.getParams());
            Future<?> future = service.submit(task);

            future.get();

            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            jobLogEntity.setTimes((int) times);
            //任务状态    0：成功    1：失败
            jobLogEntity.setStatus(0);

            log.info("任务执行完毕，任务ID：" + scheduleJob.getJobId() + "  总共耗时：" + times + "毫秒");
        } catch (Exception e) {
            log.error("任务执行失败，任务ID：" + scheduleJob.getJobId(), e);

            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            jobLogEntity.setTimes((int) times);

            //任务状态    0：成功    1：失败
            jobLogEntity.setStatus(1);
            jobLogEntity.setError(StringUtils.substring(e.toString(), 0, 2000));
        } finally {
            try {
                scheduleJobLogService.save(jobLogEntity);
            } catch (Exception e) {
                log.error("保存异常", e);
            }
        }
    }
}
