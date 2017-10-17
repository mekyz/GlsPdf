package com.gls.pojo;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * @author meky
 * <br/><pre>
 * CREATE TABLE `orders` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id,订单id',
  `order_sn` varchar(40) NOT NULL DEFAULT '' COMMENT '订单sn码,考虑扩展性，设成char',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `title` varchar(40) NOT NULL DEFAULT '' COMMENT '订单商品描述，如积分充值',
  `points` int(11) NOT NULL DEFAULT '0' COMMENT '点数，如100，充值成功后需要累加用户积分',
  `all_price` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '充值金额, -999999.99 到 9999999.99, 总共price',
  `order_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用户订单状态, 0:待支付; 1:已完成',
  `pay_type` varchar(12) NOT NULL DEFAULT '0' COMMENT '用户支付方式 0:还没支付,31:到付; alipay; wx; bfb:百度钱包; alipay_wap; wx_pub;',
  `charge_id` varchar(40) NOT NULL DEFAULT '0' COMMENT 'ping++支付的charge id, 一般是付款完成后才insert进去的, 后来用作ping++的订单 id',
  `pay_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '支付时间',
  `description` varchar(80) NOT NULL DEFAULT '' COMMENT '用户备注',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识,0.可用，1.已删除不可用',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='充值记录订单表';
 *</pre>
 */
//作为前端传过来的一个方便的对象1
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Orders  extends BasePojo  {
	private long order_id;//order_id
	private long userId;//用户id
	private String order_sn;//订单sn码
	private String title;//订单商品描述，如积分充值
	private Integer points;//点数，如100，充值成功后需要累加用户积分
	private Double all_price;//充值金额
	private int order_type;//用户订单状态, 0:待支付; 1:已完成
	private String pay_type;//用户支付方式 0:还没支付,31:到付; alipay; wx; bfb:百度钱包; alipay_wap; wx_pub
	private String description;//用户备注
	private Date createTime;//添加时间
    private Date updateTime;//最后修改时间
	public long getOrder_id() {
		return order_id;
	}
	public void setOrder_id(long order_id) {
		this.order_id = order_id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getOrder_sn() {
		return order_sn;
	}
	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
	public Double getAll_price() {
		return all_price;
	}
	public void setAll_price(Double all_price) {
		this.all_price = all_price;
	}
	public int getOrder_type() {
		return order_type;
	}
	public void setOrder_type(int order_type) {
		this.order_type = order_type;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	 * @param title
	 * @param points
	 * @param all_price
	 * @param order_type
	 * 
	 */
	public Orders(long userId, String title, Integer points, Double all_price, int order_type) {
		super();
		this.userId = userId;
		this.title = title;
		this.points = points;
		this.all_price = all_price;
		this.order_type = order_type;
		
	}
	public Orders(){}

}
