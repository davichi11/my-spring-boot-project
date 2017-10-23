package io.renren.modules.api.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

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
public class JWTConfig {
    private String secret;
    private long expire;
    private String header;

    /**
     * 生成jwt token
     */
    public String generateToken(long userId) {
        //过期时间
        LocalDateTime expireDate = LocalDateTime.now().plusSeconds(expire * 1000);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(userId + "")
                .setIssuedAt(new Date())
                .setExpiration(Date.from(expireDate.toInstant(ZoneOffset.UTC)))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Claims getClaimByToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.debug("validate is token error ", e);
            return null;
        }
    }

    /**
     * token是否过期
     *
     * @return true：过期
     */
    public boolean isTokenExpired(LocalDateTime expiration) {
        return expiration.isBefore(LocalDateTime.now());
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
