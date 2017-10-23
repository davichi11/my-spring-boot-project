package io.renren.common.validator;

import io.renren.common.exception.RRException;
import org.apache.commons.lang.StringUtils;

/**
 * 数据校验
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-23 15:50
 */
public class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new RRException(message);
        }
    }
}
