package io.renren.modules.oss.service.impl;

import io.renren.modules.oss.dao.SysOssDao;
import io.renren.modules.oss.entity.SysOssEntity;
import io.renren.modules.oss.service.SysOssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author huchunliang
 */
@Service("sysOssService")
public class SysOssServiceImpl implements SysOssService {
    @Autowired
    private SysOssDao sysOssDao;

    @Override
    public SysOssEntity queryObject(Long id) {
        return sysOssDao.queryObject(id);
    }

    @Override
    public List<SysOssEntity> queryList(Map<String, Object> map) {
        return sysOssDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysOssDao.queryTotal(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysOssEntity sysOss) throws Exception {
        sysOssDao.save(sysOss);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysOssEntity sysOss) throws Exception {
        sysOssDao.update(sysOss);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) throws Exception {
        sysOssDao.delete(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] ids) throws Exception {
        sysOssDao.deleteBatch(ids);
    }

}
