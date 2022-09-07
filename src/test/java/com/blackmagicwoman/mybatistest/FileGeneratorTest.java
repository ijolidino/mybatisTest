package com.blackmagicwoman.mybatistest;

import cn.hutool.core.io.FileUtil;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * @program: mybatisTest
 * @description: 生成文件的测试
 * @author: Fuwen
 * @create: 2022-09-07 20:18
 **/
public class FileGeneratorTest {

    @Test
    public void TestGenerator(){
        createFile();

    }

    private void createFile() {
        File file = new File("/pmsCategory");
        if (FileUtil.exist(file)){
            FileUtil.del(file);
        }
        FileUtil.touch(file);
    }
}
