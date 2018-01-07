package io.renren.modules.sys.dao

import io.renren.modules.sys.entity.SysLogEntity
import org.apache.ibatis.annotations.Mapper

/**
 * 系统日志
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-08 10:40:56
 */
@Mapper
interface SysLogDao : BaseDao<SysLogEntity> {
    /**
     * 根据参数删除
     * @param param
     * @throws Exception
     */
    @Throws(Exception::class)
    fun deleteByParams(param: Map<String, Any>)
}
