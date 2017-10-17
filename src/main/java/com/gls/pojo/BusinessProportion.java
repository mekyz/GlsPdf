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
 * 文  件  名:BusinessProportion<br/>  
 * 文件描述: 采购产品比重(详情页)<br/>  
 * 修  改  人: 陈取名 <br/>
 * 修改日期:2017年1月17日<br/>
 * 修改内容:<br/>
 */
public class BusinessProportion {
    
    private Integer pid;
    private String name;
    private double value;
    private String baifenbi;
   
    public Integer getPid() {
        return pid;
    }
    public void setPid(Integer pid) {
        this.pid = pid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        this.value = value;
    }
    public String getBaifenbi() {
        return baifenbi;
    }
    public void setBaifenbi(String baifenbi) {
        this.baifenbi = baifenbi;
    }
    
    
}
