package com.blackmagicwoman.mybatistest;

import com.blackmagicwoman.mybatistest.entity.PmsCategory;
import com.blackmagicwoman.mybatistest.mapper.PmsCategoryMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @program: mybatisTest
 * @description: 项目测试
 * @author: Fuwen
 * @create: 2022-09-13 22:19
 **/
@SpringBootTest(classes = MybatisTestApplication.class)
@RunWith(SpringRunner.class)
public class SpringbootTest {

    //private static final Logger logger = (Logger) LoggerFactory.getLogger(SpringbootTest.class);

    @Autowired
    private PmsCategoryMapper pmsCategoryMapper;

    @Test
    public void testApi(){
        List<PmsCategory> query = pmsCategoryMapper.query(new PmsCategory());
        System.out.println(query.toString());
    }

    @Test
    public void TestPartition(){
        HashMap<String, Object> map = new HashMap<>();
        List<String> strings = Arrays.asList("手机", "家用电器");
        map.put("names",strings);
        List<PmsCategory> pmsCategories = pmsCategoryMapper.queryListByCondFromMap(map);
        List<PmsCategory> query = pmsCategoryMapper.query(new PmsCategory());
        List<PmsCategory> collect1 = query.stream().filter(p -> p.getShowStatus() != null).collect(Collectors.toList());
        Map<Boolean, List<PmsCategory>> collect = collect1.stream().collect(Collectors.partitioningBy(p -> p.getShowStatus().equals(1)));
        System.out.println(collect);
        Map<Long, List<PmsCategory>> collect2 = collect1.stream().collect(Collectors.groupingBy(PmsCategory::getParentCid));
        System.out.println(collect2);
        Map<Long, Map<Long, List<PmsCategory>>> collect3 = collect1.stream().collect(Collectors.groupingBy(PmsCategory::getParentCid, Collectors.groupingBy(PmsCategory::getCatId)));
        System.out.println(collect3);
        System.out.println("今日休息，想谈恋爱");
    }
}
