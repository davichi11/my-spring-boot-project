package io.renren.modules.sys.service.impl

import io.renren.common.utils.Constant
import io.renren.modules.sys.dao.SysMenuDao
import io.renren.modules.sys.dao.SysUserDao
import io.renren.modules.sys.dao.SysUserTokenDao
import io.renren.modules.sys.entity.SysUserEntity
import io.renren.modules.sys.entity.SysUserTokenEntity
import io.renren.modules.sys.service.ShiroService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ShiroServiceImpl @Autowired constructor(
        private val sysMenuDao: SysMenuDao, private val sysUserDao: SysUserDao,
                                              private val sysUserTokenDao: SysUserTokenDao) : ShiroService {

    override fun getUserPermissions(userId: Long): MutableSet<String> {
        var permsList: MutableList<String?> = mutableListOf()

        //系统管理员，拥有最高权限
        if (userId == Constant.SUPER_ADMIN.toLong()) {
            val menuList = sysMenuDao.queryList(mapOf())
            menuList.map { it.perms }.filter { it != null }.forEach { s: String? -> permsList.add(s!!) }
        } else {
            permsList = sysUserDao.queryAllPerms(userId)
        }
        //用户权限列表
        val permsSet = mutableSetOf<String>()
        permsList.filterNotNull()
                .map { perms -> perms.trim().split(",") }
                .forEach { permsSet.addAll(it) }
        return permsSet
    }

    override fun queryByToken(token: String): SysUserTokenEntity {
        return sysUserTokenDao.queryByToken(token)
    }

    override fun queryUser(userId: Long): SysUserEntity {
        return sysUserDao.queryObject(userId)
    }
}
