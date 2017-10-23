package io.renren.common.xss;

import io.renren.common.exception.RRException;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;

/**
 * SQL过滤
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-04-01 16:16
 */
public class SQLFilter {

    /**
     * SQL注入过滤
     *
     * @param str 待验证的字符串
     */
    public static String sqlInject(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        //去掉'|"|;|\字符
        str = StringUtils.replace(str, "'", "");
        str = StringUtils.replace(str, "\"", "");
        str = StringUtils.replace(str, ";", "");
        str = StringUtils.replace(str, "\\", "");

        //转换成小写
        str = str.toLowerCase();

        //非法字符
        String[] keywords = {"master", "truncate", "insert", "select", "delete", "update", "declare", "alert", "drop"};
        String finalStr = str;
        //判断是否包含非法字符
        return Arrays.stream(keywords).filter(finalStr::contains).findFirst().orElseThrow(() -> new RRException("包含非法字符"));
    }
}
