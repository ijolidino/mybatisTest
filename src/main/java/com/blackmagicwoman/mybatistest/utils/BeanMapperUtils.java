package com.blackmagicwoman.mybatistest.utils;

import lombok.Data;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;

import java.io.Serializable;
import java.util.*;
/**
 * @program: mybatisTest
 * @description: Bean复制/拷贝工具类
 * @author: Fuwen
 * @create: 2022-09-24 10:06
 **/
public class BeanMapperUtils {

    /**
     * 默认转换器工厂
     */
    private static final  DefaultMapperFactory defaultMapperFactory = new DefaultMapperFactory.Builder().build();

    /**
     * 转换器 通过{@link BeanMapperUtils#getMapperFacade()}获取并使用
     */
    private static volatile MapperFacade mapperFacade = null;

    @Data
    static class ConvertInfo implements Serializable {
        private FuntionUtils.SFunction<?, ?> aGetFn;
        private FuntionUtils.SFunction<?, ?> bGetFn;
        private String aFieldName;
        private String bFieldName;
        private Class<?> aClass;
        private Class<?> bClass;
        private ma.glasnost.orika.Converter<?, ?> converter;
    }

    /**
     * 集合转换为Set集合
     *
     * @param source           源集合
     * @param destinationClass 目标类型
     * @param <S>              泛型-源类型
     * @param <D>              泛型-目标类型
     * @return 目标Set集合
     */
    public static <S, D> Set<D> mapAsSet(Iterable<S> source, Class<D> destinationClass) {
        return getMapperFacade().mapAsSet(source, destinationClass);
    }

    /**
     * 数组转换为集合
     *
     * @param source           源数组
     * @param destinationClass 目标类型
     * @param <S>              泛型-源类型
     * @param <D>              泛型-目标类型
     * @return 目标Set集合
     */
    public static <S, D> Set<D> mapAsSet(S[] source, Class<D> destinationClass) {
        return getMapperFacade().mapAsSet(source, destinationClass);
    }

    /**
     * 集合转换为List集合
     *
     * @param source           源集合
     * @param destinationClass 目标类型
     * @param <S>              泛型-源类型
     * @param <D>              泛型-目标类型
     * @return 目标List集合
     */
    public static <S, D> List<D> mapAsList(Iterable<S> source, Class<D> destinationClass) {
        return getMapperFacade().mapAsList(source, destinationClass);
    }

    /**
     * 数组转换为List集合
     *
     * @param source           源数组
     * @param destinationClass 目标类型
     * @param <S>              泛型-源类型
     * @param <D>              泛型-目标类型
     * @return 目标List集合
     */
    public static <S, D> List<D> mapAsList(S[] source, Class<D> destinationClass) {
        return getMapperFacade().mapAsList(source, destinationClass);
    }

    /**
     * 集合转换为数组
     *
     * @param source           源集合
     * @param destination      目标数组
     * @param destinationClass 目标类型
     * @param <S>              泛型-源类型
     * @param <D>              泛型-目标类型
     * @return 目标数组
     */
    public static <S, D> D[] mapAsArray(Iterable<S> source, D[] destination, Class<D> destinationClass) {
        return getMapperFacade().mapAsArray(destination, source, destinationClass);
    }

    /**
     * 数组转换为数组
     *
     * @param destination      目标数组
     * @param source           源数组
     * @param destinationClass 目标类型
     * @param <S>              泛型-源类型
     * @param <D>              泛型-目标类型
     * @return 目标数组
     */
    public static <S, D> D[] mapAsArray(S[] source, D[] destination, Class<D> destinationClass) {
        return getMapperFacade().mapAsArray(destination, source, destinationClass);
    }

    /**
     * 将源对象字段的值拷贝到目标对象相应字段的值
     *
     * @param sourceObject      源对象
     * @param destinationObject 目标对象
     * @param <S>               泛型-源类型
     * @param <D>               泛型-目标类型
     */
    public static <S, D> void map(S sourceObject, D destinationObject) {
        getMapperFacade().map(sourceObject, destinationObject);
    }

    /**
     * 集合转换拷贝
     *
     * @param source           源集合
     * @param destination      目标集合
     * @param destinationClass 目标类型
     * @param <S>              泛型-源类型
     * @param <D>              泛型-目标类型
     */
    public static <S, D> void mapAsCollection(Iterable<S> source, Collection<D> destination, Class<D> destinationClass) {
        getMapperFacade().mapAsCollection(source, destination, destinationClass);
    }

    /**
     * 数组转换为集合
     *
     * @param source           源数组
     * @param destination      目标集合
     * @param destinationClass 目标类型
     * @param <S>              泛型-源类型
     * @param <D>              泛型-目标类型
     */
    public static <S, D> void mapAsCollection(S[] source, Collection<D> destination, Class<D> destinationClass) {
        getMapperFacade().mapAsCollection(source, destination, destinationClass);
    }

    /**
     * bean转换到map集合（排查null值）
     *
     * @param source 源对象
     * @param <S>    源对象类型
     * @return map集合
     */
    public static <S> Map<String, Object> bean2HashMap(S source) {
        if (Objects.isNull(source)) {
            return null;
        }
        registerMapNulls(source.getClass());
        return map(source, HashMap.class);
    }

    /**
     * map集合转换到bean（排查null值）
     *
     * @param source map
     * @param dClass 目标类型
     * @param <D>    目标对象类型
     * @return 目标对象
     */
    public static <D> D hashMap2Bean(Map<String, Object> source, Class<D> dClass) {
        if (Objects.isNull(source)) {
            return null;
        }
        registerMapNulls(dClass);
        return map(source, dClass);
    }


    private final static List<String> mapNullsClassList = new ArrayList<>();

    private static void registerMapNulls(Class<?> aClass) {
        //判断是否存在
        if (!mapNullsClassList.contains(aClass.getName())) {
            synchronized (BeanMapperUtils.class) {
                if (mapNullsClassList.contains(aClass.getName())) {
                    return;
                }
                defaultMapperFactory.classMap(aClass, HashMap.class)
                        .mapNulls(false)
                        .byDefault()
                        .register();
                mapNullsClassList.add(aClass.getName());
            }
        }
    }

    /**
     * builder模式，产生一个ConvertBuilder
     *
     * @return builder对象
     */
    public static ConvertBuilder builder() {
        return new ConvertBuilder();
    }

    public static MapperFacade getMapperFacade() {
        if (mapperFacade != null) {
            return mapperFacade;
        }
        synchronized (BeanMapperUtils.class) {
            if (mapperFacade != null) {
                return mapperFacade;
            }
            mapperFacade = defaultMapperFactory.getMapperFacade();
            return mapperFacade;
        }
    }

    public static DefaultMapperFactory getDefaultMapperFactory() {
        return defaultMapperFactory;
    }

    /**
     * 转换Builder</br>
     * 注意：一个类到类的映射配置(ClassA->ClassB)应当在同一个builder内实现配置，否则会出现默认映射覆盖之前注册的自定义映射的问题
     */
    @Data
    public static class ConvertBuilder {

        private List<ConvertInfo> convertInfos = new ArrayList<>();

        /**
         * 添加一组带自定义转换器的字段映射关系
         * 自定义转换器为空则代表没有自定义转换
         *
         * @param aGetFn    源字段get函数
         * @param bGetFn    目标字段get函数
         * @param converter 自定义转换器，需要继承{@link BidirectionalConverter}实现
         * @param <S>       源对象类型
         * @param <D>       目标对象类型
         * @param <TA>      源字段类型
         * @param <TB>      目标字段类型
         * @return 当前builder对象
         */
        public <S, D, TA, TB> ConvertBuilder add(FuntionUtils.SFunction<S, ?> aGetFn,
                                                 FuntionUtils.SFunction<D, ?> bGetFn,
                                                 ma.glasnost.orika.Converter<TA, TB> converter) {
            //校验是否存在多组类的映射
            FuntionUtils.FieldInfo aFieldInfo = FuntionUtils.getFieldInfoByGetFn(aGetFn);
            FuntionUtils.FieldInfo bFieldInfo = FuntionUtils.getFieldInfoByGetFn(bGetFn);
            ConvertInfo convertInfo = new ConvertInfo();
            convertInfo.setConverter(converter);
            convertInfo.setAGetFn(aGetFn);
            convertInfo.setBGetFn(bGetFn);
            convertInfo.setAClass(aFieldInfo.getAClass());
            convertInfo.setBClass(bFieldInfo.getAClass());
            convertInfo.setAFieldName(aFieldInfo.getFieldName());
            convertInfo.setBFieldName(bFieldInfo.getFieldName());
            if (check(convertInfo)) {
                throw new RuntimeException("存在多组不同类的类型映射");
            }
            convertInfos.add(convertInfo);
            return this;
        }

        /**
         * 添加一组带自定义转换器的字段映射关系
         * 自定义转换器为空则代表没有自定义转换
         *
         * @param sClass     源类型
         * @param dClass     目标类型
         * @param sFieldName 源字段名
         * @param dFieldName 目标字段名
         * @param converter  自定义转换器，需要继承{@link BidirectionalConverter}实现
         * @param <S>        源对象类型
         * @param <D>        目标对象类型
         * @param <TA>       源字段类型
         * @param <TB>       目标字段类型
         * @return 当前builder对象
         */
        public <S, D, TA, TB> ConvertBuilder add(Class<S> sClass,
                                                 Class<D> dClass,
                                                 String sFieldName,
                                                 String dFieldName,
                                                 ma.glasnost.orika.Converter<TA, TB> converter) {
            //校验是否存在多组类的映射
            ConvertInfo convertInfo = new ConvertInfo();
            convertInfo.setConverter(converter);
            convertInfo.setAGetFn(null);
            convertInfo.setBGetFn(null);
            convertInfo.setAClass(sClass);
            convertInfo.setBClass(dClass);
            convertInfo.setAFieldName(sFieldName);
            convertInfo.setBFieldName(dFieldName);
            if (check(convertInfo)) {
                throw new RuntimeException("存在多组不同类的类型映射");
            }
            convertInfos.add(convertInfo);
            return this;
        }

        /**
         * 添加一组字段映射关系
         *
         * @param aGetFn 源字段get函数
         * @param bGetFn 目标字段get函数
         * @param <S>    源对象类型
         * @param <D>    目标对象类型
         * @return 当前builder对象
         */
        public <S, D> ConvertBuilder add(FuntionUtils.SFunction<S, ?> aGetFn,
                                         FuntionUtils.SFunction<D, ?> bGetFn) {
            add(aGetFn, bGetFn, null);
            return this;
        }

        /**
         * 添加一组字段映射关系
         *
         * @param sClass     源类类型
         * @param dClass     目标类类型
         * @param sFieldName 源字段名
         * @param dFieldName 目标字段名
         * @param <S>        源对象类型
         * @param <D>        目标对象类型
         * @return 当前builder对象
         */
        public <S, D> ConvertBuilder add(Class<S> sClass, Class<D> dClass,
                                         String sFieldName, String dFieldName) {
            add(sClass, dClass, sFieldName, dFieldName, null);
            return this;
        }

        /**
         * 注册提交的映射配置
         */
        public void register() {
            if (convertInfos.isEmpty()) {
                return;
            }
            ClassMapBuilder<?, ?> classMapBuilder = defaultMapperFactory.classMap(convertInfos.get(0).getAClass(), convertInfos.get(0).getBClass());
            convertInfos.forEach(convertInfo -> {
                        if (convertInfo.getConverter() != null) {
                            String convterId = UUID.randomUUID().toString();
                            defaultMapperFactory.getConverterFactory().registerConverter(convterId, convertInfo.getConverter());
                            classMapBuilder.fieldMap(convertInfo.getAFieldName(), convertInfo.getBFieldName()).converter(convterId).add();
                        } else {
                            classMapBuilder.fieldMap(convertInfo.getAFieldName(), convertInfo.getBFieldName()).add();
                        }
                        classMapBuilder.byDefault().register();
                    }
            );
        }

        private boolean check(ConvertInfo convertInfo) {
            if (convertInfos.isEmpty()) {
                return false;
            }
            return convertInfos.stream()
                    .filter(c -> !c.getAClass().equals(convertInfo.getAClass()) || !c.getBClass().equals(convertInfo.getBClass()))
                    .findAny()
                    .orElse(null) != null;
        }
    }


    /**
     * 将源对象转换并新生成一个目标类型的对象
     *
     * @param sourceObject     源对象
     * @param destinationClass 目标类型
     * @param <S>              泛型-源类型
     * @param <D>              泛型-目标类型
     * @return 目标对象
     */
    public static <S, D> D map(S sourceObject, Class<D> destinationClass) {
        return getMapperFacade().map(sourceObject, destinationClass);
    }
}
