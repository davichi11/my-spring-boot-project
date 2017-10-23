package io.renren.modules.api.service;


import io.renren.modules.api.entity.TokenEntity;

import java.util.Map;

/**
 * 用户Token
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-23 15:22:07
 */
public interface TokenService {
    /**
     * 根据用户ID查询token
     *
     * @param userId
     * @return
     */
    TokenEntity queryByUserId(Long userId);

    /**
     * 查询token实体
     *
     * @param token
     * @return
     */
    TokenEntity queryByToken(String token);

    /**
     * 保存
     *
     * @param token
     * @throws Exception
     */
    void save(TokenEntity token) throws Exception;

    /**
     * 更新
     *
     * @param token
     * @throws Exception
     */
    void update(TokenEntity token) throws Exception;

    /**
     * 生成token
     *
     * @param userId 用户ID
     * @return 返回token相关信息
     * @throws Exception
     */
    Map<String, Object> createToken(long userId) throws Exception;

}
