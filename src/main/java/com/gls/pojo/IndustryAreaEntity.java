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
 * 文件描述:行业进/出产品排名<br/>  
 * 修  改  人: 詹昌高 <br/>
 * 修改日期:2016年12月24日<br/>
 * 修改内容:<br/>
 */
public class IndustryAreaEntity implements Serializable{
  
    private static final long serialVersionUID = 1L;
    
    
    private String name;//地区名称
    
    private String dnameEn;
    
    private Double value;//柜量或者重量
    
    private String valuePercent;


    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDnameEn() {
        return dnameEn;
    }

    public void setDnameEn(String dnameEn) {
        this.dnameEn = dnameEn;
    }

    public String getValuePercent() {
        return valuePercent;
    }

    public void setValuePercent(String valuePercent) {
        this.valuePercent = valuePercent;
    }

    
    
    
    
    
}
