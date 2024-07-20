package com.blackmagicwoman.mybatistest.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @program: mybatisTest
 * @description: 部门
 * @author: heise
 * @create: 2022-08-06 21:52
 **/
@Data
public class Dept implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 部门编号
     */
    private Integer deptNo;

    /**
     * 部门名称
     */
    private String dName;

    /**
     * 地址
     */
    private String loc;

    private List<Emp> empList;
}
