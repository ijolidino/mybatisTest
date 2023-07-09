package com.blackmagicwoman.file.input.service;

import com.blackmagicwoman.file.input.entity.PmsCategoryAmtTest;

import java.util.Map;

/**
 * @description 测试大文件的数据
 * @author 黑色魔术女人
 * @date 2023-07-09
 */
public interface PmsCategoryAmtTestService {

    /**
     * 新增
     */
    public Object insert(PmsCategoryAmtTest pmsCategoryAmtTest);

    /**
     * 删除
     */
    public Object delete(int id);

    /**
     * 更新
     */
    public Object update(PmsCategoryAmtTest pmsCategoryAmtTest);

    /**
     * 根据主键 id 查询
     */
    public PmsCategoryAmtTest load(int id);

    /**
     * 分页查询
     */
    public Map<String,Object> pageList(int offset, int pagesize);

}
