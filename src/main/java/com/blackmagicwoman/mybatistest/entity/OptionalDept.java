package com.blackmagicwoman.mybatistest.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @program: mybatisTest
 * @description: 部门
 * @author: Fuwen
 * @create: 2022-08-06 21:52
 **/
public class OptionalDept implements Serializable {

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
    public OptionalDept() {
    }

    public Integer getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(Integer deptNo) {
        this.deptNo = deptNo;
    }

    public String getDName() {
        return dName;
    }

    public void setDName(String dName) {
        this.dName = dName;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public List<Emp> getEmpList() {
        return empList;
    }

    public void setEmpList(List<Emp> empList) {
        this.empList = empList;
    }
}
