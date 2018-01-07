package io.renren.modules.sys.dao

import io.renren.modules.sys.entity.SysRoleMenuEntity
import org.apache.ibatis.annotations.Mapper

/**
 * 角色与菜单对应关系
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年9月18日 上午9:33:46
 */
@Mapper
interface SysRoleMenuDao : BaseDao<SysRoleMenuEntity> {

    /**
     * 根据角色ID，获取菜单ID列表
     */
    fun queryMenuIdList(roleId: Long?): List<Long>
}
