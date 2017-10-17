/**
 * Copyright 2016 ECCloud Corporation. All rights reserved.
 *
 * --------------------------------------------------------
 * 此技术信息为本公司机密信息，未经本公司书面同意禁止向第三方披露.
 * 版权所有：贝格
 * --------------------------------------------------------
 */

package com.gls.pojo;

import java.io.Serializable;
/**
 * 
 * 文  件  名:IndustryProductEntity<br/>  
 * 文件描述:行业供应商/采购商占比饼图<br/>  
 * 修  改  人: 詹昌高 <br/>
 * 修改日期:2016年12月24日<br/>
 * 修改内容:<br/>
 */
public class IndustrySupplierTop10 implements Serializable{
  
    private static final long serialVersionUID = 1L;
    
    private Integer did;//行业id
    
    private String cname;//行业名称
    
    private String cnameEn;
    
    private Double value;//柜量或者重量

    private String dname; //地区名称
    
    private String dnameEn;
    
//    private Double valuePercent; //柜量 重量百分比
    
    private String Baifei; //百分比

   
    
    public String getBaifei() {
        return Baifei;
    }

    public void setBaifei(String baifei) {
        Baifei = baifei;
    }

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getCnameEn() {
        return cnameEn;
    }

    public void setCnameEn(String cnameEn) {
        this.cnameEn = cnameEn;
    }

    public String getDnameEn() {
        return dnameEn;
    }

    public void setDnameEn(String dnameEn) {
        this.dnameEn = dnameEn;
    }

//    public Double getValuePercent() {
//        return valuePercent;
//    }
//
//    public void setValuePercent(Double valuePercent) {
//        this.valuePercent = valuePercent;
//    }

    
    
    
    
    
    
}
