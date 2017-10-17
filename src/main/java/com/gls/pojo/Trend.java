/**
 * Copyright 2016 ECCloud Corporation. All rights reserved.
 *
 * --------------------------------------------------------
 * 此技术信息为本公司机密信息，未经本公司书面同意禁止向第三方披露.
 * 版权所有：贝恩国际
 * --------------------------------------------------------
 */

package com.gls.pojo;

public class Trend {
    private Double value;
    private String yearMonth;
    public Double getValue() {
        return value;
    }
    public void setValue(Double value) {
        this.value = value;
    }
    public String getYearMonth() {
        return yearMonth;
    }
    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }
   
    
    public Trend(Double value, String yearMonth) {
        super();
        this.value = value;
        this.yearMonth = yearMonth;
    }
    public Trend() {
        super();
    }
    
    
}
