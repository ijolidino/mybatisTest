package com.blackmagicwoman.mybatistest;

import com.blackmagicwoman.fileInputAndGenerate.BaseConstants;
import com.blackmagicwoman.fileInputAndGenerate.LimitReaderCursor;
import com.blackmagicwoman.mybatistest.entity.PmsCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.nio.file.Files;

/**
 * @program: mybatisTest
 * @description: 分页流式读取
 * @author: heise
 * @create: 2022-09-07 20:27
 **/
@Slf4j
public class FileReadByCursorTest {

    @Test
    public void ReadTest() {
        //读取的文件
        File file = new File("/pmsCategory.DAT");
        try(BufferedReader bufferedReader=new BufferedReader(new FileReader(file));
            LimitReaderCursor<PmsCategory> limitReaderCursor=new LimitReaderCursor<>(bufferedReader
                    //分割符
            , BaseConstants.DEFAULT_SEPARATOR
                    //每次读取的条数
            ,500
                    //赋值转成对象
            ,(line,split)->{
                String[] split1 = line.split(split);
                PmsCategory pmsCategory = new PmsCategory();
                pmsCategory.setCatId(Long.valueOf(split1[0]));
                pmsCategory.setName(split1[1]);
                pmsCategory.setParentCid(Long.valueOf(split1[2]));
                pmsCategory.setCatLevel(Integer.valueOf(split1[3]));
                pmsCategory.setShowStatus(Integer.valueOf(split1[4]));
                pmsCategory.setSort(Integer.valueOf(split1[5]));
                pmsCategory.setIcon(split1[6]);
                pmsCategory.setProductUnit(split1[7]);
                pmsCategory.setProductCount(Integer.valueOf(split1[8]));
                return pmsCategory;
            })){
            limitReaderCursor.forEach(l->{
                for (PmsCategory pmsCategory:l){
                    //处理文件
                    System.out.println(pmsCategory);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
