package io.renren.modules.sys.service;

import java.util.List;


/**
 * 用户与角色对应关系
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年9月18日 上午9:43:24
 */
public interface SysUserRoleService {
    /**
     * 更新或保存
     *
     * @param userId
     * @param roleIdList
     * @throws Exception
     */
    void saveOrUpdate(Long userId, List<Long> roleIdList) throws Exception;

    /**
     * 根据用户ID，获取角色ID列表
     *
     * @param userId
     * @return
     */
    List<Long> queryRoleIdList(Long userId);

    /**
     * 删除
     *
     * @param userId
     * @throws Exception
     */
    void delete(Long userId) throws Exception;
}
