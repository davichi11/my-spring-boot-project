package io.renren.modules.oss.service;

import io.renren.modules.oss.entity.SysOssEntity;

import java.util.List;
import java.util.Map;

/**
 * 文件上传
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-25 12:13:26
 */
public interface SysOssService {
    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    SysOssEntity queryObject(Long id);

    /**
     * 列表查询
     *
     * @param map
     * @return
     */
    List<SysOssEntity> queryList(Map<String, Object> map);

    /**
     * 查询总记录数
     *
     * @param map
     * @return
     */
    int queryTotal(Map<String, Object> map);

    /**
     * 保存
     *
     * @param sysOss
     * @throws Exception
     */
    void save(SysOssEntity sysOss) throws Exception;

    /**
     * 更新
     *
     * @param sysOss
     * @throws Exception
     */
    void update(SysOssEntity sysOss) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @throws Exception
     */
    void delete(Long id) throws Exception;

    /**
     * 批量删除
     *
     * @param ids
     * @throws Exception
     */
    void deleteBatch(Long[] ids) throws Exception;
}
