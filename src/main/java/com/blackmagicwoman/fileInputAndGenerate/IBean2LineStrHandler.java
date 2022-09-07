package com.blackmagicwoman.fileInputAndGenerate;

/**
 * bean转到字符串
 * @param <T>
 */
public interface IBean2LineStrHandler<T>{

    String convert(T source);

    default String separator(){
        return BaseConstants.DEFAULT_CHARACTER_SET
    }
}
