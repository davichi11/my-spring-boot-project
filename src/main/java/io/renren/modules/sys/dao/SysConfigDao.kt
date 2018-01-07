package io.renren.modules.sys.dao

import io.renren.modules.sys.entity.SysConfigEntity
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

/**
 * 系统配置信息
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年12月4日 下午6:46:16
 */
@Mapper
interface SysConfigDao : BaseDao<SysConfigEntity> {

    /**
     * 根据key，查询value
     */
    fun queryByKey(paramKey: String): String

    /**
     * 根据key，更新value
     */
    fun updateValueByKey(@Param("key") key: String, @Param("value") value: String): Int

}
