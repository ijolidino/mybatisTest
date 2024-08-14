package com.blackmagicwoman.query;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @program: mybatisTest
 * @description: 查询和插入；一张表在数据量太大几十万的情况下使用流进行备份
 * @author: heise
 * @create: 2022-10-31 20:05
 **/
@Data
@Slf4j
public class QueryAndInsertDB<T, R, E> {

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
     * 插入sql
     */
    private Consumer<E> insertCon;

    private DataConverter converter;

    /**
     * 构造函数
     *
     * @param mapperFun 查询mapper，数据源
     * @param param     参数
     * @param pageSize  页数
     * @param insertCon 插入语句
     */
    public QueryAndInsertDB(Function<T, List<R>> mapperFun,
                            T param,
                            Integer pageSize,
                            Consumer<E> insertCon) {
        this.mapperFun = mapperFun;
        this.param = param;
        this.pageSize = pageSize;
        this.insertCon = insertCon;
        paramCheck(param, mapperFun, insertCon);
    }

    public QueryAndInsertDB(Function<T, List<R>> mapperFun,
                            T param,
                            Consumer<E> insertCon) {
        this.mapperFun = mapperFun;
        this.param = param;
        this.insertCon = insertCon;
        paramCheck(param, mapperFun, insertCon);
    }

    /**
     * 构造函数
     *
     * @param mapperFun 查询mapper，数据源
     * @param param     参数
     * @param pageSize  页数
     * @param insertCon 插入语句
     * @param converter 转换器
     */
    public QueryAndInsertDB(Function<T, List<R>> mapperFun,
                            T param,
                            Integer pageSize,
                            Consumer<E> insertCon,
                            DataConverter converter) {
        this.mapperFun = mapperFun;
        this.param = param;
        this.pageSize = pageSize;
        this.insertCon = insertCon;
        this.converter = converter;
        paramCheck(param, mapperFun, insertCon);
    }

    public boolean startInsert() {
        log.info("开始进行分页查询数据并写到数据库中,插入函数：【{}】，函数：【{}】，页大小：【{}】，参数：【{}】",
                insertCon,
                mapperFun,
                pageSize,
                param);
        int curPage = 1;
        Page<R> objects = PageHelper.startPage(curPage, pageSize, true).doSelectPage(() -> mapperFun.apply(param));
        List<R> result = objects.getResult();
        log.info("开始进行分页查询数据并写到数据库中,插入函数：【{}】，当前页：【{}】，页大小：【{}】，查询到数据：【{}】",
                insertCon,
                curPage,
                pageSize,
                result.size());
        for (R r : result) {
            E convert = (E) converter.convert(r);
            insertCon.accept(convert);
        }
        int pages = objects.getPages();
        while (pages >= curPage) {
            curPage++;
            objects = PageHelper.startPage(curPage, pageSize, false).doSelectPage(() -> mapperFun.apply(param));
            result = objects.getResult();
            log.info("开始进行分页查询数据并写到数据库中,插入函数：【{}】，当前页：【{}】，页大小：【{}】，查询到数据：【{}】",
                    insertCon,
                    curPage,
                    pageSize,
                    result.size());
            for (R r : result) {
                E convert = (E) converter.convert(r);
                insertCon.accept(convert);
            }
        }
        return true;
    }

    private void paramCheck(T param, Function<T, List<R>> mapperFun, Consumer<E> insertCon) {
        if (Objects.isNull(param) ||
                mapperFun == null ||
                insertCon == null
        ) {
            throw new RuntimeException("构建参数不合法");
        }
    }
}
