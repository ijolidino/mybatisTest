package com.blackmagicwoman.mybatistest.controller;

import com.blackmagicwoman.mybatistest.entity.Dept;
import com.blackmagicwoman.mybatistest.mapper.DeptMapper;
import com.blackmagicwoman.mybatistest.service.DeptService;
import com.blackmagicwoman.mybatistest.service.TestService;
import com.blackmagicwoman.query.QueryAndInsertDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @program: mybatisTest
 * @description: 部门
 * @author: heise
 * @create: 2022-08-06 21:52
 **/
@RestController
@RequestMapping(value = "/dept")
public class DeptController {
    @Autowired
    private TestController testController;
    @Resource
    private DeptService deptService;
    @Resource
    private DeptMapper deptMapper;
    @Resource
    private TestService testService ;
    @Value("${server.port}")
    private String port;
    @Resource(name = "asyncThreadExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    /**
     * 新增
     * @author zhengkai.blog.csdn.net
     * @date 2022/08/06
     **/
    @PostMapping(value = "/insert")
    public void insert(@RequestBody Dept dept){
        testController.queryAndBackUp(1);
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
        String ports=this.port;
        System.out.println("port:"+ports);
        System.out.println(testService.isCanInsert());
        return deptService.selectDeptAndEmp(id);
    }

    @RequestMapping("/selectById?{id}")
    public Dept selectById(@PathVariable int id){
        return deptMapper.selectById(id);
    }
}
