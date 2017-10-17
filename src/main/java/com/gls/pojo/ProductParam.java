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
 * 文  件  名:ProductParam<br/>  
 * 文件描述: 接收参数传递<br/>  
 * 修  改  人: 陈取名 <br/>
 * 修改日期:2016年12月24日<br/>
 * 传参内容:cid:产品分类ID、did:地区分类ID、dlevel:地区等级、
 * ietype:进口:0  出口:1、
 * vwtype:柜量:0  重量:1<br/>
 */
public class ProductParam extends BasePojo{
   
    private Integer cid;        //产品分类ID
    private Integer did;        //地区ID
    private Integer dlevel;     //地区等级 , 全球:0， 国家:1， 省:2
    private Integer ietype;     //进出口参数 ,进口：0  出口：1
    private Integer vwtype;     //柜重类型,柜量：0，重量 ： 1
    private Integer clevel;     //行业级别
    private Integer pid;        //产品id
    private Integer shangJiaId; //采购商或者供应商id
    private Integer shangJiaId2; //采购商或者供应商id
    
    private String shangJiaIds;  //详情页供应商/采购商趋势所需传递
    
    private Integer pageNo; 
    private Integer pageSize;
    
    private Integer id;
    
    private Integer date_type;
    
    
    private Integer countBy; //根据交易次数排序，0：降序，1：升序
    private Integer containerBy; //根据柜量排序，0：降序，1：升序
    private Integer weightBy; //根据重量排序，0：降序，1：升序
    
    private String companyWebsite;//公司网址，查询联系人用到
    private String guid;//公司id
    private String empId;//员工id 
    private String empGuid;//员工Guid
    private String name;//公司名
    private String position;//职位
    private String linkPhone;//联系电话
    private String email;//邮件
    private String proKey;
    
    
    
	public Integer getDate_type() {
		return date_type;
	}



	public void setDate_type(Integer date_type) {
		this.date_type = date_type;
	}



	public void setProKey(String proKey) {
		this.proKey = proKey;
	}



	public String getEmpGuid() {
		return empGuid;
	}



	public void setEmpGuid(String empGuid) {
		this.empGuid = empGuid;
	}



	public String getEmpId() {
		return empId;
	}



	public void setEmpId(String empId) {
		this.empId = empId;
	}



	public String getPosition() {
		return position;
	}



	public void setPosition(String position) {
		this.position = position;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public Integer getPageNo() {
        return pageNo;
    }



    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }



    public Integer getPageSize() {
        return pageSize;
    }



    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }



    public String getShangJiaIds() {
        return shangJiaIds;
    }



    public void setShangJiaIds(String shangJiaIds) {
        this.shangJiaIds = shangJiaIds;
    }



    public Integer getCid() {
        return cid;
    }



    public void setCid(Integer cid) {
        this.cid = cid;
    }



    public Integer getDid() {
        return did;
    }



    public void setDid(Integer did) {
        this.did = did;
    }



    public Integer getDlevel() {
        return dlevel;
    }



    public void setDlevel(Integer dlevel) {
        this.dlevel = dlevel;
    }



    public Integer getIetype() {
        return ietype;
    }



    public Integer getShangJiaId2() {
		return shangJiaId2;
	}



	public void setShangJiaId2(Integer shangJiaId2) {
		this.shangJiaId2 = shangJiaId2;
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public void setIetype(Integer ietype) {
        this.ietype = ietype;
    }



    public Integer getCountBy() {
		return countBy;
	}



	public String getGuid() {
		return guid;
	}



	public void setGuid(String guid) {
		this.guid = guid;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getLinkPhone() {
		return linkPhone;
	}



	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}



	public void setCountBy(Integer countBy) {
		this.countBy = countBy;
	}



	public Integer getContainerBy() {
		return containerBy;
	}



	public void setContainerBy(Integer containerBy) {
		this.containerBy = containerBy;
	}



	public Integer getWeightBy() {
		return weightBy;
	}



	public String getCompanyWebsite() {
		return companyWebsite;
	}



	public void setCompanyWebsite(String companyWebsite) {
		this.companyWebsite = companyWebsite;
	}



	public void setWeightBy(Integer weightBy) {
		this.weightBy = weightBy;
	}



	public Integer getVwtype() {
        return vwtype;
    }



    public void setVwtype(Integer vwtype) {
        this.vwtype = vwtype;
    }



    public Integer getClevel() {
        return clevel;
    }


    public void setClevel(Integer clevel) {
        this.clevel = clevel;
    }


    public Integer getPid() {
        return pid;
    }


    public void setPid(Integer pid) {
        this.pid = pid;
    }


    public Integer getShangJiaId() {
        return shangJiaId;
    }


    public void setShangJiaId(Integer shangJiaId) {
        this.shangJiaId = shangJiaId;
    }



    @Override
	public String toString() {
		return "ProductParam [cid=" + cid + ", did=" + did + ", dlevel=" + dlevel + ", ietype=" + ietype + ", vwtype="
				+ vwtype + ", clevel=" + clevel + ", pid=" + pid + ", shangJiaId=" + shangJiaId + ", shangJiaId2="
				+ shangJiaId2 + ", shangJiaIds=" + shangJiaIds + ", pageNo=" + pageNo + ", pageSize=" + pageSize
				+ ", id=" + id + ", date_type=" + date_type + ", countBy=" + countBy + ", containerBy=" + containerBy
				+ ", weightBy=" + weightBy + ", companyWebsite=" + companyWebsite + ", guid=" + guid + ", empId="
				+ empId + ", empGuid=" + empGuid + ", name=" + name + ", position=" + position + ", linkPhone="
				+ linkPhone + ", email=" + email + ", proKey=" + proKey + "]";
	}



	public ProductParam(Integer cid, Integer did, Integer dlevel, Integer ietype, Integer vwtype, Integer clevel,
        Integer pid, Integer shangJiaId, String shangJiaIds, Integer pageNo, Integer pageSize) {
        super();
        this.cid = cid;
        this.did = did;
        this.dlevel = dlevel;
        this.ietype = ietype;
        this.vwtype = vwtype;
        this.clevel = clevel;
        this.pid = pid;
        this.shangJiaId = shangJiaId;
        this.shangJiaIds = shangJiaIds;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }



    public ProductParam() {
        super();
    }



	public String getProKey() {
		return proKey;
	}



	public void setProkey(String proKey) {
		this.proKey = proKey;
	}
    
}
