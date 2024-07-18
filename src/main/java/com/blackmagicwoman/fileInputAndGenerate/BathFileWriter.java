package com.blackmagicwoman.fileInputAndGenerate;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.function.Function;

/**
 * @program: mybatisTest
 * @description: 基础变量
 * @author: heise
 * @create: 2022-09-07 20:10
 * 读数据写文件处理类
 * 从数据库读取数据
 * 分页读分批写，避免内存溢出
 */
@Data
@Slf4j
public class BathFileWriter<T, R> {

    /**
     * 保存文件名（带路径）
     */
    private String fileName;

    /**
     * 查询数据的函数
     */
    private Function<T, List<R>> mapperFun;

    /**
     * 查询数据的参数
     */
    private T param;

    /**
     * 页大小
     */
    private Integer pageSize = 1000;

    /**
     * bean转换到一行字符串的处理器
     */
    private IBean2LineStrHandler<R> iBean2LineStrHandler;

    /**
     * 文件编码
     */
    private String fileCharacterSet = BaseConstants.DEFAULT_CHARACTER_SET;

    /**
     * 构造函数
     *
     * @param fileName             文件名 -全路径
     * @param mapperFun            数据来源
     * @param param                参数
     * @param pageSize             页大小（默认值1000）
     * @param iBean2LineStrHandler bean转换为字符串处理器
     * @param fileCharacterSet     文件字符集编码 默认-utf8
     */
    public BathFileWriter(String fileName,
                          Function<T, List<R>> mapperFun,
                          T param,
                          Integer pageSize,
                          IBean2LineStrHandler<R> iBean2LineStrHandler,
                          String fileCharacterSet) {
        paramCheck(fileName, mapperFun, iBean2LineStrHandler);
        this.fileName = fileName;
        this.mapperFun = mapperFun;
        this.param = param;
        if (pageSize != null && pageSize > 0) {
            this.pageSize = pageSize;
        }
        this.iBean2LineStrHandler = iBean2LineStrHandler;
        this.fileCharacterSet = fileCharacterSet;
    }

    public BathFileWriter(String fileName,
                          Function<T, List<R>> mapperFun,
                          T param,
                          Integer pageSize,
                          IBean2LineStrHandler<R> iBean2LineStrHandler) {
        paramCheck(fileName, mapperFun, iBean2LineStrHandler);
        this.fileName = fileName;
        this.mapperFun = mapperFun;
        this.param = param;
        if (pageSize != null && pageSize > 0) {
            this.pageSize = pageSize;
        }
        this.iBean2LineStrHandler = iBean2LineStrHandler;
    }

    /**
     * 开始读取数据并写到文件
     */
    public boolean startQueryAndWrite() {
        log.info("开始进行分页读取数据并写到文件中,文件名：【{}】，函数：【{}】，页大小：【{}】，参数：【{}】",
                fileName,
                mapperFun,
                pageSize,
                param);
        try {
            File writeFile = new File(fileName);
            if (writeFile.exists()) {
                writeFile.deleteOnExit();
            }
            writeFile.createNewFile();
            try (FileOutputStream fileOutputStream = new FileOutputStream(writeFile);
                 OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, fileCharacterSet);
                 BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {
                int curPage = 1;
                Page<R> page = PageHelper.startPage(curPage, pageSize, true).doSelectPage(() -> mapperFun.apply(param));
                List<R> result = page.getResult();
                log.info("分页读取数据并写到文件中,文件名：【{}】，当前页：【{}】，页大小：【{}】，查询到数据：【{}】",
                        fileName,
                        curPage,
                        pageSize,
                        result.size());
                for (R r : result) {
                    bufferedWriter.write(iBean2LineStrHandler.convert(r));
                    bufferedWriter.newLine();
                }
                bufferedWriter.flush();
                int pages = page.getPages();
                while (pages >= curPage) {
                    curPage++;
                    page = PageHelper.startPage(curPage, pageSize, false).doSelectPage(() -> mapperFun.apply(param));
                    result = page.getResult();
                    log.info("分页读取数据并写到文件中,文件名：【{}】，当前页：【{}】，页大小：【{}】，查询到数据：【{}】调",
                            fileName,
                            curPage,
                            pageSize,
                            result.size());
                    for (R r : result) {
                        bufferedWriter.write(iBean2LineStrHandler.convert(r));
                        bufferedWriter.newLine();
                    }
                    bufferedWriter.flush();
                }
            }
            return true;
        } catch (Exception exception) {
            log.error("分页查询数据并且写到文件发生异常：", exception);
            new File(fileName).deleteOnExit();
            return false;
        }
    }

    private void paramCheck(String fileName, Function<T, List<R>> mapperFun, IBean2LineStrHandler<R> iBean2LineStrHandler) {
        if (StringUtils.isEmpty(fileName) ||
                mapperFun == null ||
                iBean2LineStrHandler == null
        ) {
            throw new RuntimeException("构建参数不合法");
        }
    }

}
