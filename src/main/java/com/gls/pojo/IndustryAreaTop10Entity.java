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

public class IndustryAreaTop10Entity implements Serializable{
    
    private Integer pid;//产品id
    
    private Integer did;//区域id
    
    private String dname;//区域名称
    
    private String dnameEn;
    
    private Double value;//柜量、重量
    
    private String pname;//产品名称
    
    private String valuePercent; //柜量 重量百分比

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getValuePercent() {
        return valuePercent;
    }

    public void setValuePercent(String valuePercent) {
        this.valuePercent = valuePercent;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getDnameEn() {
        return dnameEn;
    }

    public void setDnameEn(String dnameEn) {
        this.dnameEn = dnameEn;
    }

    
    
    
    
    
}
