package io.renren.modules.sys.service

import io.renren.common.utils.Result
import io.renren.modules.sys.entity.SysUserTokenEntity

/**
 * 用户Token
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-23 15:22:07
 */
interface SysUserTokenService {

    /**
     * 根据用户ID查询系统用户Token
     *
     * @param userId
     * @return
     */
    fun queryByUserId(userId: Long?): SysUserTokenEntity

    /**
     * 根据token查询系统用户Token
     *
     * @param token
     * @return
     */
    fun queryByToken(token: String): SysUserTokenEntity

    /**
     * 保存
     *
     * @param token
     * @throws Exception
     */
    @Throws(Exception::class)
    fun save(token: SysUserTokenEntity)

    /**
     * 更新
     *
     * @param token
     * @throws Exception
     */
    @Throws(Exception::class)
    fun update(token: SysUserTokenEntity)

    /**
     * 生成token
     *
     * @param userId 用户ID
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun createToken(userId: Long): Result

}
