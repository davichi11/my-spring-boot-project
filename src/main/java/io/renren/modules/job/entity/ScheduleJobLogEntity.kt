package io.renren.modules.job.entity

import java.io.Serializable
import java.time.LocalDateTime

/**
 * 定时执行日志
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年12月1日 下午10:26:18
 */
data class ScheduleJobLogEntity(

        private val serialVersionUID: Long = 1L,
        /**
         * 日志id
         */
        var logId: Long? = null,


        /**
         * 任务id
         */
        var jobId: Long? = null,


        /**
         * spring bean名称
         */
        var beanName: String? = null,


        /**
         * 方法名
         */
        var methodName: String? = null,


        /**
         * 参数
         */
        var params: String? = null,


        /**
         * 任务状态    0：成功    1：失败
         */
        var status: Int? = null
        ,

        /**
         * 失败信息
         */
        var error: String? = null,


        /**
         * 耗时(单位：毫秒)
         */
        var times: Int? = null,

        /**
         * 创建时间
         */
        var createTime: LocalDateTime? = null

) : Serializable
