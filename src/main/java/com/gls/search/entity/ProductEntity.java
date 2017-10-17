/**
 * Copyright 2016 ECCloud Corporation. All rights reserved.
 *
 * --------------------------------------------------------
 * 此技术信息为本公司机密信息，未经本公司书面同意禁止向第三方披露.
 * 版权所有：贝恩国际
 * --------------------------------------------------------
 */

package com.gls.search.entity;

import java.util.List;

import com.gls.search.core.SolrEnum;
import com.gls.search.core.SolrField;

public class ProductEntity {
    
    
    private Integer id;
    @SolrField(key=SolrEnum.KEY,field=SolrEnum.FIELD)
    private Integer proId;
    @SolrField(field=SolrEnum.FIELD)
    private String pname;
    @SolrField(field=SolrEnum.FIELD)
    private Integer cid;
    @SolrField(field=SolrEnum.FIELD)
    private String cname;
    
    private Integer category_level1_id;
    private Integer category_level2_id;
    private Integer category_level3_id;
    @SolrField(field=SolrEnum.FIELD)
    private String categoryName;
    private Integer suppliersId;
    private String suppliersName;
    private String createTime;
    private String updateTime;
    
    private String listJson;
    
    private List<ProductInfoEntiy> infoList;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getProId() {
        return proId;
    }
    public void setProId(Integer proId) {
        this.proId = proId;
    }
    public Integer getCategory_level1_id() {
        return category_level1_id;
    }
    public void setCategory_level1_id(Integer category_level1_id) {
        this.category_level1_id = category_level1_id;
    }
    public Integer getCategory_level2_id() {
        return category_level2_id;
    }
    public void setCategory_level2_id(Integer category_level2_id) {
        this.category_level2_id = category_level2_id;
    }
    public Integer getCategory_level3_id() {
        return category_level3_id;
    }
    public void setCategory_level3_id(Integer category_level3_id) {
        this.category_level3_id = category_level3_id;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public Integer getSuppliersId() {
        return suppliersId;
    }
    public void setSuppliersId(Integer suppliersId) {
        this.suppliersId = suppliersId;
    }
    public String getSuppliersName() {
        return suppliersName;
    }
    public void setSuppliersName(String suppliersName) {
        this.suppliersName = suppliersName;
    }
    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
    public List<ProductInfoEntiy> getInfoList() {
        return infoList;
    }
    public void setInfoList(List<ProductInfoEntiy> infoList) {
        this.infoList = infoList;
    }
    public String getListJson() {
        return listJson;
    }
    public void setListJson(String listJson) {
        this.listJson = listJson;
    }
    public Integer getCid() {
        return cid;
    }
    public void setCid(Integer cid) {
        this.cid = cid;
    }
    public String getCname() {
        return cname;
    }
    public void setCname(String cname) {
        this.cname = cname;
    }
    public String getPname() {
        return pname;
    }
    public void setPname(String pname) {
        this.pname = pname;
    }
    
    
    
}
