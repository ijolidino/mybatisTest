package com.blackmagicwoman.mybatistest.controller;

import com.blackmagicwoman.mybatistest.entity.Emp;
import com.blackmagicwoman.mybatistest.entity.EmpEntity;
import com.blackmagicwoman.mybatistest.entity.PmsCategory;
import com.blackmagicwoman.mybatistest.mapper.PmsCategoryMapper;
import com.blackmagicwoman.mybatistest.service.TestService;
import org.apache.ibatis.cursor.Cursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: mybatisTest
 * @description: 控制层
 * @author: Fuwen
 * @create: 2022-04-15 21:01
 **/

@RestController
@RequestMapping("/demoproject/test")
public class TestController {

    @Autowired
    private TestService testService ;

    @Autowired
    private PmsCategoryMapper pmsCategoryMapper;
    @RequestMapping(value = "/get/{empNo}",method = RequestMethod.GET)
    public EmpEntity test(@PathVariable Integer empNo){
        System.out.println("empNo:" + empNo);
        return testService.getById(empNo);
    }

    @RequestMapping(value = "batchInsert",method = RequestMethod.POST)
    public int batchInsert(@PathVariable List<Emp> emps){

        return 1;
    }

    @RequestMapping(value = "/getEmp/{empNo}",method = RequestMethod.POST)
    public EmpEntity getBody(@PathVariable Integer empNo){
        return testService.getById(empNo);
    }
    @Transactional
    @RequestMapping(value = "getCursor/{limit}" ,method = RequestMethod.POST)
    public void getCursor(@PathVariable Integer limit){
        Cursor<PmsCategory> cursor = pmsCategoryMapper.getCursor(limit);
        cursor.forEach(pmsCategory -> {
            System.out.println(pmsCategory.getCatId());
        });
        System.out.println("----------");
//添加新分支
    }
}
