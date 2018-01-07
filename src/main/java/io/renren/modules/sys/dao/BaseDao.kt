package io.renren.modules.sys.dao

/**
 * 基础Dao(还需在XML文件里，有对应的SQL语句)
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年9月18日 上午9:31:36
 */
interface BaseDao<T> {

    /**
     * 保存一个对象
     *
     * @param t
     * @throws Exception
     */
    @Throws(Exception::class)
    fun save(t: T)

    /**
     * 根据参数保存
     *
     * @param map
     * @throws Exception
     */
    @Throws(Exception::class)
    fun save(map: Map<String, Any>)

    /**
     * 批量保存
     *
     * @param list
     * @throws Exception
     */
    @Throws(Exception::class)
    fun saveBatch(list: List<T>)

    /**
     * 更新一个对象
     *
     * @param t
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun update(t: T): Int

    /**
     * 更加参数更新
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun update(map: Map<String, Any>): Int

    /**
     * 删除一个对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun delete(id: Any): Int

    /**
     * 根据参数删除
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun delete(map: Map<String, Any>): Int


    /**
     * 批量删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun deleteBatch(id: Array<out Any>): Int

    /**
     * 根据ID查询对象
     *
     * @param id
     * @return
     */
    fun queryObject(id: Any): T

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    fun queryList(map: Map<String, Any>): List<T>

    /**
     * 查询总记录数
     *
     * @param map
     * @return
     */
    fun queryTotal(map: Map<String, Any>): Int

}
