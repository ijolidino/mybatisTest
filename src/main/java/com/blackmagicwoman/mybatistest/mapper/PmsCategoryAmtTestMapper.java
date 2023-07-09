package com.blackmagicwoman.mybatistest.mapper;

import com.blackmagicwoman.file.input.entity.PmsCategoryAmtTest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description 测试大文件的数据
 * @author 黑色魔术女人
 * @date 2023-07-09
 */
@Mapper
@Repository
public interface PmsCategoryAmtTestMapper {

    /**
     * 新增
     * @author 黑色魔术女人
     * @date 2023/07/09
     **/
    int insert(PmsCategoryAmtTest pmsCategoryAmtTest);

    /**
     * 刪除
     * @author 黑色魔术女人
     * @date 2023/07/09
     **/
    int delete(int id);

    /**
     * 更新
     * @author 黑色魔术女人
     * @date 2023/07/09
     **/
    int update(PmsCategoryAmtTest pmsCategoryAmtTest);

    /**
     * 查询 根据主键 id 查询
     * @author 黑色魔术女人
     * @date 2023/07/09
     **/
    PmsCategoryAmtTest load(int id);

    /**
     * 查询 分页查询
     * @author 黑色魔术女人
     * @date 2023/07/09
     **/
    List<PmsCategoryAmtTest> pageList(int offset, int pagesize);

    /**
     * 查询 分页查询 count
     * @author 黑色魔术女人
     * @date 2023/07/09
     **/
    int pageListCount(int offset,int pagesize);

    int batchInsert(@Param("list")List<PmsCategoryAmtTest> pmsCategoryAmtTests);
}
