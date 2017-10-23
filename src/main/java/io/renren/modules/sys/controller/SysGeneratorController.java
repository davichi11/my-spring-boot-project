package io.renren.modules.sys.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.GenUtils;
import io.renren.common.utils.Result;
import io.renren.modules.sys.service.SysGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project renren-fast
 * @Package io.renren.modules.sys.controller
 * @Description TODO(描述)
 * @create 2017/8/13-21:24
 */
@Slf4j
@RestController
@RequestMapping("/sys/generator")
public class SysGeneratorController {
    @Autowired
    private SysGeneratorService sysGeneratorService;

    /**
     * 列表数据
     *
     * @param params
     * @return
     */
    @GetMapping("/list")
    public Result list(@RequestParam Map<String, Object> params) {

        PageInfo<Map<String, Object>> pageInfo;
        try {
            pageInfo = PageHelper.startPage(MapUtils.getInteger(params, "page"),
                    MapUtils.getInteger(params, "limit")).doSelectPageInfo(() -> sysGeneratorService.queryList(params));
        } catch (Exception e) {
            log.error("查询异常", e);
            return Result.error("查询异常");
        }
        return Result.ok().put("page", pageInfo);

    }

    /**
     * 生成代码
     */
    @GetMapping("/code")
    public Result code(HttpServletRequest request) throws IOException {
        String[] tableNames = new String[]{};
        String tables = request.getParameter("tables");
        tableNames = JSON.parseArray(tables).toArray(tableNames);

        byte[] data;
        try {
            data = sysGeneratorService.generatorCode(tableNames);
        } catch (Exception e) {
            log.error("生成异常", e);
            return Result.error("生成失败");
        }
        String fileName = "gender" + DateUtils.formatDateTime(LocalDateTime.now(), null) + ".zip";
        File file = new File(GenUtils.getClasspath() + "code/" + fileName);
        try {
            FileUtils.writeByteArrayToFile(file, data);
        } catch (IOException e) {
            log.error("文件写入异常", e);
            return Result.error("生成失败,文件写入异常");
        }
        return Result.ok();
//        response.reset();
//        response.setHeader("Content-Disposition", "attachment; filename=\"renren.zip\"");
//        response.addHeader("Content-Length", "" + data.length);
//        response.setContentType("application/octet-stream; charset=UTF-8");
//
//        IOUtils.write(data, response.getOutputStream());
    }
}
