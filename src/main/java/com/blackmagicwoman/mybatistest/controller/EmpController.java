package com.blackmagicwoman.mybatistest.controller;

import com.blackmagicwoman.mybatistest.entity.Dept;
import com.blackmagicwoman.mybatistest.entity.PmsCategory;
import com.blackmagicwoman.mybatistest.mapper.DeptMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * <p>
 * 员工表 前端控制器
 * </p>
 *
 * @author heise
 * @since 2022-04-15
 */
@Controller
@RequestMapping("/emp")
@Slf4j
public class EmpController {
    @Resource
    private DeptMapper deptMapper;
    public void empOther(PmsCategory pmsCategory){
        log.info("测试进入其他类EmpController");
        pmsCategory.setName("测试进入其他类EmpController");
    }

    @Transactional(propagation = Propagation.NESTED)
    public void test1() {
        Dept dept = new Dept();
        dept.setDeptNo(9);
        dept.setLoc("test1第一个事务测试方法已更新");
        deptMapper.update(dept);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void test2() {
        Dept dept = new Dept();
        dept.setDeptNo(10);
        dept.setLoc("test1第二个事务测试方法已更新");
        int i=1/0;
        deptMapper.update(dept);
    }
    @Transactional(propagation = Propagation.NESTED)
    public void test3() {
        Dept dept = new Dept();
        dept.setDeptNo(30);
        dept.setLoc("test1第三个事务测试方法已更新");
        deptMapper.update(dept);
    }

    public void test4() {
        Dept dept = new Dept();
        dept.setDeptNo(40);
        dept.setLoc("test1第四个事务测试方法已更新");
        deptMapper.update(dept);
    }
}
