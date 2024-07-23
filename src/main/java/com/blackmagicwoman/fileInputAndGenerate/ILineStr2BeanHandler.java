package com.blackmagicwoman.fileInputAndGenerate;


/**
 * @program: mybatisTest
 * @description: 基础变量
 * @author: heise
 * @create: 2022-09-07 20:10
 * 字符串到bean转换器
 */
public interface ILineStr2BeanHandler<T> {

    /**
     * 转换
     *
     * @param lineStr 行数据-文件中的一行数据
     * @param split   分割符
     * @return T
     */
    T convert(String lineStr, String split);
}
