package io.renren.modules.api.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

/**
 * @author ChunLiang Hu
 * @Company
 * @Project renren-fast
 * @Package io.renren.modules.api.config
 * @Description TODO(描述)
 * @create 2017/10/21-12:08
 */
@Slf4j
@ConfigurationProperties(prefix = "renren.jwt")
@Component
class JWTConfig {
    private val log = LoggerFactory.getLogger(JWTConfig::class.java)
    private var secret: String? = null
    var expire: Long = 0
    var header: String? = null

    /**
     * 生成jwt token
     */
    fun generateToken(userId: Long): String {
        //过期时间
        val expireDate = LocalDateTime.now().plusSeconds(expire * 1000)

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(userId.toString() + "")
                .setIssuedAt(Date())
                .setExpiration(Date.from(expireDate.toInstant(ZoneOffset.UTC)))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact()
    }

    fun getClaimByToken(token: String): Claims? {
        return try {
            Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .body
        } catch (e: Exception) {
            log.debug("validate is token error ", e)
            null
        }

    }

    /**
     * token是否过期
     *
     * @return true：过期
     */
    fun isTokenExpired(expiration: LocalDateTime): Boolean {
        return expiration.isBefore(LocalDateTime.now())
    }
}
