package io.renren.modules.api.service.impl


import io.renren.common.exception.RRException
import io.renren.modules.api.dao.UserDao
import io.renren.modules.api.entity.UserEntity
import io.renren.modules.api.service.UserService
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*


@Service("userService")
class UserServiceImpl @Autowired constructor(private val userDao: UserDao) : UserService {

    override fun queryObject(userId: Long): UserEntity {
        return userDao.queryObject(userId)
    }

    override fun queryList(map: Map<String, Any>): List<UserEntity> {
        return userDao.queryList(map)
    }

    override fun queryTotal(map: Map<String, Any>): Int {
        return userDao.queryTotal(map)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun save(mobile: String, password: String) {
        val user = UserEntity()
        user.mobile = mobile
        user.username = mobile
        user.password = DigestUtils.sha256Hex(password)
        user.createTime = LocalDateTime.now()
        userDao.save(user)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun update(user: UserEntity) {
        userDao.update(user)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun delete(userId: Long) {
        userDao.delete(userId)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun deleteBatch(userIds: Array<Long>) {
        userDao.deleteBatch(userIds)
    }

    override fun queryByMobile(mobile: String): UserEntity {
        return userDao.queryByMobile(mobile)
    }

    override fun login(mobile: String, password: String): Long {
        val user = Optional.ofNullable(queryByMobile(mobile)).filter { u -> u.password == DigestUtils.sha256Hex(password) }
                .orElseThrow { RRException("手机号或密码错误") }
        return user.userId!!
    }
}
