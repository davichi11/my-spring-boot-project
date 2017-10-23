package io.renren.modules.sys.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.renren.common.annotation.SysLog;
import io.renren.common.utils.Constant;
import io.renren.common.utils.Result;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.SysRoleEntity;
import io.renren.modules.sys.service.SysRoleMenuService;
import io.renren.modules.sys.service.SysRoleService;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年11月8日 下午2:18:33
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 角色列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:role:list")
    public Result list(@RequestParam Map<String, Object> params) {
        //如果不是超级管理员，则只查询自己创建的角色列表
        if (getUserId() != Constant.SUPER_ADMIN) {
            params.put("createUserId", getUserId());
        }
        //查询列表数据
        PageInfo<SysRoleEntity> pageInfo = PageHelper.startPage(MapUtils.getInteger(params, "page"),
                MapUtils.getInteger(params, "limit")).doSelectPageInfo(() -> sysRoleService.queryList(params));
        return Result.ok().put("page", pageInfo);
    }

    /**
     * 角色列表
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:role:select")
    public Result select() {
        Map<String, Object> map = new HashMap<>(16);

        //如果不是超级管理员，则只查询自己所拥有的角色列表
        if (getUserId() != Constant.SUPER_ADMIN) {
            map.put("createUserId", getUserId());
        }
        List<SysRoleEntity> list = sysRoleService.queryList(map);

        return Result.ok().put("list", list);
    }

    /**
     * 角色信息
     */
    @RequestMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    public Result info(@PathVariable("roleId") Long roleId) {
        SysRoleEntity role = sysRoleService.queryObject(roleId);

        //查询角色对应的菜单
        List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);

        return Result.ok().put("role", role);
    }

    /**
     * 保存角色
     */
    @SysLog("保存角色")
    @RequestMapping("/save")
    @RequiresPermissions("sys:role:save")
    public Result save(@RequestBody SysRoleEntity role) {
        ValidatorUtils.validateEntity(role);

        role.setCreateUserId(getUserId());
        try {
            sysRoleService.save(role);
        } catch (Exception e) {
            logger.error("保存角色异常", e);
        }

        return Result.ok();
    }

    /**
     * 修改角色
     */
    @SysLog("修改角色")
    @RequestMapping("/update")
    @RequiresPermissions("sys:role:update")
    public Result update(@RequestBody SysRoleEntity role) {
        ValidatorUtils.validateEntity(role);

        role.setCreateUserId(getUserId());
        try {
            sysRoleService.update(role);
        } catch (Exception e) {
            logger.error("更新角色异常", e);
        }

        return Result.ok();
    }

    /**
     * 删除角色
     */
    @SysLog("删除角色")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    public Result delete(@RequestBody Long[] roleIds) {
        try {
            sysRoleService.deleteBatch(roleIds);
        } catch (Exception e) {
            logger.error("删除角色异常", e);
        }

        return Result.ok();
    }
}
