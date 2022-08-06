package com.blackmagicwoman.mybatistest.service;

import com.blackmagicwoman.mybatistest.entity.Dept;

import java.util.Map;

/**
 * @program: mybatisTest
 * @description: 部门
 * @author: Fuwen
 * @create: 2022-08-06 21:53
 **/
public interface DeptService {

    /**
     * 新增
     */
    public void insert(Dept dept);

    /**
     * 删除
     */
    public void delete(int id);

    /**
     * 更新
     */
    public void update(Dept dept);

    /**
     * 根据主键 id 查询
     */
    public Dept load(int id);

    /**
     * 分页查询
     */
    public Map<String,Object> pageList(int offset, int pagesize);

    Dept selectDeptAndEmp(int id);
}
