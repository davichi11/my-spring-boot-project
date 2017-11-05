package io.renren.modules.sys.entity;


import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 系统配置信息
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年12月4日 下午6:43:36
 */
@Data
public class SysConfigEntity {
    private Long id;
    @NotNull(message = "参数名不能为空")
    private String key;
    @NotNull(message = "参数值不能为空")
    private String value;
    private String remark;
}
