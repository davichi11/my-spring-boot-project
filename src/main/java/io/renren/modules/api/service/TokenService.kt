package io.renren.modules.api.service


import io.renren.modules.api.entity.TokenEntity

/**
 * 用户Token
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-23 15:22:07
 */
interface TokenService {
    /**
     * 根据用户ID查询token
     *
     * @param userId
     * @return
     */
    fun queryByUserId(userId: Long?): TokenEntity

    /**
     * 查询token实体
     *
     * @param token
     * @return
     */
    fun queryByToken(token: String): TokenEntity

    /**
     * 保存
     *
     * @param token
     * @throws Exception
     */
    @Throws(Exception::class)
    fun save(token: TokenEntity)

    /**
     * 更新
     *
     * @param token
     * @throws Exception
     */
    @Throws(Exception::class)
    fun update(token: TokenEntity)

    /**
     * 生成token
     *
     * @param userId 用户ID
     * @return 返回token相关信息
     * @throws Exception
     */
    @Throws(Exception::class)
    fun createToken(userId: Long): Map<String, Any>

}
