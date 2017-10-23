package io.renren.modules.job.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 定时执行日志
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年12月1日 下午10:26:18
 */
@Data
public class ScheduleJobLogEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 日志id
     */
    private Long logId;

    /**
     * 任务id
     */
    private Long jobId;

    /**
     * spring bean名称
     */
    private String beanName;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 参数
     */
    private String params;

    /**
     * 任务状态    0：成功    1：失败
     */
    private Integer status;

    /**
     * 失败信息
     */
    private String error;

    /**
     * 耗时(单位：毫秒)
     */
    private Integer times;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
