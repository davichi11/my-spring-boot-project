package io.renren.modules.job.entity

import org.hibernate.validator.constraints.NotBlank
import java.time.LocalDateTime

/**
 * 定时器
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年11月28日 下午12:54:44
 */
data class ScheduleJobEntity(
        val JOB_PARAM_KEY: String = "JOB_PARAM_KEY",
        /**
         * 任务id
         */
        var jobId: Long? = null,

        /**
         * spring bean名称
         */
        @NotBlank(message = "bean名称不能为空")
        var beanName: String? = null,

        /**
         * 方法名
         */
        @NotBlank(message = "方法名称不能为空")
        var methodName: String? = null,

        /**
         * 参数
         */
        var params: String? = null,

        /**
         * cron表达式
         */
        @NotBlank(message = "cron表达式不能为空")
        var cronExpression: String? = null,

        /**
         * 任务状态
         */
        var status: Int? = null,

        /**
         * 备注
         */
        var remark: String? = null,

        /**
         * 创建时间
         */
        var createTime: LocalDateTime? = null

)
