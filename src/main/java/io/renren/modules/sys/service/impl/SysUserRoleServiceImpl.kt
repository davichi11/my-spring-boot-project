package io.renren.modules.sys.service.impl

import io.renren.modules.sys.dao.SysUserRoleDao
import io.renren.modules.sys.service.SysUserRoleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


/**
 * 用户与角色对应关系
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年9月18日 上午9:45:48
 */
@Service("sysUserRoleService")
class SysUserRoleServiceImpl @Autowired constructor(private val sysUserRoleDao: SysUserRoleDao): SysUserRoleService {

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun saveOrUpdate(userId: Long?, roleIdList: List<Long>) {
        if (roleIdList.isEmpty()) {
            return
        }
        //先删除用户与角色关系
        userId?.let { sysUserRoleDao.delete(it) }

        //保存用户与角色关系
        val map = HashMap<String, Any>(16)
        map["userId"] = userId!!
        map["roleIdList"] = roleIdList
        sysUserRoleDao.save(map)
    }

    override fun queryRoleIdList(userId: Long?): List<Long> {
        return sysUserRoleDao.queryRoleIdList(userId)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun delete(userId: Long?) {
        userId?.let { sysUserRoleDao.delete(it) }
    }
}
