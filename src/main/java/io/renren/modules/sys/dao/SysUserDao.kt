package io.renren.modules.sys.dao

import io.renren.modules.sys.entity.SysUserEntity
import org.apache.ibatis.annotations.Mapper

/**
 * 系统用户
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年9月18日 上午9:34:11
 */
@Mapper
interface SysUserDao : BaseDao<SysUserEntity> {

    /**
     * 查询用户的所有权限
     *
     * @param userId 用户ID
     */
    fun queryAllPerms(userId: Long?): MutableList<String>

    /**
     * 查询用户的所有菜单ID
     */
    fun queryAllMenuId(userId: Long?): List<Long>

    /**
     * 根据用户名，查询系统用户
     */
    fun queryByUserName(username: String): SysUserEntity

    /**
     * 修改密码
     */
    fun updatePassword(map: Map<String, Any>): Int
}
