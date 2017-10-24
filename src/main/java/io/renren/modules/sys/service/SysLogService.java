package io.renren.modules.sys.service;

import io.renren.modules.sys.entity.SysLogEntity;

import java.util.List;
import java.util.Map;

/**
 * 系统日志
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-08 10:40:56
 */
public interface SysLogService {

    /**
     * 根据ID查询日志
     *
     * @param id
     * @return
     */
    SysLogEntity queryObject(Long id);

    /**
     * 日志列表
     *
     * @param map
     * @return
     */
    List<SysLogEntity> queryList(Map<String, Object> map);

    /**
     * 日志记录数
     *
     * @param map
     * @return
     */
    int queryTotal(Map<String, Object> map);

    /**
     * 保存
     *
     * @param sysLog
     * @throws Exception
     */
    void save(SysLogEntity sysLog) throws Exception;

    /**
     * 更新
     *
     * @param sysLog
     * @throws Exception
     */
    void update(SysLogEntity sysLog) throws Exception;

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

    /**
     * 根据传入的参数删除
     * @param param 传入删除条件参数 id 开始时间 结束时间
     * @throws Exception
     */
    void deleteByParams(Map<String, Object> param) throws Exception;

    /**
     * 保存异常的日志信息
     * @param params
     */
    void saveErrorLog(String params);
}
