package io.renren.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project renren-fast
 * @Package io.renren.common.utils
 * @Description TODO(描述)
 * @create 2017/7/28-07:46
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    @SuppressWarnings("all")
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        if (applicationContext != null) {
            throw new IllegalStateException("ApplicationContextHolder already holded 'applicationContext'.");
        }
        applicationContext = context;
    }

    private static ApplicationContext getApplicationContext() {
        return Optional.ofNullable(applicationContext).orElseThrow(() ->
                new IllegalStateException("'applicationContext' property is null,ApplicationContextHolder not yet init."));
    }

    public static Object getBean(String beanName) {
        return getApplicationContext().getBean(beanName);
    }

    public static void cleanHolder() {
        applicationContext = null;
    }
}
