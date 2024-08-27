package com.blackmagicwoman.mybatistest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

/**
 * @program: mybatisTest
 * @description: 事务配置类，不可缺少，尚未知具体作用
 * @author: heise
 * @create: 2022-04-15 20:53
 **/
public class TransactionManagementConfiguration implements TransactionManagementConfigurer {
    @Autowired
    private DataSource dataSource;


    @Override
    public TransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
