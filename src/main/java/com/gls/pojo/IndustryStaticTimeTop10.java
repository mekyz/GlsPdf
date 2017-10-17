/**
 * Copyright 2016 ECCloud Corporation. All rights reserved.
 *
 * --------------------------------------------------------
 * 此技术信息为本公司机密信息，未经本公司书面同意禁止向第三方披露.
 * 版权所有：贝格
 * --------------------------------------------------------
 */

package com.gls.pojo;

import java.io.Serializable;
import java.util.List;

public class IndustryStaticTimeTop10 implements Serializable{
    
    private Integer cid; //行业id
    
    private String cname;//行业名称
    
    private String cnameEn;//英文
    
    private Integer clevel;
    
    private List<IndustryStatis> statisList;
    

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }


    public String getCnameEn() {
        return cnameEn;
    }

    public void setCnameEn(String cnameEn) {
        this.cnameEn = cnameEn;
    }

    public Integer getClevel() {
        return clevel;
    }

    public void setClevel(Integer clevel) {
        this.clevel = clevel;
    }

    public List<IndustryStatis> getStatisList() {
        return statisList;
    }

    public void setStatisList(List<IndustryStatis> statisList) {
        this.statisList = statisList;
    }

    
    
}
