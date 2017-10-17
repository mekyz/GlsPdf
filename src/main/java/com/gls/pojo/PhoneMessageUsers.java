package com.gls.pojo;

import java.util.Date;

public class PhoneMessageUsers {
    private Long id;//自增id,

    private String userName;//短信接收人

    private String gender;//性别：男，女

    private Long mobilephone;//手机号码

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public Long getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(Long mobilephone) {
        this.mobilephone = mobilephone;
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