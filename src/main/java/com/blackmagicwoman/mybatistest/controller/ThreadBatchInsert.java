package com.blackmagicwoman.mybatistest.controller;

import com.blackmagicwoman.mybatistest.entity.PmsCategory;
import com.blackmagicwoman.mybatistest.mapper.PmsCategoryMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: mybatisTest
 * @description: 批量插入
 * @author: Fuwen
 * @create: 2022-06-02 22:22
 **/
@Service
@Data
public class ThreadBatchInsert implements Runnable{
    @Autowired
    private PmsCategoryMapper pmsCategoryMapper;
    private List<PmsCategory> pmsCategoryList;
    @Override
    public void run() {
        for (PmsCategory pmsCategory:pmsCategoryList){
            pmsCategoryMapper.insert(pmsCategory);
        }
        System.out.println(Thread.currentThread().getName()+"插入成功");
    }

    void insert(List<PmsCategory> pmsCategoryList){
        pmsCategoryMapper.batchInsert(pmsCategoryList);
    }
}
