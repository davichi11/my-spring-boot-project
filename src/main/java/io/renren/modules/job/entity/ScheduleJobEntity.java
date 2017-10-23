package io.renren.modules.job.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 定时器
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年11月28日 下午12:54:44
 */
@Data
public class ScheduleJobEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 任务调度参数key
     */
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";

    /**
     * 任务id
     */
    private Long jobId;

    /**
     * spring bean名称
     */
    @NotBlank(message = "bean名称不能为空")
    private String beanName;

    /**
     * 方法名
     */
    @NotBlank(message = "方法名称不能为空")
    private String methodName;

    /**
     * 参数
     */
    private String params;

    /**
     * cron表达式
     */
    @NotBlank(message = "cron表达式不能为空")
    private String cronExpression;

    /**
     * 任务状态
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
