package com.blackmagicwoman.mybatistest.service;

import com.blackmagicwoman.mybatistest.dao.TestDao;
import com.blackmagicwoman.mybatistest.entity.EmpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: mybatisTest
 * @description: service
 * @author: Fuwen
 * @create: 2022-04-15 21:02
 **/
@Service
public class TestService {
    @Autowired
    private TestDao testDao ;

    public EmpEntity getById(Integer empNo){
        List<String> strings = new ArrayList<>();
        strings.add("SMITH");
        strings.add("ALLEN");
        strings.add("WARD");
        List<EmpEntity> empEntities = testDao.seleteNotIn(strings);
        //int i = testDao.deleteNotIn(strings);
        System.out.println("success");
        return testDao.getById(empNo);
    }

    public EmpEntity getCursor(Integer limit) {
        return testDao.getCursor(limit);
    }
}
