package com.blackmagicwoman.mybatistest.mapper;

import com.blackmagicwoman.mybatistest.entity.PmsCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
public interface PmsCategoryMapper extends BaseMapper<PmsCategory> {

    Cursor<PmsCategory> getCursor(Integer limit);
}
