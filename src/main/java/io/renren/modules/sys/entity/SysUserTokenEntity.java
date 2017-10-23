package io.renren.modules.sys.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 系统用户Token
 */
@Data
public class SysUserTokenEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //用户ID
    private Long userId;
    //token
    private String token;
    //过期时间
    private LocalDateTime expireTime;
    //更新时间
    private LocalDateTime updateTime;

}
