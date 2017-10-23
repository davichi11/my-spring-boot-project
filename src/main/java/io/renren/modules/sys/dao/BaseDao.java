package io.renren.modules.sys.dao;

import java.util.List;
import java.util.Map;

/**
 * 基础Dao(还需在XML文件里，有对应的SQL语句)
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年9月18日 上午9:31:36
 */
public interface BaseDao<T> {

    /**
     * 保存一个对象
     *
     * @param t
     * @throws Exception
     */
    void save(T t) throws Exception;

    /**
     * 根据参数保存
     *
     * @param map
     * @throws Exception
     */
    void save(Map<String, Object> map) throws Exception;

    /**
     * 批量保存
     *
     * @param list
     * @throws Exception
     */
    void saveBatch(List<T> list) throws Exception;

    /**
     * 更新一个对象
     *
     * @param t
     * @return
     * @throws Exception
     */
    int update(T t) throws Exception;

    /**
     * 更加参数更新
     *
     * @param map
     * @return
     * @throws Exception
     */
    int update(Map<String, Object> map) throws Exception;

    /**
     * 删除一个对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    int delete(Object id) throws Exception;

    /**
     * 根据参数删除
     *
     * @param map
     * @return
     * @throws Exception
     */
    int delete(Map<String, Object> map) throws Exception;


    /**
     * 批量删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    int deleteBatch(Object[] id) throws Exception;

    /**
     * 根据ID查询对象
     *
     * @param id
     * @return
     */
    T queryObject(Object id);

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    List<T> queryList(Map<String, Object> map);

    /**
     * 查询总记录数
     *
     * @param map
     * @return
     */
    int queryTotal(Map<String, Object> map);

}
