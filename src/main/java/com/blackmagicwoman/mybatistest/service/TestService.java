package com.blackmagicwoman.mybatistest.service;

import com.blackmagicwoman.mybatistest.dao.TestDao;
import com.blackmagicwoman.mybatistest.entity.Dept;
import com.blackmagicwoman.mybatistest.entity.EmpEntity;
import com.blackmagicwoman.mybatistest.mapper.DeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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

    private boolean canInsert=false;
    @Resource
    private DeptMapper deptMapper;
    public EmpEntity getById(Integer empNo){
        List<String> strings = new ArrayList<>();
        strings.add("SMITH");
        strings.add("ALLEN");
        strings.add("WARD");
        List<EmpEntity> empEntities = testDao.seleteNotIn(strings);
        //int i = testDao.deleteNotIn(strings);
        System.out.println("success");
        Map<String, Function> functionMap = new HashMap<>();
        functionMap.put("1",(p)->deptMapper.load((Integer) p));
        functionMap.put("2",(p)->deptMapper.loadAll((Dept) p));
        functionMap.put("3",(p)->deptMapper.insert((Dept) p));
        if (functionMap.containsKey("1")){
            Dept apply = (Dept)functionMap.get("1").apply(10);
            System.out.println(apply.toString());
        }
        if (functionMap.containsKey("2")){
            List<Dept> apply = (List<Dept>)functionMap.get("2").apply(new Dept());
            System.out.println(apply);
        }
        if (functionMap.containsKey("3")){
            Dept dept = new Dept();
            dept.setDeptNo(50);
            dept.setDName("您好");
            dept.setLoc("佛山");
            Integer apply = (Integer)functionMap.get("3").apply(dept);
            System.out.println("插入了"+apply+"条");
        }
        return testDao.getById(empNo);
    }

    public EmpEntity getCursor(Integer limit) {
        return testDao.getCursor(limit);
    }

    public boolean isCanInsert() {
        return canInsert;
    }

    public void setCanInsert(boolean canInsert) {
        this.canInsert = canInsert;
    }
}
