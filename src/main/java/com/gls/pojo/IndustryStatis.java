/**
 * Copyright 2016 ECCloud Corporation. All rights reserved.
 *
 * --------------------------------------------------------
 * 此技术信息为本公司机密信息，未经本公司书面同意禁止向第三方披露.
 * 版权所有：贝恩国际
 * --------------------------------------------------------
 */

package com.gls.pojo;

import java.io.Serializable;

public class IndustryStatis implements Serializable{
    
    private Double value;//柜量，重量
    
    private String statisTime;//统计时间段

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
    
    
    
}
