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

public class TenantsAmount implements Serializable{
    
    private Integer tenantsAmount;
    
    private Integer value;

    public Integer getTenantsAmount() {
        return tenantsAmount;
    }

    public void setTenantsAmount(Integer tenantsAmount) {
        this.tenantsAmount = tenantsAmount;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
    
    
    
    
}
