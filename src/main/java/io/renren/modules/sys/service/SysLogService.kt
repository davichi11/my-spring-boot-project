package io.renren.modules.sys.service

import io.renren.modules.sys.entity.SysLogEntity

/**
 * 系统日志
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-08 10:40:56
 */
interface SysLogService {

    /**
     * 根据ID查询日志
     *
     * @param id
     * @return
     */
    fun queryObject(id: Long): SysLogEntity

    /**
     * 日志列表
     *
     * @param map
     * @return
     */
    fun queryList(map: Map<String, Any>): List<SysLogEntity>

    /**
     * 日志记录数
     *
     * @param map
     * @return
     */
    fun queryTotal(map: Map<String, Any>): Int

    /**
     * 保存
     *
     * @param sysLog
     * @throws Exception
     */
    @Throws(Exception::class)
    fun save(sysLog: SysLogEntity)

    /**
     * 更新
     *
     * @param sysLog
     * @throws Exception
     */
    @Throws(Exception::class)
    fun update(sysLog: SysLogEntity)

    /**
     * 删除
     *
     * @param id
     * @throws Exception
     */
    @Throws(Exception::class)
    fun delete(id: Long)

    /**
     * 批量删除
     *
     * @param ids
     * @throws Exception
     */
    @Throws(Exception::class)
    fun deleteBatch(ids: Array<Long>)

    /**
     * 根据传入的参数删除
     * @param param 传入删除条件参数 id 开始时间 结束时间
     * @throws Exception
     */
    @Throws(Exception::class)
    fun deleteByParams(param: Map<String, Any>)

    /**
     * 保存异常的日志信息
     * @param params
     */
    fun saveErrorLog(params: String)
}
