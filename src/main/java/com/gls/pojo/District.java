package com.gls.pojo;

import java.util.List;
/**
 * 
 * 文  件  名:District<br/>  
 * 文件描述:地区分类 实体<br/>  
 * 修  改  人: 陈取名 <br/>
 * 修改日期:2016年12月28日<br/>
 * 修改内容:<br/>
 */
public class District {
    private Integer did;//自增id,

    private String title;//地区名

    private String dnameEn;//地区对应的英文

   // private String dnameCode;//地区简称

    private Integer level;//1 一级,2 二级,3 三级,4 四级）

    private Integer pid;//上一级id

    private String longitude;//经度

    private String latitude;//纬度

   // private Date createTime;//添加时间

   // private Date updateTime;//最后修改时间

   // private Byte isDeleted;//删除标识,0.可用，1.已删除不可用
    
    private List<District> content;

    public List<District> getContent() {
        return content;
    }

    public void setContent(List<District> content) {
        this.content = content;
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

    public String getDnameEn() {
        return dnameEn;
    }

    public void setDnameEn(String dnameEn) {
        this.dnameEn = dnameEn == null ? null : dnameEn.trim();
    }

    

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }


    public String getLongitude() {
        return longitude;
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

    public District(Integer did, String title, String dnameEn, Integer level, Integer pid, String longitude,
        String latitude, List<District> content) {
        super();
        this.did = did;
        this.title = title;
        this.dnameEn = dnameEn;
        this.level = level;
        this.pid = pid;
        this.longitude = longitude;
        this.latitude = latitude;
        this.content = content;
    }

    public District() {
        super();
    }
    
    
}