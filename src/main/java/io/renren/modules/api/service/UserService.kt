package io.renren.modules.api.service


import io.renren.modules.api.entity.UserEntity

/**
 * 用户
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-23 15:22:06
 */
interface UserService {
    /**
     * 根据用户ID查询用户数据
     *
     * @param userId
     * @return
     */
    fun queryObject(userId: Long): UserEntity

    /**
     * 查询用户列表
     *
     * @param map
     * @return
     */
    fun queryList(map: Map<String, Any>): List<UserEntity>

    /**
     * 查询总记录数
     *
     * @param map
     * @return
     */
    fun queryTotal(map: Map<String, Any>): Int

    /**
     * 根据手机号和密码保存用户
     *
     * @param mobile
     * @param password
     * @throws Exception
     */
    @Throws(Exception::class)
    fun save(mobile: String, password: String)

    /**
     * 更新
     *
     * @param user
     * @throws Exception
     */
    @Throws(Exception::class)
    fun update(user: UserEntity)

    /**
     * 删除
     *
     * @param userId
     * @throws Exception
     */
    @Throws(Exception::class)
    fun delete(userId: Long)

    /**
     * 批量删除
     *
     * @param userIds
     * @throws Exception
     */
    @Throws(Exception::class)
    fun deleteBatch(userIds: Array<Long>)

    /**
     * 根据手机号查询用户
     *
     * @param mobile
     * @return
     */
    fun queryByMobile(mobile: String): UserEntity?

    /**
     * 用户登录
     *
     * @param mobile   手机号
     * @param password 密码
     * @return 返回用户ID
     */
    fun login(mobile: String, password: String): Long?

}
