package io.renren.modules.sys.service.impl

import io.renren.common.exception.RRException
import io.renren.common.utils.Constant
import io.renren.modules.sys.dao.SysRoleDao
import io.renren.modules.sys.dao.SysUserDao
import io.renren.modules.sys.entity.SysUserEntity
import io.renren.modules.sys.service.SysUserRoleService
import io.renren.modules.sys.service.SysUserService
import org.apache.commons.lang.RandomStringUtils
import org.apache.shiro.crypto.hash.Sha256Hash
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*


/**
 * 系统用户
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年9月18日 上午9:46:09
 */
@Service("sysUserService")
class SysUserServiceImpl @Autowired constructor(private val sysUserDao: SysUserDao, private val sysUserRoleService: SysUserRoleService,
                                                private val sysRoleDao: SysRoleDao) : SysUserService {

    override fun queryAllPerms(userId: Long?): List<String> {
        return sysUserDao.queryAllPerms(userId)
    }

    override fun queryAllMenuId(userId: Long?): List<Long> {
        return sysUserDao.queryAllMenuId(userId)
    }

    override fun queryByUserName(username: String): SysUserEntity {
        return sysUserDao.queryByUserName(username)
    }

    override fun queryObject(userId: Long?): SysUserEntity {
        return sysUserDao.queryObject(userId!!)
    }

    override fun queryList(map: Map<String, Any>): List<SysUserEntity> {
        return sysUserDao.queryList(map)
    }

    override fun queryTotal(map: Map<String, Any>): Int {
        return sysUserDao.queryTotal(map)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun save(user: SysUserEntity) {
        user.createTime = LocalDateTime.now()
        //sha256加密
        val salt = RandomStringUtils.randomAlphanumeric(20)
        user.password = Sha256Hash(user.password, salt).toHex()
        user.salt = salt
        sysUserDao.save(user)

        //检查角色是否越权
        checkRole(user)

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.userId, user.roleIdList!!)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun update(user: SysUserEntity) {
        if (user.password.isNullOrBlank()) {
            user.password = null
        } else {
            user.password = Sha256Hash(user.password, user.salt).toHex()
        }
        sysUserDao.update(user)

        //检查角色是否越权
        checkRole(user)

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.userId, user.roleIdList!!)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun deleteBatch(userIds: Array<Long>) {
        sysUserDao.deleteBatch(userIds)
    }

    override fun updatePassword(userId: Long?, password: String, newPassword: String): Int {
        val map = HashMap<String, Any>(16)
        map.put("userId", userId!!)
        map.put("password", password)
        map.put("newPassword", newPassword)
        return sysUserDao.updatePassword(map)
    }

    /**
     * 检查角色是否越权
     */
    private fun checkRole(user: SysUserEntity) {
        //如果不是超级管理员，则需要判断用户的角色是否自己创建
        if (user.createUserId!!.toInt() == Constant.SUPER_ADMIN) {
            return
        }

        //查询用户创建的角色列表
        val roleIdList = sysRoleDao.queryRoleIdList(user.createUserId)

        //判断是否越权
        if (!roleIdList.containsAll(user.roleIdList!!)) {
            throw RRException("新增用户所选角色，不是本人创建")
        }
    }
}
