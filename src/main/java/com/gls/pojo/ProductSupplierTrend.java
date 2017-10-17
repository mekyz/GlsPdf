/**
 * Copyright 2016 ECCloud Corporation. All rights reserved.
 *
 * --------------------------------------------------------
 * 此技术信息为本公司机密信息，未经本公司书面同意禁止向第三方披露.
 * 版权所有：贝恩国际
 * --------------------------------------------------------
 */

package com.gls.pojo;

import java.util.List;

public class ProductSupplierTrend {
    
    private Integer id;
    private String name;
    private List<Trend> trends;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
   
    public List<Trend> getTrends() {
        return trends;
    }

    public void setTrends(List<Trend> trends) {
        this.trends = trends;
    }

    public ProductSupplierTrend(Integer id, String name, List<Trend> trends) {
        super();
        this.id = id;
        this.name = name;
        this.trends = trends;
    }

    public ProductSupplierTrend() {
        super();
    }
    
}
