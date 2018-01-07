package io.renren.modules.sys.service.impl

import io.renren.common.utils.Result
import io.renren.modules.sys.dao.SysUserTokenDao
import io.renren.modules.sys.entity.SysUserTokenEntity
import io.renren.modules.sys.oauth2.TokenGenerator
import io.renren.modules.sys.service.SysUserTokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import java.time.LocalDateTime


@Service("sysUserTokenService")
class SysUserTokenServiceImpl @Autowired constructor(private val sysUserTokenDao: SysUserTokenDao): SysUserTokenService {

    override fun queryByUserId(userId: Long?): SysUserTokenEntity {
        return sysUserTokenDao.queryByUserId(userId)
    }

    override fun queryByToken(token: String): SysUserTokenEntity {
        return sysUserTokenDao.queryByToken(token)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun save(token: SysUserTokenEntity) {
        sysUserTokenDao.save(token)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun update(token: SysUserTokenEntity) {
        sysUserTokenDao.update(token)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun createToken(userId: Long): Result {
        //生成一个token
        val token = TokenGenerator.generateValue()

        //当前时间
        val now = LocalDateTime.now()
        //过期时间
        val expireTime = LocalDateTime.now().plusDays(1)

        //判断是否生成过token
        var tokenEntity: SysUserTokenEntity? = queryByUserId(userId)
        if (tokenEntity == null) {
            tokenEntity = SysUserTokenEntity()
            tokenEntity.userId = userId
            tokenEntity.token = token
            tokenEntity.updateTime = now
            tokenEntity.expireTime = expireTime
            //保存token
            save(tokenEntity)
        } else {
            tokenEntity.token = token
            tokenEntity.updateTime = now
            tokenEntity.expireTime = expireTime

            //更新token
            update(tokenEntity)
        }

        return Result().ok().put("token", token!!).put("expire", EXPIRE)
    }

    companion object {
        /**
         * 12小时后过期
         */
        private val EXPIRE = 3600 * 12
    }
}
