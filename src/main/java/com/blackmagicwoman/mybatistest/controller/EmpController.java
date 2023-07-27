package com.blackmagicwoman.mybatistest.controller;

import com.blackmagicwoman.mybatistest.entity.PmsCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 员工表 前端控制器
 * </p>
 *
 * @author Fuwen
 * @since 2022-04-15
 */
@Controller
@RequestMapping("/emp")
@Slf4j
public class EmpController {

    public void empOther(PmsCategory pmsCategory){
        log.info("测试进入其他类EmpController");
        pmsCategory.setName("测试进入其他类EmpController");
    }

}
