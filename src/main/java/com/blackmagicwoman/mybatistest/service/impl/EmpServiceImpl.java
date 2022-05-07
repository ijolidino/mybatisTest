package com.blackmagicwoman.mybatistest.service.impl;

import com.blackmagicwoman.mybatistest.entity.Emp;
import com.blackmagicwoman.mybatistest.mapper.EmpMapper;
import com.blackmagicwoman.mybatistest.service.IEmpService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 员工表 服务实现类
 * </p>
 *
 * @author Fuwen
 * @since 2022-04-15
 */
@Service
public class EmpServiceImpl extends ServiceImpl<EmpMapper, Emp> implements IEmpService {

}
