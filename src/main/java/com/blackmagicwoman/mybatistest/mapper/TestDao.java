package com.blackmagicwoman.mybatistest.mapper;

import com.blackmagicwoman.mybatistest.entity.EmpEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: mybatisTest
 * @description:
 * @author: heise
 * @create: 2022-04-15 21:03
 **/

@Mapper
public interface TestDao {

    EmpEntity getById(Integer empNo);

    List<EmpEntity> seleteNotIn(@Param("list") List<String> list);

    int deleteNotIn(@Param("list") List<String> strings);

    EmpEntity getCursor(@Param("limit") Integer limit);
}
