/**
 * Copyright 2016 ECCloud Corporation. All rights reserved.
 *
 * --------------------------------------------------------
 * 此技术信息为本公司机密信息，未经本公司书面同意禁止向第三方披露.
 * 版权所有：贝恩国际
 * --------------------------------------------------------
 */

package com.gls.search.entity;

import com.gls.search.core.SolrEnum;
import com.gls.search.core.SolrField;

public class ProductInfoEntiy {
    
    private Integer id;
    @SolrField(key=SolrEnum.KEY,field=SolrEnum.FIELD)
    private Integer proInfoId;
    @SolrField(field=SolrEnum.FIELD)
    private Integer productId;
    @SolrField(field=SolrEnum.FIELD)
    private String productName;
    @SolrField(field=SolrEnum.FIELD)
    private Integer franklyOrderId;
    @SolrField(field=SolrEnum.FIELD)
    private Double amount;
    @SolrField(field=SolrEnum.FIELD)
    private Double weights;
    @SolrField(field=SolrEnum.FIELD)
    private String weightUnit;
    @SolrField(field=SolrEnum.FIELD)
    private Double volume;
    @SolrField(field=SolrEnum.FIELD)
    private String volumeUnit;
    @SolrField(field=SolrEnum.FIELD)
    private String arriveTime;
    @SolrField(field=SolrEnum.FIELD)
    private String createTime;
    @SolrField(field=SolrEnum.FIELD)
    private String updateTime;
    @SolrField(field=SolrEnum.FIELD)
    private Integer suppliersId;
    @SolrField(field=SolrEnum.FIELD)
    private Integer businessId;
    
    @SolrField(field=SolrEnum.FIELD)
    private Integer vesselId;
    
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getProInfoId() {
        return proInfoId;
    }
    public void setProInfoId(Integer proInfoId) {
        this.proInfoId = proInfoId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public Integer getFranklyOrderId() {
        return franklyOrderId;
    }
    public void setFranklyOrderId(Integer franklyOrderId) {
        this.franklyOrderId = franklyOrderId;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public Double getWeights() {
        return weights;
    }
    public void setWeights(Double weights) {
        this.weights = weights;
    }
    public String getWeightUnit() {
        return weightUnit;
    }
    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }
    public Double getVolume() {
        return volume;
    }
    public void setVolume(Double volume) {
        this.volume = volume;
    }
    public String getVolumeUnit() {
        return volumeUnit;
    }
    public void setVolumeUnit(String volumeUnit) {
        this.volumeUnit = volumeUnit;
    }
    public String getArriveTime() {
        return arriveTime;
    }
    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
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
    public Integer getProductId() {
        return productId;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    public Integer getSuppliersId() {
        return suppliersId;
    }
    public void setSuppliersId(Integer suppliersId) {
        this.suppliersId = suppliersId;
    }
    public Integer getBusinessId() {
        return businessId;
    }
    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }
    public Integer getVesselId() {
        return vesselId;
    }
    public void setVesselId(Integer vesselId) {
        this.vesselId = vesselId;
    }
    
    
    
}
