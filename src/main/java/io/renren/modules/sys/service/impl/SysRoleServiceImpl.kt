package io.renren.modules.sys.service.impl

import io.renren.common.exception.RRException
import io.renren.common.utils.Constant
import io.renren.modules.sys.dao.SysRoleDao
import io.renren.modules.sys.dao.SysUserDao
import io.renren.modules.sys.entity.SysRoleEntity
import io.renren.modules.sys.service.SysRoleMenuService
import io.renren.modules.sys.service.SysRoleService
import io.renren.modules.sys.service.SysUserRoleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import java.time.LocalDateTime


/**
 * 角色
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年9月18日 上午9:45:12
 */
@Service("sysRoleService")
class SysRoleServiceImpl @Autowired constructor(
        private val sysRoleDao: SysRoleDao,
        private val sysUserRoleService: SysUserRoleService,
        private val sysRoleMenuService: SysRoleMenuService, private val sysUserDao: SysUserDao) : SysRoleService {

    override fun queryObject(roleId: Long?): SysRoleEntity {
        return sysRoleDao.queryObject(roleId!!)
    }

    override fun queryList(map: Map<String, Any>): List<SysRoleEntity> {
        return sysRoleDao.queryList(map)
    }

    override fun queryTotal(map: Map<String, Any>): Int {
        return sysRoleDao.queryTotal(map)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun save(role: SysRoleEntity) {
        role.createTime = LocalDateTime.now()
        sysRoleDao.save(role)

        //检查权限是否越权
        role.checkPrems()

        //保存角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.roleId, role.menuIdList!!)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun update(role: SysRoleEntity) {
        sysRoleDao.update(role)

        //检查权限是否越权
        role.checkPrems()

        //更新角色与菜单关系
        role.menuIdList?.let { sysRoleMenuService.saveOrUpdate(role.roleId, it) }
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun deleteBatch(roleIds: Array<Long>) {
        sysRoleDao.deleteBatch(roleIds)
    }

    override fun queryRoleIdList(createUserId: Long?): List<Long> {
        return sysRoleDao.queryRoleIdList(createUserId)
    }

    /**
     * 检查权限是否越权
     */
    private fun SysRoleEntity.checkPrems() {
        //如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
        if (this.createUserId!!.toInt() == Constant.SUPER_ADMIN) {
            return
        }

        //查询用户所拥有的菜单列表
        val menuIdList = sysUserDao.queryAllMenuId(this.createUserId)

        //判断是否越权
        if (!menuIdList.containsAll(this.menuIdList!!)) {
            throw RRException("新增角色的权限，已超出你的权限范围")
        }
    }
}
