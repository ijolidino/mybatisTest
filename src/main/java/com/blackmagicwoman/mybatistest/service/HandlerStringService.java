package com.blackmagicwoman.mybatistest.service;

import com.blackmagicwoman.fileInputAndGenerate.IBean2LineStrHandler;

/**
 * @program: mybatisTest
 * @description: 处理String字符串的service
 * @author: Fuwen
 * @create: 2022-09-14 21:50
 **/
public class HandlerStringService {
    /**
     * 处理字符串s
     * @param s
     * @param bean2LineStrHandler
     * @return
     */
    public String handlerString(String s,IBean2LineStrHandler bean2LineStrHandler){
        return bean2LineStrHandler.convert(s);
    }
}
