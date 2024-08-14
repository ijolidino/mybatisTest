package com.blackmagicwoman.mybatistest.controller;

import cn.hutool.core.io.FileUtil;
import com.blackmagicwoman.file.input.entity.PmsCategoryAmtTest;
import com.blackmagicwoman.fileInputAndGenerate.BaseConstants;
import com.blackmagicwoman.fileInputAndGenerate.BathFileWriter;
import com.blackmagicwoman.mybatistest.entity.Emp;
import com.blackmagicwoman.mybatistest.entity.EmpEntity;
import com.blackmagicwoman.mybatistest.entity.PmsCategory;
import com.blackmagicwoman.mybatistest.entity.PmsCategory2;
import com.blackmagicwoman.mybatistest.mapper.PmsCategoryAmtTestMapper;
import com.blackmagicwoman.mybatistest.mapper.PmsCategoryMapper;
import com.blackmagicwoman.mybatistest.service.TestService;
import com.blackmagicwoman.query.QueryAndInsertDB;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cursor.Cursor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
 * @author: heise
 * @create: 2022-04-15 21:01
 **/

@RestController
@RequestMapping("/demoproject/test")
@Slf4j
public class TestController {
    @Resource
    private DeptController deptController;
    @Resource
    private TestService testService;
    @Resource
    private ThreadBatchInsert batchInsert1;
    @Resource
    private PmsCategoryMapper pmsCategoryMapper;
    @Resource
    private EmpController empController;
    @Resource
    private PmsCategoryAmtTestMapper pmsCategoryAmtTestMapper;

    @GetMapping(value = "/get/{empNo}")
    public EmpEntity test(@PathVariable Integer empNo) {
        deptController.selectdeptWithEmp(1);
        System.out.println("empNo:" + empNo);
        System.out.println(testService.isCanInsert());
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
        log.info("pmsCategory:{}",pmsCategory);
        otherMethod(pmsCategory);
        System.out.println("------------");
    }

    private void otherMethod(PmsCategory pmsCategory) {
        log.info("进入第二个方法");
        pmsCategory.setName("进入第二个方法");
        empController.empOther(pmsCategory);
        log.info(pmsCategory.toString());
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

    @RequestMapping(value ="/batch/writeAllTempFile",method = RequestMethod.GET)
    public void GeneratorTempFile() throws IOException {
        File file = new File("D:\\javaTest\\bigFile");
        if (!file.exists()){
            file.mkdirs();
        }
        File bigFile = new File("D:\\javaTest\\bigFile\\pmsCategory.DAT");
        if (bigFile.exists()){
            bigFile.deleteOnExit();
        }
        boolean newFile = bigFile.createNewFile();
        try (FileOutputStream fileOutputStream=new FileOutputStream(bigFile);
             OutputStreamWriter outputStreamWriter= new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
             BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)){
                for (long i=0;i<20000000;i++){
                    String s="";
                    for (int j=0;j<21;j++){
                        s+=i+"↑";
                    }
                    bufferedWriter.write(s);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
        }
    }

    @RequestMapping(value ="/batch/analysisBigFile",method = RequestMethod.GET)
    public void AnalysisBigFile() throws IOException {
        File fileSource = new File("D:\\javaTest\\bigFile\\pmsCategory.DAT");
        File file = new File("D:\\javaTest\\bigFile\\splitPath");
        if (!file.exists()){
            file.mkdirs();
        }
        List<File> files= new ArrayList<>();
        for (long i=0;i<20;i++){
            File bigFile = new File("D:\\javaTest\\bigFile\\splitPath\\pmsCategory"+i+".DAT");
            if(bigFile.exists()){
                bigFile.deleteOnExit();
            }
            boolean newFile = bigFile.createNewFile();
            files.add(bigFile);
        }
        try (FileInputStream fileInputStream=new FileInputStream(fileSource);
             InputStreamReader inputStreamReader= new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)){
            for (File file1 : files) {
                try (FileOutputStream fileOutputStream =new FileOutputStream(file1,true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream,StandardCharsets.UTF_8);
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)){
                    for (long i = 1; i <= 1000000; i++) {
                        String readLine = bufferedReader.readLine();
                        bufferedWriter.write(readLine);
                        bufferedWriter.newLine();
                        if (i%10000==0){
                            bufferedWriter.flush();
                        }
                        if (i%100000==0){
                            log.info("文件{}刷了{}条",file1.getName(),i);
                        }
                    }
                }
            }
        }
    }

    @RequestMapping(value ="/batch/analysisBigFileToDataBase",method = RequestMethod.GET)
    public void AnalysisBigFileToDataBase() throws IOException {
        File fileSource = new File("D:\\javaTest\\bigFile\\pmsCategory.DAT");
        File file = new File("D:\\javaTest\\bigFile\\splitPath");
        if (!file.exists()){
            file.mkdirs();
        }
        List<File> files= new ArrayList<>();
        for (long i=0;i<20;i++){
            File bigFile = new File("D:\\javaTest\\bigFile\\splitPath\\pmsCategory"+i+".DAT");
            files.add(bigFile);
        }
        for (File file1 : files) {
            try (FileInputStream fileInputStream =new FileInputStream(file1);
                 InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream,StandardCharsets.UTF_8);
                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader)){
                String readLine;
                List<PmsCategoryAmtTest> pmsCategoryAmtTests = new ArrayList<>();
                while (StringUtils.isNotEmpty(readLine=bufferedReader.readLine())){
                    String[] split = readLine.split("↑");
                    PmsCategoryAmtTest pmsCategoryAmtTest = new PmsCategoryAmtTest();
                    pmsCategoryAmtTest.setCatId(split[0]);
                    pmsCategoryAmtTest.setAmt0(split[1]);
                    pmsCategoryAmtTest.setAmt1(split[2]);
                    pmsCategoryAmtTest.setAmt2(split[3]);
                    pmsCategoryAmtTest.setAmt3(split[4]);
                    pmsCategoryAmtTest.setAmt4(split[5]);
                    pmsCategoryAmtTest.setAmt5(split[6]);
                    pmsCategoryAmtTest.setAmt6(split[7]);
                    pmsCategoryAmtTest.setAmt7(split[8]);
                    pmsCategoryAmtTest.setAmt8(split[9]);
                    pmsCategoryAmtTest.setAmt9(split[10]);
                    pmsCategoryAmtTest.setAmt10(split[11]);
                    pmsCategoryAmtTest.setAmt11(split[12]);
                    pmsCategoryAmtTest.setAmt12(split[13]);
                    pmsCategoryAmtTest.setAmt13(split[14]);
                    pmsCategoryAmtTest.setAmt14(split[15]);
                    pmsCategoryAmtTest.setAmt15(split[16]);
                    pmsCategoryAmtTest.setAmt16(split[17]);
                    pmsCategoryAmtTest.setAmt17(split[18]);
                    pmsCategoryAmtTest.setAmt18(split[19]);
                    pmsCategoryAmtTest.setAmt19(split[20]);
                    pmsCategoryAmtTests.add(pmsCategoryAmtTest);
                    if (pmsCategoryAmtTests.size()>1000){
                        pmsCategoryAmtTestMapper.batchInsert(pmsCategoryAmtTests);
                        pmsCategoryAmtTests.clear();
                    }
                }
            }
        }
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


    @RequestMapping("/queryAndBackUp/{id}")
    public void queryAndBackUp(@PathVariable int id){
        QueryAndInsertDB<PmsCategory, PmsCategory, Object> db = new QueryAndInsertDB<>((p) -> pmsCategoryMapper.loadAll(p),
                new PmsCategory(),
                1000,
                a -> pmsCategoryMapper.insert((PmsCategory) a),
                o -> null);
        db.startInsert();
    }
}
