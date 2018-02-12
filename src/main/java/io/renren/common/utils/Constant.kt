package io.renren.common.utils

/**
 * 常量
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年11月15日 下午1:23:52
 */
object Constant {
    /**
     * 超级管理员ID
     */
    const val SUPER_ADMIN = 1

    /**
     * 菜单类型
     *
     * @author ChunLiang Hu
     * @email davichi2009@gmail.com
     * @date 2016年11月15日 下午1:24:29
     */
    enum class MenuType(val value: Int) {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2)
    }

    /**
     * 定时任务状态
     *
     * @author ChunLiang Hu
     * @email davichi2009@gmail.com
     * @date 2016年12月3日 上午12:07:22
     */
    enum class ScheduleStatus(val value: Int) {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1)
    }

    /**
     * 云服务商
     */
    enum class CloudService(val value: Int) {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3)
    }

}
