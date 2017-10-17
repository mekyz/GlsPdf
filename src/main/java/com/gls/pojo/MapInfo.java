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
 * 文  件  名:MapInfo<br/>  
 * 文件描述:地图实体<br/>  
 * 修  改  人: 陈取名 <br/>
 * 修改日期:2017年1月23日<br/>
 * 修改内容:<br/>
 */
public class MapInfo {
    
    private Integer value;
    
    private String name;
    
    private String latitude;
    
    private String longitude;
    
    private Integer did;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }
    
    
    
}
