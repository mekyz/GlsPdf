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

/**
 * 
 * 文  件  名:MapClickInfo<br/>  
 * 文件描述:地图信息辅助接口，获取点击地图的具体国家信息<br/>  
 * 修  改  人: 陈取名 <br/>
 * 修改日期:2017年1月10日<br/>
 * 修改内容:<br/>
 */
public class MapClickInfo {

    private Integer did;//自增id,

    private String title;//地区名

    //private String dnameEn;//地区对应的英文

    private String longitude;//经度

    private String latitude;//纬度
    
    private Integer value;
    
    private  List<MapInfo> mapInfo;
    
    

    public List<MapInfo> getMapInfo() {
        return mapInfo;
    }

    public void setMapInfo(List<MapInfo> mapInfo) {
        this.mapInfo = mapInfo;
    }

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLongitude() {
        return longitude;
    }

    public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

   

    public MapClickInfo(Integer did, String title, String longitude, String latitude, List<MapInfo> mapInfo) {
        super();
        this.did = did;
        this.title = title;
        this.longitude = longitude;
        this.latitude = latitude;
        this.mapInfo = mapInfo;
    }

    public MapClickInfo() {
        super();
    }
    
    
}
