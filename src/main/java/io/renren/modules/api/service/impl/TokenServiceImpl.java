package io.renren.modules.api.service.impl;

import io.renren.modules.api.dao.TokenDao;
import io.renren.modules.api.entity.TokenEntity;
import io.renren.modules.api.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service("tokenService")
public class TokenServiceImpl implements TokenService {
    @Autowired
    private TokenDao tokenDao;
    /**
     * 12小时后过期
     */
    private final static int EXPIRE = 3600 * 12;

    @Override
    public TokenEntity queryByUserId(Long userId) {
        return tokenDao.queryByUserId(userId);
    }

    @Override
    public TokenEntity queryByToken(String token) {
        return tokenDao.queryByToken(token);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(TokenEntity token) throws Exception {
        tokenDao.save(token);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(TokenEntity token) throws Exception {
        tokenDao.update(token);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createToken(long userId) throws Exception {
        //生成一个token
        String token = UUID.randomUUID().toString();
        //当前时间
        LocalDate now = LocalDate.now();

        //过期时间
        LocalDateTime expireTime = LocalDateTime.now().plusHours(12);

        //判断是否生成过token
        TokenEntity tokenEntity = queryByUserId(userId);
        if (tokenEntity == null) {
            tokenEntity = new TokenEntity();
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

        Map<String, Object> map = new HashMap<>(16);
        map.put("token", token);
        map.put("expire", EXPIRE);

        return map;
    }
}
