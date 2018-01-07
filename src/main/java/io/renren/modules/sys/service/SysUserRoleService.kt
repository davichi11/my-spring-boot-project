package io.renren.modules.sys.service


/**
 * 用户与角色对应关系
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年9月18日 上午9:43:24
 */
interface SysUserRoleService {
    /**
     * 更新或保存
     *
     * @param userId
     * @param roleIdList
     * @throws Exception
     */
    @Throws(Exception::class)
    fun saveOrUpdate(userId: Long?, roleIdList: List<Long>)

    /**
     * 根据用户ID，获取角色ID列表
     *
     * @param userId
     * @return
     */
    fun queryRoleIdList(userId: Long?): List<Long>

    /**
     * 删除
     *
     * @param userId
     * @throws Exception
     */
    @Throws(Exception::class)
    fun delete(userId: Long?)
}
