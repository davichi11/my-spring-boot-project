package io.renren.modules.sys.service;

import io.renren.modules.sys.entity.SysRoleEntity;

import java.util.List;
import java.util.Map;


/**
 * 角色
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年9月18日 上午9:42:52
 */
public interface SysRoleService {

    /**
     * 根据ID查询
     *
     * @param roleId
     * @return
     */
    SysRoleEntity queryObject(Long roleId);

    /**
     * 列表查询
     *
     * @param map
     * @return
     */
    List<SysRoleEntity> queryList(Map<String, Object> map);

    /**
     * 查询总记录数
     *
     * @param map
     * @return
     */
    int queryTotal(Map<String, Object> map);

    /**
     * 保存
     *
     * @param role
     * @throws Exception
     */
    void save(SysRoleEntity role) throws Exception;

    /**
     * 更新
     *
     * @param role
     * @throws Exception
     */
    void update(SysRoleEntity role) throws Exception;

    /**
     * 批量删除
     *
     * @param roleIds
     * @throws Exception
     */
    void deleteBatch(Long[] roleIds) throws Exception;

    /**
     * 查询用户创建的角色ID列表
     *
     * @param createUserId
     * @return
     */
    List<Long> queryRoleIdList(Long createUserId);
}
