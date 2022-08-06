package com.blackmagicwoman.mybatistest.service.impl;

import com.blackmagicwoman.mybatistest.entity.Dept;
import com.blackmagicwoman.mybatistest.mapper.DeptMapper;
import com.blackmagicwoman.mybatistest.service.DeptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: mybatisTest
 * @description: 部门
 * @author: Fuwen
 * @create: 2022-08-06 21:54
 **/
@Service
public class DeptServiceImpl implements DeptService {

    @Resource
    private DeptMapper deptMapper;


    @Override
    public void insert(Dept dept) {
        deptMapper.insert(dept);
    }


    @Override
    public void delete(int id) {
        int ret = deptMapper.delete(id);
    }


    @Override
    public void update(Dept dept) {
        int ret = deptMapper.update(dept);
    }


    @Override
    public Dept load(int id) {
        return deptMapper.load(id);
    }


    @Override
    public Map<String,Object> pageList(int offset, int pagesize) {

        List<Dept> pageList = deptMapper.pageList(offset, pagesize);
        int totalCount = deptMapper.pageListCount(offset, pagesize);

        // result
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("pageList", pageList);
        result.put("totalCount", totalCount);

        return result;
    }

    @Override
    public Dept selectDeptAndEmp(int id) {
        return deptMapper.selectDeptAndEmp(id);
    }

}
