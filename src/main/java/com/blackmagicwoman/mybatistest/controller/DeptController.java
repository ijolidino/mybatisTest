package com.blackmagicwoman.mybatistest.controller;

import com.blackmagicwoman.mybatistest.entity.Dept;
import com.blackmagicwoman.mybatistest.service.DeptService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @program: mybatisTest
 * @description: 部门
 * @author: Fuwen
 * @create: 2022-08-06 21:52
 **/
@RestController
@RequestMapping(value = "/dept")
public class DeptController {

    @Resource
    private DeptService deptService;

    /**
     * 新增
     * @author zhengkai.blog.csdn.net
     * @date 2022/08/06
     **/
    @RequestMapping("/insert")
    public void insert(Dept dept){
         deptService.insert(dept);
    }

    /**
     * 刪除
     * @author zhengkai.blog.csdn.net
     * @date 2022/08/06
     **/
    @RequestMapping("/delete")
    public void delete(int id){
         deptService.delete(id);
    }

    /**
     * 更新
     * @author zhengkai.blog.csdn.net
     * @date 2022/08/06
     **/
    @RequestMapping("/update")
    public void update(Dept dept){
         deptService.update(dept);
    }

    /**
     * 查询 根据主键 id 查询
     * @author zhengkai.blog.csdn.net
     * @date 2022/08/06
     **/
    @RequestMapping("/load")
    public Object load(int id){
        return deptService.load(id);
    }

    /**
     * 查询 分页查询
     * @author zhengkai.blog.csdn.net
     * @date 2022/08/06
     **/
    @RequestMapping("/pageList")
    public Map<String, Object> pageList(@RequestParam(required = false, defaultValue = "0") int offset,
                                        @RequestParam(required = false, defaultValue = "10") int pagesize) {
        return deptService.pageList(offset, pagesize);
    }

    @RequestMapping("/selectdeptWithEmp/{id}")
    public Dept selectdeptWithEmp(@PathVariable int id){
        return deptService.selectDeptAndEmp(id);
    }
}
