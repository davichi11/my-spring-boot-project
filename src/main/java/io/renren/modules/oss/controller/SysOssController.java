package io.renren.modules.oss.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import io.renren.common.controller.BaseController;
import io.renren.common.exception.RRException;
import io.renren.common.utils.ConfigConstant;
import io.renren.common.utils.Constant;
import io.renren.common.utils.Result;
import io.renren.common.validator.ValidatorUtils;
import io.renren.common.validator.group.AliyunGroup;
import io.renren.common.validator.group.QcloudGroup;
import io.renren.common.validator.group.QiniuGroup;
import io.renren.modules.oss.cloud.CloudStorageConfig;
import io.renren.modules.oss.entity.SysOssEntity;
import io.renren.modules.oss.service.SysOssService;
import io.renren.modules.sys.service.SysConfigService;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 文件上传
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-25 12:13:26
 */
@Slf4j
@RestController
//@RequestMapping("sys/oss")
public class SysOssController extends BaseController {
    @Autowired
    private SysOssService sysOssService;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private Router router;
    @Autowired
    private JsonObject jsonObject;

    private final static String KEY = ConfigConstant.CLOUD_STORAGE_CONFIG_KEY;

    /**
     * 公用的deploy方法
     */
    @Override
    protected void deploy() {
        this.router.get("/sys/oss/list").handler(this::list);
        this.router.get("/sys/oss/config").handler(this::config);
        this.router.post("/sys/oss/saveConfig").handler(this::saveConfig);
        this.router.get("/sys/oss/upload").handler(this::upload);
        this.router.get("/sys/oss/delete").handler(this::delete);
    }

    /**
     * 列表
     */
//    @RequestMapping("/list")
    @RequiresPermissions("sys:oss:all")
    public void list(RoutingContext routingContext) {
        MultiMap param = routingContext.request().params();
        Map<String, Object> params = Maps.newHashMap();
        param.forEach(stringEntry -> params.put(stringEntry.getKey(),stringEntry.getValue()));
        //查询列表数据
        PageInfo<SysOssEntity> pageInfo = PageHelper.startPage(MapUtils.getInteger(params, "page"),
                MapUtils.getInteger(params, "limit")).doSelectPageInfo(() -> sysOssService.queryList(params));
        doSuccess(routingContext, JSON.toJSONString(Result.ok().put("page", pageInfo)));
    }


    /**
     * 云存储配置信息
     */
//    @RequestMapping("/config")
    @RequiresPermissions("sys:oss:all")
    public void config(RoutingContext routingContext) {
        CloudStorageConfig config = sysConfigService.getConfigObject(KEY, CloudStorageConfig.class);

        doSuccess(routingContext, JSON.toJSONString(Result.ok().put("config", config)));
    }


    /**
     * 保存云存储配置信息
     */
//    @RequestMapping("/saveConfig")
    @RequiresPermissions("sys:oss:all")
    public void saveConfig(RoutingContext routingContext) {
        CloudStorageConfig config = routingContext.getBodyAsJson().mapTo(CloudStorageConfig.class);
        //校验类型
        ValidatorUtils.validateEntity(config);

        if (config.getType() == Constant.CloudService.QINIU.getValue()) {
            //校验七牛数据
            ValidatorUtils.validateEntity(config, QiniuGroup.class);
        } else if (config.getType() == Constant.CloudService.ALIYUN.getValue()) {
            //校验阿里云数据
            ValidatorUtils.validateEntity(config, AliyunGroup.class);
        } else if (config.getType() == Constant.CloudService.QCLOUD.getValue()) {
            //校验腾讯云数据
            ValidatorUtils.validateEntity(config, QcloudGroup.class);
        }


        try {
            sysConfigService.updateValueByKey(KEY, new Gson().toJson(config));
        } catch (Exception e) {
            log.error("保存云存储配置信息异常", e);
            doError(routingContext, JSON.toJSONString(Result.error("保存云存储配置信息")));
        }

        doSuccess(routingContext, JSON.toJSONString(Result.ok()));
    }


    /**
     * 上传文件
     */
//    @RequestMapping("/upload")
    @RequiresPermissions("sys:oss:all")
    public void upload(RoutingContext routingContext) {
        Set<FileUpload> file = routingContext.fileUploads();
        if (file.isEmpty()) {
            throw new RRException("上传文件不能为空");
        }

        //上传文件
//        String url = OSSFactory.build().upload();
        String url = "";

        //保存文件信息
        SysOssEntity ossEntity = new SysOssEntity();
        ossEntity.setUrl(url);
        ossEntity.setCreateDate(new Date());
        try {
            sysOssService.save(ossEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        doSuccess(routingContext, JSON.toJSONString(Result.ok().put("url", url)));
    }


    /**
     * 删除
     */
//    @RequestMapping("/delete")
    @RequiresPermissions("sys:oss:all")
    public void delete(RoutingContext routingContext) {
        try {
            List<Long> ids = Arrays.stream(routingContext.request().params().get("ids").split(",")).mapToLong(NumberUtils::toLong).boxed().collect(Collectors.toList());
            sysOssService.deleteBatch(ids.toArray(new Long[ids.size()]));
        } catch (Exception e) {
            log.error("删除文件异常", e);
            doError(routingContext, JSON.toJSONString(Result.error("删除文件异常")));
        }
        doSuccess(routingContext, JSON.toJSONString(Result.ok()));
    }


}
