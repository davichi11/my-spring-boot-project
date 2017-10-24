package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.SysLogEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 系统日志
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-08 10:40:56
 */
@Mapper
public interface SysLogDao extends BaseDao<SysLogEntity> {
    /**
     * 根据参数删除
     * @param param
     * @throws Exception
     */
    void deleteByParams(Map<String, Object> param) throws Exception;
}
