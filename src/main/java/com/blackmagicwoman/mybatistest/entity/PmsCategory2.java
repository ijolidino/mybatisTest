package com.blackmagicwoman.mybatistest.entity;

//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableId;
//import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 商品三级分类
 * </p>
 *
 * @author heise
 * @since 2022-05-03
 */
//@TableName("pms_category")
public class PmsCategory2 implements Serializable {

    @ApiModelProperty("分类id")
    //@TableId(value = "cat_id", type = IdType.AUTO)
    private String catId;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("父分类id")
    private String parentCid;

    @ApiModelProperty("层级")
    private String catLevel;

    @ApiModelProperty("是否显示[0-不显示，1显示]")
    private String showStatus;

    @ApiModelProperty("排序")
    private String sort;

    @ApiModelProperty("图标地址")
    private String icon;

    @ApiModelProperty("计量单位")
    private String productUnit;

    @ApiModelProperty("商品数量")
    private String productCount;

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getParentCid() {
        return parentCid;
    }

    public void setParentCid(String parentCid) {
        this.parentCid = parentCid;
    }
    public String getCatLevel() {
        return catLevel;
    }

    public void setCatLevel(String catLevel) {
        this.catLevel = catLevel;
    }
    public String getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(String showStatus) {
        this.showStatus = showStatus;
    }
    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }
    public String getProductCount() {
        return productCount;
    }

    public void setProductCount(String productCount) {
        this.productCount = productCount;
    }

    @Override
    public String toString() {
        return "PmsCategory{" +
            "catId=" + catId +
            ", name=" + name +
            ", parentCid=" + parentCid +
            ", catLevel=" + catLevel +
            ", showStatus=" + showStatus +
            ", sort=" + sort +
            ", icon=" + icon +
            ", productUnit=" + productUnit +
            ", productCount=" + productCount +
        "}";
    }
}
