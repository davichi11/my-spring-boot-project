package io.renren.modules.sys.service;

import io.renren.modules.sys.entity.SysUserEntity;

import java.util.List;
import java.util.Map;


/**
 * 系统用户
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年9月18日 上午9:43:39
 */
public interface SysUserService {

    /**
     * 查询用户的所有权限
     *
     * @param userId 用户ID
     * @return
     */
    List<String> queryAllPerms(Long userId);

    /**
     * 查询用户的所有菜单ID
     *
     * @param userId
     * @return
     */
    List<Long> queryAllMenuId(Long userId);

    /**
     * 根据用户名，查询系统用户
     *
     * @param username
     * @return
     */
    SysUserEntity queryByUserName(String username);

    /**
     * 根据用户ID，查询用户
     *
     * @param userId
     * @return
     */
    SysUserEntity queryObject(Long userId);

    /**
     * 查询用户列表
     *
     * @param map
     * @return
     */
    List<SysUserEntity> queryList(Map<String, Object> map);

    /**
     * 查询总数
     *
     * @param map
     * @return
     */
    int queryTotal(Map<String, Object> map);

    /**
     * 保存用户
     *
     * @param user
     * @throws Exception
     */
    void save(SysUserEntity user) throws Exception;

    /**
     * 修改用户
     *
     * @param user
     * @throws Exception
     */
    void update(SysUserEntity user) throws Exception;

    /**
     * 删除用户
     *
     * @param userIds
     * @throws Exception
     */
    void deleteBatch(Long[] userIds) throws Exception;

    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param password    原密码
     * @param newPassword 新密码
     * @return
     * @throws Exception
     */
    int updatePassword(Long userId, String password, String newPassword) throws Exception;
}
