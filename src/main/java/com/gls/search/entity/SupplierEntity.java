/**
 * Copyright 2016 ECCloud Corporation. All rights reserved.
 *
 * --------------------------------------------------------
 * 此技术信息为本公司机密信息，未经本公司书面同意禁止向第三方披露.
 * 版权所有：贝恩国际
 * --------------------------------------------------------
 */

package com.gls.search.entity;

import com.gls.search.core.SolrEnum;
import com.gls.search.core.SolrField;

public class SupplierEntity implements Comparable<SupplierEntity>{
    
    
    private Integer id;
    @SolrField(key=SolrEnum.KEY,field=SolrEnum.FIELD)
    private Integer sid;
    @SolrField(field=SolrEnum.FIELD)
    private String sname;
//    @SolrField(field=SolrEnum.FIELD)
//    private Integer linkPhone;
    @SolrField(field=SolrEnum.FIELD)
    private String detailsAddress;
//    @SolrField(field=SolrEnum.FIELD)
//    private Integer logoUrl;
//    @SolrField(field=SolrEnum.FIELD)
    private String netUrl;
    @SolrField(field=SolrEnum.FIELD)
    private Integer totalCount;
    @SolrField(field=SolrEnum.FIELD)
    private Double totalWeight;
    @SolrField(field=SolrEnum.FIELD)
    private Double totalVolume;
    @SolrField(field=SolrEnum.FIELD)
    private String lastDate;
    @SolrField(field=SolrEnum.FIELD)
    private String lastProductDesc;
//    @SolrField(field=SolrEnum.FIELD)
    private String areaName;
//    @SolrField(field=SolrEnum.FIELD)
    private Integer did;
    
    private double sortStr;
   
    public double getSortStr() {
		return sortStr;
	}
	public void setSortStr(double sortStr) {
		this.sortStr = sortStr;
	}
	public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getSid() {
        return sid;
    }
    public void setSid(Integer sid) {
        this.sid = sid;
    }
//    public Integer getLinkPhone() {
//        return linkPhone;
//    }
//    public void setLinkPhone(Integer linkPhone) {
//        this.linkPhone = linkPhone;
//    }
    public String getDetailsAddress() {
        return detailsAddress;
    }
    public void setDetailsAddress(String detailsAddress) {
        this.detailsAddress = detailsAddress;
    }
//    public Integer getLogoUrl() {
//        return logoUrl;
//    }
//    public void setLogoUrl(Integer logoUrl) {
//        this.logoUrl = logoUrl;
//    }
    
    public String getNetUrl() {
        return netUrl;
    }
    public void setNetUrl(String netUrl) {
        this.netUrl = netUrl;
    }
    public String getSname() {
        return sname;
    }
    public void setSname(String sname) {
        this.sname = sname;
    }
    public Integer getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
    public Double getTotalWeight() {
        return totalWeight;
    }
    public void setTotalWeight(Double totalWeight) {
        this.totalWeight = totalWeight;
    }
    public Double getTotalVolume() {
        return totalVolume;
    }
    public void setTotalVolume(Double totalVolume) {
        this.totalVolume = totalVolume;
    }
    public String getLastDate() {
        return lastDate;
    }
    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }
    public String getLastProductDesc() {
        return lastProductDesc;
    }
    public void setLastProductDesc(String lastProductDesc) {
        this.lastProductDesc = lastProductDesc;
    }
    public String getAreaName() {
        return areaName;
    }
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
    public Integer getDid() {
        return did;
    }
    public void setDid(Integer did) {
        this.did = did;
    }
	@Override
	public int compareTo(SupplierEntity sup) {
		// TODO Auto-generated method stub
		int i =(int) (this.sortStr*1000);
		return (int) (this.sortStr*1000)-(int) (sup.sortStr*1000) ;
	}
    
    
    
    
    
}
