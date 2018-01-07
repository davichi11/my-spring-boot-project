package io.renren.modules.api.dao

import io.renren.modules.api.entity.UserEntity
import io.renren.modules.sys.dao.BaseDao
import org.apache.ibatis.annotations.Mapper

/**
 * 用户
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-23 15:22:06
 */
@Mapper
interface UserDao : BaseDao<UserEntity> {

    /**
     * 根据手机号查询
     *
     * @param mobile
     * @return
     */
    fun queryByMobile(mobile: String): UserEntity

}
