package com.blackmagicwoman.mybatistest.utils;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * @program: mybatisTest
 * @description: 流程Bean'
 * @author: heise
 * @create: 2023-02-05 22:51
 **/
@Component
@Log4j2
public class FlowBeanUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public FlowBeanUtil() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        FlowBeanUtil.applicationContext = applicationContext;
    }

    public static <T> T getBeanByFlowType(String flowType) {
        try {
            Class<FlowProcess> flowProcessClass = FlowProcess.class;
            Map<String, Object> beansWithAnnotation =
                    applicationContext.getBeansWithAnnotation(flowProcessClass);
            Iterator<Map.Entry<String, Object>> iterator =
                    beansWithAnnotation.entrySet().iterator();
            Class clazz;
            FlowProcess flowProcess;
            while (iterator.hasNext()) {
                Map.Entry<String, Object> next = iterator.next();
                clazz = next.getValue().getClass();
                if (!Objects.isNull(clazz) && StringUtils.isNotEmpty(clazz.getName()) && clazz.getName().equals(flowType)) {
                    return (T) applicationContext.getBean(clazz);
                }
            }
            return null;
        } catch (Exception e) {
            log.error("从静态变量ApplicationContext中获取bean异常，异常信息为:{}", (Object) e.getStackTrace());
        }

        return null;
    }
}
