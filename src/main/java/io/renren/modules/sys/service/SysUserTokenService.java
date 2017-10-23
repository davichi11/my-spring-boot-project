package io.renren.modules.sys.service;

import io.renren.common.utils.Result;
import io.renren.modules.sys.entity.SysUserTokenEntity;

/**
 * 用户Token
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-23 15:22:07
 */
public interface SysUserTokenService {

    /**
     * 根据用户ID查询系统用户Token
     *
     * @param userId
     * @return
     */
    SysUserTokenEntity queryByUserId(Long userId);

    /**
     * 根据token查询系统用户Token
     *
     * @param token
     * @return
     */
    SysUserTokenEntity queryByToken(String token);

    /**
     * 保存
     *
     * @param token
     * @throws Exception
     */
    void save(SysUserTokenEntity token) throws Exception;

    /**
     * 更新
     *
     * @param token
     * @throws Exception
     */
    void update(SysUserTokenEntity token) throws Exception;

    /**
     * 生成token
     *
     * @param userId 用户ID
     * @return
     * @throws Exception
     */
    Result createToken(long userId) throws Exception;

}
