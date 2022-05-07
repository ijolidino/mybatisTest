package com.blackmagicwoman.mybatistest.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 员工表
 * </p>
 *
 * @author Fuwen
 * @since 2022-04-15
 */
@ApiModel(value = "Emp对象", description = "员工表")
public class Emp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("员工编号")
    private Integer empNo;

    @ApiModelProperty("员工姓名")
    private String eName;

    @ApiModelProperty("职位")
    private String job;

    @ApiModelProperty("分数")
    private Integer mgr;

    @ApiModelProperty("入职时间")
    private LocalDateTime hireDate;

    @ApiModelProperty("薪资")
    private Integer sal;

    private Integer comm;

    @ApiModelProperty("部门变化")
    private Integer deptNo;

    public Integer getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Integer empNo) {
        this.empNo = empNo;
    }
    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }
    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
    public Integer getMgr() {
        return mgr;
    }

    public void setMgr(Integer mgr) {
        this.mgr = mgr;
    }
    public LocalDateTime getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDateTime hireDate) {
        this.hireDate = hireDate;
    }
    public Integer getSal() {
        return sal;
    }

    public void setSal(Integer sal) {
        this.sal = sal;
    }
    public Integer getComm() {
        return comm;
    }

    public void setComm(Integer comm) {
        this.comm = comm;
    }
    public Integer getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(Integer deptNo) {
        this.deptNo = deptNo;
    }

    @Override
    public String toString() {
        return "Emp{" +
            "empNo=" + empNo +
            ", eName=" + eName +
            ", job=" + job +
            ", mgr=" + mgr +
            ", hireDate=" + hireDate +
            ", sal=" + sal +
            ", comm=" + comm +
            ", deptNo=" + deptNo +
        "}";
    }
}
