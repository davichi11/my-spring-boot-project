package io.renren.modules.job.task;

import com.google.common.collect.Maps;
import io.renren.common.utils.DateUtils;
import io.renren.modules.sys.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project renren-fast
 * @Package io.renren.modules.job.task
 * @Description TODO(描述)
 * @create 2017/10/24-09:40
 */
@Slf4j
@Component("delete_log")
public class DeleteLogTask {
    @Autowired
    private SysLogService logService;

    /**
     * 定时删除日志任务
     *
     * @param params
     */
    @SuppressWarnings("unchecked")
    public void deleteLog(String params) {
        Map<String, Object> map = Maps.newHashMap();
        LocalDate beginDate = LocalDate.now().minusMonths(1);
        LocalDate endDate = LocalDate.now();
        if (StringUtils.contains(params, ",")) {
            String[] strings = params.split(",");
            //提取开始时间并转换
            beginDate = Arrays.stream(strings).filter(s -> s.toUpperCase().contains("BEGINDATE"))
                    .map(this::convertToLocalDate).findFirst().orElse(LocalDate.now().minusMonths(1));
            //提取结束时间并转换
            endDate = Arrays.stream(strings).filter(s -> s.toUpperCase().contains("ENDDATE"))
                    .map(this::convertToLocalDate).findFirst().orElse(LocalDate.now());
        }
        map.put("beginDate", beginDate);
        map.put("endDate", endDate);
        try {
            logService.deleteByParams(map);
        } catch (Exception e) {
            log.error("删除日志异常", e);
            logService.saveErrorLog(params);
        }
    }

    /**
     * 转换日期时间
     *
     * @param date
     * @return
     */
    private LocalDate convertToLocalDate(String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        String separator = "";
        if (date.contains(":")) {
            separator = ":";
        }
        if (date.contains("=")) {
            separator = "=";
        }
        return DateUtils.stringFormatDate(StringUtils.substringAfter(date, separator));
    }

}
