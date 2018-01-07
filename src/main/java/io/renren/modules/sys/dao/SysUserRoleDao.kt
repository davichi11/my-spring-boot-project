package io.renren.modules.sys.dao

import io.renren.modules.sys.entity.SysUserRoleEntity
import org.apache.ibatis.annotations.Mapper

/**
 * 用户与角色对应关系
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年9月18日 上午9:34:46
 */
@Mapper
interface SysUserRoleDao : BaseDao<SysUserRoleEntity> {

    /**
     * 根据用户ID，获取角色ID列表
     */
    fun queryRoleIdList(userId: Long?): List<Long>
}
