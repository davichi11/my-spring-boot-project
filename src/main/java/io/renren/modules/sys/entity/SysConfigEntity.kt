package io.renren.modules.sys.entity


/**
 * 系统配置信息
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年12月4日 下午6:43:36
 */
data class SysConfigEntity(
        var id: Long = 0,
        var key: String = "",
        var value: String = "",
        var remark: String = ""
)

