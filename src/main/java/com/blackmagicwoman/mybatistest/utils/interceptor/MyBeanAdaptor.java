package com.blackmagicwoman.mybatistest.utils.interceptor;

import com.github.pagehelper.PageInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: mybatisTest
 * @description: 适配器
 * @author: heise
 * @create: 2024-01-20 16:26
 **/
@Component
@Slf4j
public class MyBeanAdaptor implements ApplicationListener<ContextRefreshedEvent> {
    @Resource
    private List<SqlSessionFactory> sqlSessionFactoryList;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            List<Interceptor> interceptors = sqlSessionFactory.getConfiguration().getInterceptors();
            log.info("注册拦截器前，构造工厂添加自定义拦截器前，顺序为：【{}】", interceptors);
            sqlSessionFactory.getConfiguration().addInterceptor(new MyInterceptor());
            sqlSessionFactory.getConfiguration().addInterceptor(new PageInterceptor());
            //interceptors.add(new MyInterceptor());
            log.info("注册拦截器后 ，构造工厂添加自定义拦截器后，顺序为：【{}】", interceptors);
        }
    }
}
