package io.renren.modules.sys.dao

import io.renren.modules.sys.entity.SysRoleEntity
import org.apache.ibatis.annotations.Mapper

/**
 * 角色管理
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年9月18日 上午9:33:33
 */
@Mapper
interface SysRoleDao : BaseDao<SysRoleEntity> {

    /**
     * 查询用户创建的角色ID列表
     */
    fun queryRoleIdList(createUserId: Long?): List<Long>
}
