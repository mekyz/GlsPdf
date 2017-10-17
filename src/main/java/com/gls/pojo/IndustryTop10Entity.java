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

public class IndustryTop10Entity implements Serializable{
    
    private Integer cid; //行业id
    
    private String cname;//行业名称
    
    private String cnameEn;//英文
    
    private Double value;//柜量，重量
    
    private Integer clevel;
    
    private String statisTime;//统计时间段
    

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

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getStatisTime() {
        return statisTime;
    }

    public void setStatisTime(String statisTime) {
        this.statisTime = statisTime;
    }

    public String getCnameEn() {
        return cnameEn;
    }

    public void setCnameEn(String cnameEn) {
        this.cnameEn = cnameEn;
    }

    public Integer getClevel() {
        return clevel;
    }

    public void setClevel(Integer clevel) {
        this.clevel = clevel;
    }

    
    
}
