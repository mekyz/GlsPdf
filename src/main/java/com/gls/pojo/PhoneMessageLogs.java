package com.gls.pojo;

import java.util.Date;

public class PhoneMessageLogs {
    private Long id;//自增id,

    private Long mobilephone;//手机号码

    private String content;//消息内容

    private Byte process;//发送标识，0：未处理，1：已处理

    private String remark;//备注

    private Date sendTime;//发送时间

    private Date createTime;//添加时间

    private Date updateTime;//最后修改时间

    private Byte isDeleted;//删除标识,0.可用，1.已删除不可用

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(Long mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Byte getProcess() {
        return process;
    }

    public void setProcess(Byte process) {
        this.process = process;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }
}