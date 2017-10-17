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

import java.util.Date;

public class FrankOrderEntity {
    
    private Integer id; //主键id
    @SolrField(key=SolrEnum.KEY,field=SolrEnum.FIELD)
    private Integer orderId;//提单id 
    @SolrField(field=SolrEnum.FIELD)
    private String orderNo;//提单编号
    @SolrField(field=SolrEnum.FIELD)
    private String proDesc;//提单描述
    @SolrField(field=SolrEnum.FIELD)
    private String buniess;//采购商
    @SolrField(field=SolrEnum.FIELD)
    private String supplier;//供应商 
    @SolrField(field=SolrEnum.FIELD)
    private String tongzhiren;//通知人
    @SolrField(field=SolrEnum.FIELD)
    private String orginierCountry;//原产国
    @SolrField(field=SolrEnum.FIELD)
    private String mudiPort;//目的港
    @SolrField(field=SolrEnum.FIELD)
    private String arrideDate;//到岗日期
    @SolrField(field=SolrEnum.FIELD)
    private String qiyunPort;//起运港
    @SolrField(field=SolrEnum.FIELD)
    private Double orderWeight;//提单重量
    @SolrField(field=SolrEnum.FIELD)
    private String countryName;
    @SolrField(field = SolrEnum.FIELD)
    private Date frankTime;
    private String keyWordss;
    private String buniessAddress;
    private String productName;
    private Double orderVolume;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getOrderId() {
        return orderId;
    }
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    
    public String getBuniess() {
        return buniess;
    }
    public void setBuniess(String buniess) {
        this.buniess = buniess;
    }
    public String getSupplier() {
        return supplier;
    }
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
    public String getTongzhiren() {
        return tongzhiren;
    }
    public void setTongzhiren(String tongzhiren) {
        this.tongzhiren = tongzhiren;
    }
    public String getOrginierCountry() {
        return orginierCountry;
    }
    public void setOrginierCountry(String orginierCountry) {
        this.orginierCountry = orginierCountry;
    }
    public String getMudiPort() {
        return mudiPort;
    }
    public void setMudiPort(String mudiPort) {
        this.mudiPort = mudiPort;
    }
    public String getArrideDate() {
        return arrideDate;
    }
    public void setArrideDate(String arrideDate) {
        this.arrideDate = arrideDate;
    }
    public String getQiyunPort() {
        return qiyunPort;
    }
    public void setQiyunPort(String qiyunPort) {
        this.qiyunPort = qiyunPort;
    }
    public Double getOrderWeight() {
        return orderWeight;
    }
    public void setOrderWeight(Double orderWeight) {
        this.orderWeight = orderWeight;
    }
    public String getCountryName() {
        return countryName;
    }
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    public String getProDesc() {
        return proDesc;
    }
    public void setProDesc(String proDesc) {
        this.proDesc = proDesc;
    }


    public void setFrankTime(Date frankTime) {
        this.frankTime = frankTime;
    }

    public Date getFrankTime() {
        return frankTime;
    }

    public String getKeyWordss() {
        return keyWordss;
    }

    public void setKeyWordss(String keyWordss) {
        this.keyWordss = keyWordss;
    }

    public String getBuniessAddress() {
        return buniessAddress;
    }

    public void setBuniessAddress(String buniessAddress) {
        this.buniessAddress = buniessAddress;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public Double getOrderVolume() {
        return orderVolume;
    }

    public void setOrderVolume(Double orderVolume) {
        this.orderVolume = orderVolume;
    }
}
