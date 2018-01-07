package io.renren.modules.sys.dao

import io.renren.modules.sys.entity.SysMenuEntity
import org.apache.ibatis.annotations.Mapper

/**
 * 菜单管理
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年9月18日 上午9:33:01
 */
@Mapper
interface SysMenuDao : BaseDao<SysMenuEntity> {

    /**
     * 根据父菜单，查询子菜单
     *
     * @param parentId 父菜单ID
     */
    fun queryListParentId(parentId: Long?): List<SysMenuEntity>

    /**
     * 获取不包含按钮的菜单列表
     */
    fun queryNotButtonList(): MutableList<SysMenuEntity>

    /**
     * 查询用户的权限列表
     */
    fun queryUserList(userId: Long?): List<SysMenuEntity>
}
