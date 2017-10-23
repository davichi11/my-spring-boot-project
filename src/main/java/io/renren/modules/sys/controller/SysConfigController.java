package io.renren.modules.sys.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.renren.common.annotation.SysLog;
import io.renren.common.utils.Result;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.SysConfigEntity;
import io.renren.modules.sys.service.SysConfigService;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 系统配置信息
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年12月4日 下午6:55:53
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController extends AbstractController {
    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 所有配置列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:config:list")
    public Result list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        PageInfo<SysConfigEntity> pageInfo = PageHelper.startPage(MapUtils.getInteger(params, "page"),
                MapUtils.getInteger(params, "limit")).doSelectPageInfo(() -> sysConfigService.queryList(params));
        return Result.ok().put("page", pageInfo);
    }


    /**
     * 配置信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:config:info")
    public Result info(@PathVariable("id") Long id) {
        SysConfigEntity config = sysConfigService.queryObject(id);

        return Result.ok().put("config", config);
    }

    /**
     * 保存配置
     */
    @SysLog("保存配置")
    @RequestMapping("/save")
    @RequiresPermissions("sys:config:save")
    public Result save(@RequestBody SysConfigEntity config) {
        ValidatorUtils.validateEntity(config);

        try {
            sysConfigService.save(config);
        } catch (Exception e) {
            logger.error("保存配置异常", e);
            return Result.error("保存配置异常");
        }

        return Result.ok();
    }

    /**
     * 修改配置
     */
    @SysLog("修改配置")
    @RequestMapping("/update")
    @RequiresPermissions("sys:config:update")
    public Result update(@RequestBody SysConfigEntity config) {
        ValidatorUtils.validateEntity(config);

        try {
            sysConfigService.update(config);
        } catch (Exception e) {
            logger.error("修改配置异常", e);
            return Result.error("修改配置异常");
        }

        return Result.ok();
    }

    /**
     * 删除配置
     */
    @SysLog("删除配置")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:config:delete")
    public Result delete(@RequestBody Long[] ids) {
        try {
            sysConfigService.deleteBatch(ids);
        } catch (Exception e) {
            logger.error("删除配置异常", e);
            return Result.error("删除配置异常");
        }
        return Result.ok();
    }

}
