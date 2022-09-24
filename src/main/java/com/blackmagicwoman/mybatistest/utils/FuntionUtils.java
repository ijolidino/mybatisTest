package com.blackmagicwoman.mybatistest.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;
/**
 * @program: mybatisTest
 * @description: function相关操作集合
 * @author: Fuwen
 * @create: 2022-09-24 10:16
 **/
@Slf4j
public class FuntionUtils {

    public static <T> FieldInfo getFieldInfoByGetFn(SFunction<T, ?> fn) {
        try {
            FieldInfo fieldInfo = new FieldInfo();
            SerializedLambda serializedLambda = getSerializedLambda(fn);
            // 从lambda信息取出method、field、class等
            String fieldName = StringUtils.uncapitalize(serializedLambda.getImplMethodName().substring("get".length()));
            Class<?> aClass = Class.forName(serializedLambda.getImplClass().replaceAll("/", "."));
            Field field = aClass.getDeclaredField(fieldName);
            fieldInfo.setField(field);
            fieldInfo.setFieldName(fieldName);
            fieldInfo.setAClass(aClass);
            return fieldInfo;
        } catch (Exception e) {
            log.error("通过函数获取字段相关信息发生异常", e);
            throw new RuntimeException(e);
        }
    }

    private static <T> SerializedLambda getSerializedLambda(SFunction<T, ?> fn) throws NoSuchMethodException {
        // 从function取出序列化方法
        Method writeReplaceMethod = fn.getClass().getDeclaredMethod("writeReplace");

        // 从序列化方法取出序列化的lambda信息
        boolean isAccessible = writeReplaceMethod.isAccessible();
        writeReplaceMethod.setAccessible(true);
        SerializedLambda serializedLambda;
        try {
            serializedLambda = (SerializedLambda) writeReplaceMethod.invoke(fn);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        writeReplaceMethod.setAccessible(isAccessible);
        return serializedLambda;
    }

    @Data
    public static class FieldInfo implements Serializable {
        private String fieldName;
        private Field field;
        private Class<?> aClass;
    }

    /**
     * 使Function获取序列化能力
     */
    @FunctionalInterface
    public interface SFunction<T, R> extends Function<T, R>, Serializable { }
}
