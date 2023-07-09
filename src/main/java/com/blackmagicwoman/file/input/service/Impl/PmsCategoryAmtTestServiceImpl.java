package com.blackmagicwoman.file.input.service.Impl;

import com.blackmagicwoman.file.input.entity.PmsCategoryAmtTest;
import com.blackmagicwoman.file.input.service.PmsCategoryAmtTestService;
import com.blackmagicwoman.mybatistest.mapper.PmsCategoryAmtTestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 测试大文件的数据
 * @author 黑色魔术女人
 * @date 2023-07-09
 */
@Service
public class PmsCategoryAmtTestServiceImpl implements PmsCategoryAmtTestService {

    @Resource
    private PmsCategoryAmtTestMapper pmsCategoryAmtTestMapper;


    @Override
    public Object insert(PmsCategoryAmtTest pmsCategoryAmtTest) {

        // valid
        if (pmsCategoryAmtTest == null) {
            return null;
        }

        pmsCategoryAmtTestMapper.insert(pmsCategoryAmtTest);
        return null;
    }


    @Override
    public Object delete(int id) {
        int ret = pmsCategoryAmtTestMapper.delete(id);
        return null;
    }


    @Override
    public Object update(PmsCategoryAmtTest pmsCategoryAmtTest) {
        int ret = pmsCategoryAmtTestMapper.update(pmsCategoryAmtTest);
        return null;
    }


    @Override
    public PmsCategoryAmtTest load(int id) {
        return pmsCategoryAmtTestMapper.load(id);
    }


    @Override
    public Map<String,Object> pageList(int offset, int pagesize) {

        List<PmsCategoryAmtTest> pageList = pmsCategoryAmtTestMapper.pageList(offset, pagesize);
        int totalCount = pmsCategoryAmtTestMapper.pageListCount(offset, pagesize);

        // result
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("pageList", pageList);
        result.put("totalCount", totalCount);

        return result;
    }

}
