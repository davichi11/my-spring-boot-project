package io.renren.modules.sys.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import io.renren.common.controller.BaseController;
import io.renren.common.utils.Result;
import io.renren.modules.sys.entity.SysLogEntity;
import io.renren.modules.sys.service.SysLogService;
import io.vertx.core.MultiMap;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


/**
 * 系统日志
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-08 10:40:56
 */
@Controller
//@RequestMapping("/sys/log")
public class SysLogController extends BaseController {
    @Autowired
    private SysLogService sysLogService;

    /**
     * 列表
     */
    @ResponseBody
//    @RequestMapping("/list")
    @RequiresPermissions("sys:log:list")
    public void list(RoutingContext routingContext) {
        MultiMap param = routingContext.request().params();
        Map<String, Object> params = Maps.newHashMap();
        param.forEach(stringEntry -> params.put(stringEntry.getKey(),stringEntry.getValue()));
        //查询列表数据
        PageInfo<SysLogEntity> pageInfo = PageHelper.startPage(MapUtils.getInteger(params, "page"),
                MapUtils.getInteger(params, "limit")).doSelectPageInfo(() -> sysLogService.queryList(params));
        doSuccess(routingContext, JSON.toJSONString(Result.ok().put("page", pageInfo)));
    }

    /**
     * 公用的deploy方法
     */
    @Override
    protected void deploy() {
        this.router.get("/sys/log/list").handler(this::list);
    }
}
