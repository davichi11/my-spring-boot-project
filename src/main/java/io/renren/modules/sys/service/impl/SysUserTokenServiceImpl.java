package io.renren.modules.sys.service.impl;

import io.renren.common.utils.Result;
import io.renren.modules.sys.dao.SysUserTokenDao;
import io.renren.modules.sys.entity.SysUserTokenEntity;
import io.renren.modules.sys.oauth2.TokenGenerator;
import io.renren.modules.sys.service.SysUserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service("sysUserTokenService")
public class SysUserTokenServiceImpl implements SysUserTokenService {
    @Autowired
    private SysUserTokenDao sysUserTokenDao;
    /**
     * 12小时后过期
     */
    private final static int EXPIRE = 3600 * 12;

    @Override
    public SysUserTokenEntity queryByUserId(Long userId) {
        return sysUserTokenDao.queryByUserId(userId);
    }

    @Override
    public SysUserTokenEntity queryByToken(String token) {
        return sysUserTokenDao.queryByToken(token);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysUserTokenEntity token) throws Exception {
        sysUserTokenDao.save(token);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysUserTokenEntity token) throws Exception {
        sysUserTokenDao.update(token);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result createToken(long userId) throws Exception {
        //生成一个token
        String token = TokenGenerator.generateValue();

        //当前时间
        LocalDateTime now = LocalDateTime.now();
        //过期时间
        LocalDateTime expireTime = LocalDateTime.now().plusDays(1);

        //判断是否生成过token
        SysUserTokenEntity tokenEntity = queryByUserId(userId);
        if (tokenEntity == null) {
            tokenEntity = new SysUserTokenEntity();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //保存token
            save(tokenEntity);
        } else {
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //更新token
            update(tokenEntity);
        }

        return Result.ok().put("token", token).put("expire", EXPIRE);
    }
}
