package io.renren.modules.sys.service


/**
 * 角色与菜单对应关系
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年9月18日 上午9:42:30
 */
interface SysRoleMenuService {

    /**
     * 更新或插入
     *
     * @param roleId
     * @param menuIdList
     * @throws Exception
     */
    @Throws(Exception::class)
    fun saveOrUpdate(roleId: Long?, menuIdList: List<Long>)

    /**
     * 根据角色ID，获取菜单ID列表
     *
     * @param roleId
     * @return
     */
    fun queryMenuIdList(roleId: Long?): List<Long>

}
