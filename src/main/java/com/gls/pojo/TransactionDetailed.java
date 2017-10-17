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
 * 文  件  名:TransactionDetailed<br/>  
 * 文件描述:交易清单相关实体类<br/>  
 * 修  改  人: 陈取名 <br/>
 * 修改日期:2017年3月8日<br/>
 * 修改内容:<br/>
 */
public class TransactionDetailed {
    private Integer id; //id
    private String name; //供应商/采购商名称
    private String productName; //产品名称
    private Integer count; //贸易次数
    private Double totalVolume; //尺柜量
    private String volumeProportion; //尺柜占比
    private Double totalWeight; //重量
    private String weightProportion; //重量占比
    private String latelyDate; //最近交易时间
    private Integer comCount;//公司数量
    private String dname;//所属国家
    
    
    
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
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }
    public Double getTotalVolume() {
        return totalVolume;
    }
    public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public Integer getComCount() {
		return comCount;
	}
	public void setComCount(Integer comCount) {
		this.comCount = comCount;
	}
	public void setTotalVolume(Double totalVolume) {
        this.totalVolume = totalVolume;
    }
    public String getVolumeProportion() {
        return volumeProportion;
    }
    public void setVolumeProportion(String volumeProportion) {
        this.volumeProportion = volumeProportion;
    }
    public Double getTotalWeight() {
        return totalWeight;
    }
    public void setTotalWeight(Double totalWeight) {
        this.totalWeight = totalWeight;
    }
    public String getWeightProportion() {
        return weightProportion;
    }
    public void setWeightProportion(String weightProportion) {
        this.weightProportion = weightProportion;
    }
    
    public String getLatelyDate() {
        return latelyDate;
    }
    public void setLatelyDate(String latelyDate) {
        this.latelyDate = latelyDate;
    }
    
    public TransactionDetailed(Integer id, String name, String productName, Integer count, Double totalVolume,
        String volumeProportion, Double totalweight, String weightProportion, String latelyDate,Integer comCount) {
        super();
        this.id = id;
        this.name = name;
        this.productName = productName;
        this.count = count;
        this.totalVolume = totalVolume;
        this.volumeProportion = volumeProportion;
        this.totalWeight = totalweight;
        this.weightProportion = weightProportion;
        this.latelyDate = latelyDate;
        this.comCount = comCount;
    }
    public TransactionDetailed() {
        super();
    }
    
}
