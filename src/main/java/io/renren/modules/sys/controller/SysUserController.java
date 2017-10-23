package io.renren.modules.sys.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.renren.common.annotation.SysLog;
import io.renren.common.exception.RRException;
import io.renren.common.utils.Constant;
import io.renren.common.utils.Result;
import io.renren.common.validator.ValidatorUtils;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.UpdateGroup;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserRoleService;
import io.renren.modules.sys.service.SysUserService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 系统用户
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年10月31日 上午10:40:10
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 所有用户列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:user:list")
    public Result list(@RequestParam Map<String, Object> params) {
        //只有超级管理员，才能查看所有管理员列表
        if (getUserId() != Constant.SUPER_ADMIN) {
            params.put("createUserId", getUserId());
        }

        //查询列表数据
        PageInfo<SysUserEntity> pageInfo = PageHelper.startPage(MapUtils.getInteger(params, "page"),
                MapUtils.getInteger(params, "limit")).doSelectPageInfo(() -> sysUserService.queryList(params));
        return Result.ok().put("page", pageInfo);
    }

    /**
     * 获取登录的用户信息
     */
    @RequestMapping("/info")
    public Result info() {
        return Result.ok().put("user", getUser());
    }

    /**
     * 修改登录用户密码
     */
    @SysLog("修改密码")
    @RequestMapping("/password")
    public Result password(String password, String newPassword) {

        //sha256加密
        password = new Sha256Hash(password, getUser().getSalt()).toHex();
        //sha256加密
        newPassword = Optional.ofNullable(newPassword).orElseThrow(() -> new RRException("新密码不为能空"));

        //更新密码
        int count = 0;
        try {
            count = sysUserService.updatePassword(getUserId(), password, newPassword);
        } catch (Exception e) {
            logger.error("修改密码异常", e);
        }
        if (count == 0) {
            return Result.error("原密码不正确");
        }

        return Result.ok();
    }

    /**
     * 用户信息
     */
    @RequestMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    public Result info(@PathVariable("userId") Long userId) {
        SysUserEntity user = sysUserService.queryObject(userId);

        //获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);

        return Result.ok().put("user", user);
    }

    /**
     * 保存用户
     */
    @SysLog("保存用户")
    @RequestMapping("/save")
    @RequiresPermissions("sys:user:save")
    public Result save(@RequestBody SysUserEntity user) {
        ValidatorUtils.validateEntity(user, AddGroup.class);

        user.setCreateUserId(getUserId());
        try {
            sysUserService.save(user);
        } catch (Exception e) {
            logger.error("保存用户异常", e);
            return Result.error("保存用户异常");
        }

        return Result.ok();
    }

    /**
     * 修改用户
     */
    @SysLog("修改用户")
    @RequestMapping("/update")
    @RequiresPermissions("sys:user:update")
    public Result update(@RequestBody SysUserEntity user) {
        ValidatorUtils.validateEntity(user, UpdateGroup.class);

        user.setCreateUserId(getUserId());
        try {
            sysUserService.update(user);
        } catch (Exception e) {
            logger.error("修改用户异常", e);
            return Result.error("修改用户异常");
        }

        return Result.ok();
    }

    /**
     * 删除用户
     */
    @SysLog("删除用户")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public Result delete(@RequestBody Long[] userIds) {
        if (ArrayUtils.contains(userIds, 1L)) {
            return Result.error("系统管理员不能删除");
        }

        if (ArrayUtils.contains(userIds, getUserId())) {
            return Result.error("当前用户不能删除");
        }

        try {
            sysUserService.deleteBatch(userIds);
        } catch (Exception e) {
            logger.error("删除用户异常", e);
            return Result.error("删除用户异常");
        }

        return Result.ok();
    }
}
