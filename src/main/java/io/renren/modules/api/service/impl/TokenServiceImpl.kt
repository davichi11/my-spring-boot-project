package io.renren.modules.api.service.impl

import io.renren.modules.api.dao.TokenDao
import io.renren.modules.api.entity.TokenEntity
import io.renren.modules.api.service.TokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.HashMap
import java.util.UUID


@Service("tokenService")
class TokenServiceImpl : TokenService {
    @Autowired
    private val tokenDao: TokenDao? = null

    override fun queryByUserId(userId: Long?): TokenEntity {
        return tokenDao!!.queryByUserId(userId)
    }

    override fun queryByToken(token: String): TokenEntity {
        return tokenDao!!.queryByToken(token)
    }

    @Transactional(rollbackFor = arrayOf(Exception::class))
    @Throws(Exception::class)
    override fun save(token: TokenEntity) {
        tokenDao!!.save(token)
    }

    @Transactional(rollbackFor = arrayOf(Exception::class))
    @Throws(Exception::class)
    override fun update(token: TokenEntity) {
        tokenDao!!.update(token)
    }

    @Transactional(rollbackFor = arrayOf(Exception::class))
    @Throws(Exception::class)
    override fun createToken(userId: Long): Map<String, Any> {
        //生成一个token
        val token = UUID.randomUUID().toString()
        //当前时间
        val now = LocalDate.now()

        //过期时间
        val expireTime = LocalDateTime.now().plusHours(12)

        //判断是否生成过token
        var tokenEntity: TokenEntity? = queryByUserId(userId)
        if (tokenEntity == null) {
            tokenEntity = TokenEntity()
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

        val map = HashMap<String, Any>(16)
        map.put("token", token)
        map.put("expire", EXPIRE)

        return map
    }

    companion object {
        /**
         * 12小时后过期
         */
        private val EXPIRE = 3600 * 12
    }
}
