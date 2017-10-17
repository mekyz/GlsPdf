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
public class IndustryProductEntity implements Serializable{
  
    private static final long serialVersionUID = 1L;
    
    private Integer pid;//产品id
    
    private String pname;//产品名称
    
    private Double value;//柜量或者重量

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
    
    
    
}
