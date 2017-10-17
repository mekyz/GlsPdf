package com.gls.pojo;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;
/**
 * @author meky
 * <br/><pre>
 * CREATE TABLE `user_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `all_points` int(11) NOT NULL DEFAULT '0' COMMENT '总点数，用于前端显示',
  `free_points` int(11) NOT NULL DEFAULT '0' COMMENT '赠送的免费点数',
  `pay_points` int(11) NOT NULL DEFAULT '0' COMMENT '充值支付的点数，用途不一样',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识,0.可用，1.已删除不可用',
  PRIMARY KEY (`id`),
  KEY `k_userid` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户账户';

 *</pre>
 */
//作为前端传过来的一个方便的对象1
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class UserAccount  extends BasePojo  {
	private long id;//id
	private long userId;//用户id
	private Integer all_points;//总点数，用于前端显示
	private Integer free_points;//赠送的免费点数
	private Integer pay_points;//充值支付的点数，用途不一样
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
	public Integer getAll_points() {
		return all_points;
	}
	public void setAll_points(Integer all_points) {
		this.all_points = all_points;
	}
	public Integer getFree_points() {
		return free_points;
	}
	public void setFree_points(Integer free_points) {
		this.free_points = free_points;
	}
	public Integer getPay_points() {
		return pay_points;
	}
	public void setPay_points(Integer pay_points) {
		this.pay_points = pay_points;
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
	/**
	 * @param userId
	 */
	public UserAccount(long userId) {
		super();
		this.userId = userId;
	}
	public UserAccount(){}
}
