package io.renren.modules.sys.service

import io.renren.modules.sys.entity.SysConfigEntity

/**
 * 系统配置信息
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年12月4日 下午6:49:01
 */
interface SysConfigService {

    /**
     * 保存配置信息
     *
     * @param config
     * @throws Exception
     */
    @Throws(Exception::class)
    fun save(config: SysConfigEntity)

    /**
     * 更新配置信息
     *
     * @param config
     * @throws Exception
     */
    @Throws(Exception::class)
    fun update(config: SysConfigEntity)

    /**
     * 根据key，更新value
     *
     * @param key
     * @param value
     * @throws Exception
     */
    @Throws(Exception::class)
    fun updateValueByKey(key: String, value: String)

    /**
     * 删除配置信息
     *
     * @param ids
     * @throws Exception
     */
    @Throws(Exception::class)
    fun deleteBatch(ids: Array<Long>)

    /**
     * 获取List列表
     *
     * @param map
     * @return 配置集合
     */
    fun queryList(map: Map<String, Any>): List<SysConfigEntity>

    /**
     * 获取总记录数
     *
     * @param map
     * @return 总记录数
     */
    fun queryTotal(map: Map<String, Any>): Int

    /**
     * 根据ID查询配置
     *
     * @param id
     * @return
     */
    fun queryObject(id: Long?): SysConfigEntity

    /**
     * 根据key，获取配置的value值
     *
     * @param key          key
     * @param defaultValue 缺省值
     * @return
     */
    fun getValue(key: String, defaultValue: String): String

    /**
     * * 根据key，获取value的Object对象
     *
     * @param key   key
     * @param clazz Object对象
     * @param <T>
     * @return
    </T> */
    fun <T> getConfigObject(key: String, clazz: Class<T>): T

}
