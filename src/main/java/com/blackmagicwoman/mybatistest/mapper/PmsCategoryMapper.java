package com.blackmagicwoman.mybatistest.mapper;

import com.blackmagicwoman.mybatistest.entity.PmsCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.cursor.Cursor;

import java.util.List;

/**
 * <p>
 * 商品三级分类 Mapper 接口
 * </p>
 *
 * @author Fuwen
 * @since 2022-05-03
 */
@Mapper
public interface PmsCategoryMapper {

    Cursor<PmsCategory> getCursor(Integer limit);

    @Select("select * from pms_category order by cat_id")
    List<PmsCategory> queryByPage(@Param("pageNum") int pageNum,
                                  @Param("pageSize") int pageSize);

    void batchInsert(@Param("list") List<PmsCategory> pmsCategories);

    void insert(PmsCategory pmsCategory);

    List<PmsCategory> query(PmsCategory pmsCategory);
}
