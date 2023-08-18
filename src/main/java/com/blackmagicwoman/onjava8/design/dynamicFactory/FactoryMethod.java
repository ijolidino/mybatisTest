package com.blackmagicwoman.onjava8.design.dynamicFactory;

/**
 * 工厂接口
 */
public interface FactoryMethod {

    Shape create(String type) throws Exception;
}
