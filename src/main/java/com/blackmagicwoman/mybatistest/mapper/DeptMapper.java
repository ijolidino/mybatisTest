package com.blackmagicwoman.mybatistest.mapper;

import com.blackmagicwoman.mybatistest.entity.Dept;
import com.blackmagicwoman.redisLock.RedisLock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: mybatisTest
 * @description: 部门
 * @author: heise
 * @create: 2022-08-06 21:54
 **/
@Mapper
@Repository
public interface DeptMapper {

    /**
     * 新增
     * @author zhengkai.blog.csdn.net
     * @date 2022/08/06
     **/
    @RedisLock(value = "com.blackmagicwoman.redisLock.LockAnnotationParser.getByEl")
    int insert(Dept dept);

    /**
     * 刪除
     * @author zhengkai.blog.csdn.net
     * @date 2022/08/06
     **/
    int delete(int id);

    /**
     * 更新
     * @author zhengkai.blog.csdn.net
     * @date 2022/08/06
     **/
    int update(Dept dept);

    /**
     * 查询 根据主键 id 查询
     * @author zhengkai.blog.csdn.net
     * @date 2022/08/06
     **/
    Dept load(@Param("id") int id);

    /**
     * 查询 分页查询
     * @author zhengkai.blog.csdn.net
     * @date 2022/08/06
     **/
    List<Dept> pageList(int offset, int pagesize);

    /**
     * 查询 分页查询 count
     * @author zhengkai.blog.csdn.net
     * @date 2022/08/06
     **/
    int pageListCount(int offset,int pagesize);

    Dept selectDeptAndEmp(int id);

    Dept selectById(int id);

    List<Dept> loadAll(Dept dept);
}
