package io.renren.modules.sys.service.impl;

import io.renren.modules.sys.dao.SysLogDao;
import io.renren.modules.sys.entity.SysLogEntity;
import io.renren.modules.sys.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author huchunliang
 */
@Service("sysLogService")
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private SysLogDao sysLogDao;

    @Override
    public SysLogEntity queryObject(Long id) {
        return sysLogDao.queryObject(id);
    }

    @Override
    public List<SysLogEntity> queryList(Map<String, Object> map) {
        return sysLogDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysLogDao.queryTotal(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysLogEntity sysLog) throws Exception {
        sysLogDao.save(sysLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysLogEntity sysLog) throws Exception {
        sysLogDao.update(sysLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) throws Exception {
        sysLogDao.delete(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] ids) throws Exception {
        sysLogDao.deleteBatch(ids);
    }

}
