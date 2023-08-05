package com.blackmagicwoman.onjava8.design.dynamicFactory;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: mybatisTest
 * @description:
 * @author: Fuwen
 * @create: 2022-11-13 10:01
 **/
public class ShapeFactory2 implements FactoryMethod {
    private Map<String, Constructor> factories =
            new HashMap<>();

    private static Constructor load(String id) {
        System.out.println("loading " + id);
        try {
            return Class.forName("patterns.shapes." + id)
                    .getConstructor();
        } catch (ClassNotFoundException |
                 NoSuchMethodException e) {
            return null;
        }
    }

    @Override
    public Shape create(String id) throws Exception {
        try {
            return (Shape) factories
                    .computeIfAbsent(id, ShapeFactory2::load)
                    .newInstance();
        } catch (Exception e) {
            throw new Exception(id);
        }
    }

    public static void main(String[] args) {
        FactoryTest.test(new ShapeFactory2());
    }
}
