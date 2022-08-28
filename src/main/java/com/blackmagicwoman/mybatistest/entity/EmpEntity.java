package com.blackmagicwoman.mybatistest.entity;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 员工表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-04-14 23:14:48
 */
@Data
public class EmpEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 员工编号
	 */
	  Integer empNo;
	/**
	 * 员工姓名
	 */
	private String eName;
	/**
	 * 职位
	 */
	private String job;
	/**
	 * 分数
	 */
	private Integer mgr;
	/**
	 * 入职时间
	 */
	private Date hireDate;
	/**
	 * 薪资
	 */
	private Integer sal;
	/**
	 *
	 */
	private Integer comm;
	/**
	 * 部门变化
	 */
	private Integer deptNo;

}
