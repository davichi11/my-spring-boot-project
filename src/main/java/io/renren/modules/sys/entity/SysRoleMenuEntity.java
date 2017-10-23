package io.renren.modules.sys.entity;


import lombok.Data;

import java.io.Serializable;

/**
 * 角色与菜单对应关系
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年9月18日 上午9:28:13
 */
@Data
public class SysRoleMenuEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 菜单ID
     */
    private Long menuId;


}
