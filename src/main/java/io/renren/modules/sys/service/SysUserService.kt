package io.renren.modules.sys.service

import io.renren.modules.sys.entity.SysUserEntity


/**
 * 系统用户
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年9月18日 上午9:43:39
 */
interface SysUserService {

    /**
     * 查询用户的所有权限
     *
     * @param userId 用户ID
     * @return
     */
    fun queryAllPerms(userId: Long): MutableList<String?>

    /**
     * 查询用户的所有菜单ID
     *
     * @param userId
     * @return
     */
    fun queryAllMenuId(userId: Long): List<Long>

    /**
     * 根据用户名，查询系统用户
     *
     * @param username
     * @return
     */
    fun queryByUserName(username: String): SysUserEntity

    /**
     * 根据用户ID，查询用户
     *
     * @param userId
     * @return
     */
    fun queryObject(userId: Long): SysUserEntity

    /**
     * 查询用户列表
     *
     * @param map
     * @return
     */
    fun queryList(map: Map<String, Any>): List<SysUserEntity>

    /**
     * 查询总数
     *
     * @param map
     * @return
     */
    fun queryTotal(map: Map<String, Any>): Int

    /**
     * 保存用户
     *
     * @param user
     * @throws Exception
     */
    @Throws(Exception::class)
    fun save(user: SysUserEntity)

    /**
     * 修改用户
     *
     * @param user
     * @throws Exception
     */
    @Throws(Exception::class)
    fun update(user: SysUserEntity)

    /**
     * 删除用户
     *
     * @param userIds
     * @throws Exception
     */
    @Throws(Exception::class)
    fun deleteBatch(userIds: Array<Long>)

    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param password    原密码
     * @param newPassword 新密码
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun updatePassword(userId: Long, password: String, newPassword: String): Int
}
