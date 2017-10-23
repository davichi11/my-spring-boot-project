package io.renren.modules.sys.service;

import io.renren.modules.sys.entity.SysConfigEntity;

import java.util.List;
import java.util.Map;

/**
 * 系统配置信息
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年12月4日 下午6:49:01
 */
public interface SysConfigService {

    /**
     * 保存配置信息
     *
     * @param config
     * @throws Exception
     */
    void save(SysConfigEntity config) throws Exception;

    /**
     * 更新配置信息
     *
     * @param config
     * @throws Exception
     */
    void update(SysConfigEntity config) throws Exception;

    /**
     * 根据key，更新value
     *
     * @param key
     * @param value
     * @throws Exception
     */
    void updateValueByKey(String key, String value) throws Exception;

    /**
     * 删除配置信息
     *
     * @param ids
     * @throws Exception
     */
    void deleteBatch(Long[] ids) throws Exception;

    /**
     * 获取List列表
     *
     * @param map
     * @return 配置集合
     */
    List<SysConfigEntity> queryList(Map<String, Object> map);

    /**
     * 获取总记录数
     *
     * @param map
     * @return 总记录数
     */
    int queryTotal(Map<String, Object> map);

    /**
     * 根据ID查询配置
     *
     * @param id
     * @return
     */
    SysConfigEntity queryObject(Long id);

    /**
     * 根据key，获取配置的value值
     *
     * @param key          key
     * @param defaultValue 缺省值
     * @return
     */
    String getValue(String key, String defaultValue);

    /**
     * * 根据key，获取value的Object对象
     *
     * @param key   key
     * @param clazz Object对象
     * @param <T>
     * @return
     */
    <T> T getConfigObject(String key, Class<T> clazz);

}
