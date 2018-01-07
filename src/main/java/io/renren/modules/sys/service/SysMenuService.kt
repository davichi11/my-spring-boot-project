package io.renren.modules.sys.service

import io.renren.modules.sys.entity.SysMenuEntity


/**
 * 菜单管理
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年9月18日 上午9:42:16
 */
interface SysMenuService {

    /**
     * 根据父菜单，查询子菜单
     *
     * @param parentId   父菜单ID
     * @param menuIdList 用户菜单ID
     * @return
     */
    fun queryListParentId(parentId: Long?, menuIdList: List<Long>?): List<SysMenuEntity>

    /**
     * 根据父菜单，查询子菜单
     *
     * @param parentId 父菜单ID
     * @return
     */
    fun queryListParentId(parentId: Long?): List<SysMenuEntity>

    /**
     * 获取不包含按钮的菜单列表
     *
     * @return
     */
    fun queryNotButtonList(): MutableList<SysMenuEntity>

    /**
     * 获取用户菜单列表
     *
     * @param userId
     * @return
     */
    fun getUserMenuList(userId: Long?): List<SysMenuEntity>

    /**
     * 查询菜单
     *
     * @param menuId
     * @return
     */
    fun queryObject(menuId: Long?): SysMenuEntity

    /**
     * 查询菜单列表
     *
     * @param map
     * @return
     */
    fun queryList(map: Map<String, Any>): List<SysMenuEntity>

    /**
     * 查询总数
     *
     * @param map
     * @return
     */
    fun queryTotal(map: Map<String, Any>): Int

    /**
     * 保存菜单
     *
     * @param menu
     * @throws Exception
     */
    @Throws(Exception::class)
    fun save(menu: SysMenuEntity)

    /**
     * 修改
     *
     * @param menu
     * @throws Exception
     */
    @Throws(Exception::class)
    fun update(menu: SysMenuEntity)

    /**
     * 删除
     *
     * @param menuIds
     * @throws Exception
     */
    @Throws(Exception::class)
    fun deleteBatch(menuIds: Array<Long>)

    /**
     * 查询用户的权限列表
     *
     * @param userId
     * @return
     */
    fun queryUserList(userId: Long?): List<SysMenuEntity>
}
