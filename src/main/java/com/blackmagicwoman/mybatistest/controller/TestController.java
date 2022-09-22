package com.blackmagicwoman.mybatistest.controller;

import cn.hutool.core.io.FileUtil;
import com.blackmagicwoman.fileInputAndGenerate.BaseConstants;
import com.blackmagicwoman.fileInputAndGenerate.BathFileWriter;
import com.blackmagicwoman.fileInputAndGenerate.IBean2LineStrHandler;
import com.blackmagicwoman.mybatistest.entity.Emp;
import com.blackmagicwoman.mybatistest.entity.EmpEntity;
import com.blackmagicwoman.mybatistest.entity.PmsCategory;
import com.blackmagicwoman.mybatistest.mapper.PmsCategoryMapper;
import com.blackmagicwoman.mybatistest.service.TestService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cursor.Cursor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: mybatisTest
 * @description: 控制层
 * @author: Fuwen
 * @create: 2022-04-15 21:01
 **/

@RestController
@RequestMapping("/demoproject/test")
@Slf4j
public class TestController {

    @Autowired
    private TestService testService ;
    @Autowired
    private ThreadBatchInsert batchInsert1;
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
    //@Transactional
    @RequestMapping(value = "getCursor/{limit}" ,method = RequestMethod.POST)
    public void getCursor(@PathVariable Integer limit){
        Cursor<PmsCategory> cursor = pmsCategoryMapper.getCursor(limit);
        cursor.forEach(pmsCategory -> {
            System.out.println(pmsCategory.getCatId());
        });
        System.out.println("----------");
//添加新分支
        //添加新分支2
    }
    @RequestMapping(value = "getPage/{pageNum}/{pageSize}" ,method = RequestMethod.POST)
    public PageInfo<PmsCategory> getPage(@PathVariable Integer pageNum,@PathVariable Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<PmsCategory> pmsCategories = pmsCategoryMapper.queryByPage(pageNum, pageSize);
        PageInfo<PmsCategory> pmsCategoryPageInfo = new PageInfo<>(pmsCategories);
        System.out.println("------------");
        return pmsCategoryPageInfo;
    }

    @RequestMapping(value = "jsonRequest" ,method = RequestMethod.POST)
    public void jsonRequest(@RequestBody PmsCategory pmsCategory){

        System.out.println("------------");
    }

    @RequestMapping(value = "batchInsert1" ,method = RequestMethod.POST)
    public void threadTest(){
        int count=0;
        List<List<PmsCategory>> ps = new ArrayList<>();
        List<PmsCategory> pmsCategories=new ArrayList<>();
        for (int i=1500;i<1600;i++){
            count++;
            if (count%10==0){
                ps.add(pmsCategories);
                pmsCategories= new ArrayList<>();
            }
            PmsCategory p = new PmsCategory();
            p.setName("tomato"+i);
            pmsCategories.add(p);

        }

        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4, 100, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(200), new ThreadTest.NameTreadFactory(), new ThreadTest.MyIgnorePolicy());
        for (List<PmsCategory> pms :ps) {
//            for (PmsCategory pmsCategory:pmsCategories){
//                pmsCategoryMapper.insert(pmsCategory);
//            }
            batchInsert1.setPmsCategoryList(pms);
            executor.execute(batchInsert1);
        }
        System.out.println("------------");
    }

    @RequestMapping(value = "/batch/writeAllPmsCategory",method = RequestMethod.GET)
    public void TestGenerator(){
        PmsCategory pmsCategory = new PmsCategory();

        BathFileWriter bathFileWriter = new BathFileWriter<>("/pmsCategory.DAT",
                (p) -> pmsCategoryMapper.query(p),
                pmsCategory,
                1000,
                PmsCategory::toString,
                BaseConstants.DEFAULT_CHARACTER_SET
                );
        createFile();
        bathFileWriter.startQueryAndWrite();
    }

    private void createFile() {
        File file = new File("/pmsCategory");
        if (FileUtil.exist(file)){
            FileUtil.del(file);
        }
        FileUtil.touch(file);
    }

    @RequestMapping(value = "/query/queryByMap",method = RequestMethod.GET)
    public void queryByMap(){
        log.info("开始日志");
        HashMap<String, Object> map = new HashMap<>();
        List<String> strings = Arrays.asList("手机", "家用电器");
        String [] strings1={"手机", "家用电器"};
        map.put("names",strings1);
        List<PmsCategory> pmsCategories = pmsCategoryMapper.queryListByCondFromMap(map);
        System.out.println(pmsCategories);
        log.info(pmsCategories.toString());
        //rocketmq太难了
    }
}
