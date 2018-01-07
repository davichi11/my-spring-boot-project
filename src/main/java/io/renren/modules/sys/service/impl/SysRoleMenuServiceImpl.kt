package io.renren.modules.sys.service.impl

import io.renren.modules.sys.dao.SysRoleMenuDao
import io.renren.modules.sys.service.SysRoleMenuService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


/**
 * 角色与菜单对应关系
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年9月18日 上午9:44:35
 */
@Service("sysRoleMenuService")
class SysRoleMenuServiceImpl @Autowired constructor(private val sysRoleMenuDao: SysRoleMenuDao): SysRoleMenuService {

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun saveOrUpdate(roleId: Long?, menuIdList: List<Long>) {
        //先删除角色与菜单关系
        sysRoleMenuDao.delete(roleId!!)

        if (menuIdList.isEmpty()) {
            return
        }

        //保存角色与菜单关系
        val map = mutableMapOf<String,Any>()
        map.put("roleId", roleId)
        map.put("menuIdList", menuIdList)
        sysRoleMenuDao.save(map)
    }

    override fun queryMenuIdList(roleId: Long?): List<Long> {
        return sysRoleMenuDao.queryMenuIdList(roleId)
    }

}
