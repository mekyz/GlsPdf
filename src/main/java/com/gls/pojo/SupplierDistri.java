/**
 * Copyright 2016 ECCloud Corporation. All rights reserved.
 *
 * --------------------------------------------------------
 * 此技术信息为本公司机密信息，未经本公司书面同意禁止向第三方披露.
 * 版权所有：贝恩国际
 * --------------------------------------------------------
 */

package com.gls.pojo;
/**
 * 
 * 文  件  名:SupplierDistri<br/>  
 * 文件描述:产品供应商分布比重图实体<br/>  
 * 修  改  人: 陈取名 <br/>
 * 修改日期:2016年12月29日<br/>
 * 修改内容:<br/>
 */
public class SupplierDistri {
    
    //private Integer did;
    
    private String name;
    
    private Double value;
    
    private String Baifei; //百分比

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getBaifei() {
        return Baifei;
    }

    public void setBaifei(String baifei) {
        Baifei = baifei;
    }
}
