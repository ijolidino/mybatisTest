package com.blackmagicwoman.mybatistest.utils.interceptor;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @program: mybatisTest
 * @description: SQL拦截器
 * @author: heise
 * @create: 2024-01-20 15:56
 **/
@Slf4j
@Intercepts({
        @Signature(method = "query",
                type = Executor.class,
                args = {MappedStatement.class,
                        Object.class,
                        RowBounds.class,
                        ResultHandler.class, CacheKey.class,
                        BoundSql.class}
        ),
        @Signature(method = "query",
                type = Executor.class,
                args = {MappedStatement.class,
                        Object.class, RowBounds.class,
                        ResultHandler.class})})
public class MyInterceptor implements Interceptor {
    //可注入redis获取redis的内容
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //1、此处获取包路径，根据不同类的包路径下是否需要拦截
        Optional<StackTraceElement> any = Arrays.stream(Thread.currentThread().getStackTrace()).filter(i -> i.getClassName().contains(".java8.")).findAny();
        if (any.isPresent()) {
            return invocation.proceed();
        }
        MappedStatement mappedStatement = null;
        Object object = null;
        RowBounds rowBounds = null;
        ResultHandler resultHandler = null;
        Executor executor = null;
        CacheKey cacheKey = null;
        BoundSql boundSql = null;
        try {
            Object[] args = invocation.getArgs();
            mappedStatement = (MappedStatement) args[0];
            object = args[1];
            rowBounds = (RowBounds) args[2];
            resultHandler = (ResultHandler) args[3];
            executor = (Executor) invocation.getTarget();
            if (args.length == 4) {
                boundSql = mappedStatement.getBoundSql(object);
                cacheKey = executor.createCacheKey(mappedStatement, object, rowBounds, boundSql);

            } else {
                cacheKey = (CacheKey) args[4];
                boundSql = (BoundSql) args[5];
            }
            String lowerCaseSql = boundSql.getSql().toLowerCase();
            List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
            if (lowerCaseSql.contains("limit") || CollUtil.isNotEmpty(parameterMappings)) {
                log.info("原始SQL包含限制，不进行拦截或入参不为空不进行拦截，参数为：【{}】",parameterMappings);
                return invocation.proceed();
            }
            Page<Object> localPage = PageHelper.getLocalPage();
            if (Objects.nonNull(localPage) && localPage.getEndRow() > 0 && localPage.getEndRow() < 5000) {
                log.info("原始SQL包含分页处理器分页且分页小于1000，分页信息为：【{}】，执行原始SQL，原始SQL为：【{}】", localPage, lowerCaseSql);
                return invocation.proceed();
            }
            boolean canFilter = false;
            //2、此处获取需要拦截的黑名单，直接放到缓存或者应用重启时放到内存中，如果涉及表包含在黑名单中，则会拦截且强行增加分页信息
            PageHelper.clearPage();
            String newSql = "select * from (" + lowerCaseSql.replace(";", "") + ")limit 5000";
            log.info("新SQL为【{}】", newSql);
            Field sql = boundSql.getClass().getDeclaredField("sql");
            sql.setAccessible(true);
            sql.set(boundSql, newSql);
            sql.setAccessible(false);
        } catch (Exception e) {
            log.error("拦截器报错，拦截不成功，执行原始SQL");
            log.error(Arrays.toString(e.getStackTrace()));
            return invocation.proceed();
        }
        return invocation.proceed();
    }
}
