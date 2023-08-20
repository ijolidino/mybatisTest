package com.blackmagicwoman.query;

/**
 * 数据转换器
 */
public interface DataConverter <R,E>{
    /**
     * 转换
     * @param r
     * @return E
     */
    E convert(R r);
}
