package com.gls.pojo;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * @author meky
 * <br/><pre>
 * CREATE TABLE `user_bill` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `title` varchar(50) NOT NULL DEFAULT '' COMMENT '查看的内容，系统自动生成',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '查看的收费项目类型，0：联系人，1：报告，2xx',
  `detail_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '保存各个类型查看的内容对应的ID',
  `points` int(11) NOT NULL DEFAULT '0' COMMENT '点数，正负区分，充值为正，扣除为负',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识,0.可用，1.已删除不可用',
  PRIMARY KEY (`id`),
  KEY `k_userid` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='用户历史查看的积分账单';
 * 
 *</pre>
 */
//作为前端传过来的一个方便的对象1
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class UserBill  extends BasePojo  {
	private long id;//id
	private long userId;//用户id
	private String title;//查看的内容，系统自动生成
	private int type;//查看的收费项目类型，0：联系人，1：报告，2xx
	private long detailId;//保存各个类型查看的内容对应的ID
	private Integer points;//点数，正负区分，充值为正，扣除为负
	private Date createTime;//添加时间
    private Date updateTime;//最后修改时间
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getDetailId() {
		return detailId;
	}
	public void setDetailId(long detailId) {
		this.detailId = detailId;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
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
	public UserBill(long userId) {
		super();
		this.userId = userId;
	}
	public UserBill(){}
    

}
