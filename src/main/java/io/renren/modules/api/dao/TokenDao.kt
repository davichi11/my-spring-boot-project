package io.renren.modules.api.dao

import io.renren.modules.api.entity.TokenEntity
import io.renren.modules.sys.dao.BaseDao
import org.apache.ibatis.annotations.Mapper

/**
 * 用户Token
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-23 15:22:07
 */
@Mapper
interface TokenDao : BaseDao<TokenEntity> {

    /**
     * 根据ID查询
     *
     * @param userId
     * @return
     */
    fun queryByUserId(userId: Long?): TokenEntity

    /**
     * 根据token查询
     *
     * @param token
     * @return
     */
    fun queryByToken(token: String): TokenEntity

}
