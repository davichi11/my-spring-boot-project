package io.renren.modules.sys.entity;


import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

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
    @NotBlank(message = "参数名不能为空")
    private String key;
    @NotBlank(message = "参数值不能为空")
    private String value;
    private String remark;
}
