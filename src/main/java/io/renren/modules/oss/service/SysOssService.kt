package io.renren.modules.oss.service

import io.renren.modules.oss.entity.SysOssEntity

/**
 * 文件上传
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-25 12:13:26
 */
interface SysOssService {
    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    fun queryObject(id: Long?): SysOssEntity

    /**
     * 列表查询
     *
     * @param map
     * @return
     */
    fun queryList(map: Map<String, Any>): List<SysOssEntity>

    /**
     * 查询总记录数
     *
     * @param map
     * @return
     */
    fun queryTotal(map: Map<String, Any>): Int

    /**
     * 保存
     *
     * @param sysOss
     * @throws Exception
     */
    @Throws(Exception::class)
    fun save(sysOss: SysOssEntity)

    /**
     * 更新
     *
     * @param sysOss
     * @throws Exception
     */
    @Throws(Exception::class)
    fun update(sysOss: SysOssEntity)

    /**
     * 删除
     *
     * @param id
     * @throws Exception
     */
    @Throws(Exception::class)
    fun delete(id: Long?)

    /**
     * 批量删除
     *
     * @param ids
     * @throws Exception
     */
    @Throws(Exception::class)
    fun deleteBatch(ids: Array<Long>)
}
