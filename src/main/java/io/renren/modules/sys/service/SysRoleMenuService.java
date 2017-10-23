package io.renren.modules.sys.service;

import java.util.List;


/**
 * 角色与菜单对应关系
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年9月18日 上午9:42:30
 */
public interface SysRoleMenuService {

    /**
     * 更新或插入
     *
     * @param roleId
     * @param menuIdList
     * @throws Exception
     */
    void saveOrUpdate(Long roleId, List<Long> menuIdList) throws Exception;

    /**
     * 根据角色ID，获取菜单ID列表
     *
     * @param roleId
     * @return
     */
    List<Long> queryMenuIdList(Long roleId);

}
