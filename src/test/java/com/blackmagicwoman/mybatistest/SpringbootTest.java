package com.blackmagicwoman.mybatistest;

import com.blackmagicwoman.mybatistest.entity.PmsCategory;
import com.blackmagicwoman.mybatistest.mapper.PmsCategoryMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

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
}
