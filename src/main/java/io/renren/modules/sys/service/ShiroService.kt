package io.renren.modules.sys.service

import io.renren.modules.sys.entity.SysUserEntity
import io.renren.modules.sys.entity.SysUserTokenEntity

/**
 * shiro相关接口
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-06-06 8:49
 */
interface ShiroService {
    /**
     * 获取用户权限列表
     */
    fun getUserPermissions(userId: Long): Set<String>

    fun queryByToken(token: String): SysUserTokenEntity

    /**
     * 根据用户ID，查询用户
     *
     * @param userId
     */
    fun queryUser(userId: Long?): SysUserEntity
}
