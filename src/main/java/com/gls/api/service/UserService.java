package com.gls.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gls.api.service.rabbitmq.RabbitQueueService;
import com.gls.api.service.redis.RedisService;
import com.gls.dao.mapper.UsersMapper;
import com.gls.pojo.Orders;
import com.gls.pojo.ProductParam;
import com.gls.pojo.UserAccount;
import com.gls.pojo.UserBill;
import com.gls.pojo.UserSearchLog;
import com.gls.pojo.Users;

@Service
public class UserService extends RedisService {
	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
    private RabbitQueueService rabbitQueueService;
	
	
	//验证用户密码
	public int varidateUserPsword(Users user) {
		return usersMapper.varidateUserPsword(user);
	}
	//退出
	public void logout(Users user) {
		redisTemplate.delete(user.getToken());
	}
	public List<Users> seleUser4Phone(Users user) {
		return usersMapper.seleUser4Phone(user);
	}
	//注册用户
	public void insertUser(Users user){
		 usersMapper.insertUser(user);
	} 
	//检验用户名(user_name)是否存在
	public int checkUserName(Users user){
		
		return usersMapper.checkUserName(user);
	}
	//admin.g2l-service.com 用户审核 使用
	public List<Users> getUserList(Users user){
		return usersMapper.getUserList(user);
	}
	public Users getUserById(Users user){
		return usersMapper.getUserById(user);
	}
	public int getUserCount(Users user){
		return usersMapper.getUserCount(user);
	}
	//审核
	public void setUserStart(Users user){
		usersMapper.setUserStart(user);
	}
	//检验用户id(id)是否存在用户账户 user_account
    public int checkAccount(UserAccount userAccount){
		
		return usersMapper.checkAccount(userAccount);
	}
    //插入用户账户
    public void insertAccount(UserAccount userAccount){
		 usersMapper.insertAccount(userAccount);
	}
    //修改用户账户
  	public void setAccount(UserAccount userAccount){
  		 usersMapper.setAccount(userAccount);
  	}
     //查询用户账户
  	public  UserAccount findAccountByUserId(UserAccount userAccount){
  		return usersMapper.findAccountByUserId(userAccount);
  	}
  	//用户历史查看的积分账单
  	//插入 积分 账单记录
  	public void insertUserBill(UserBill userBill){
		 usersMapper.insertUserBill(userBill);
	}
  	//获取    积分 账单记录 
  	public List<UserBill> getUserBillList(UserBill userBill){
		return usersMapper.getUserBillList(userBill);
	}
  	//统计积分 账单记录  总数
	public int getUserBillListCount(UserBill userBill){
		return usersMapper.getUserBillListCount(userBill);
	}
	//
    public void insertOrders(Orders orders){
		 usersMapper.insertOrders(orders);
	}
    public List<UserBill> getDetaukIdBillList(UserBill userBill){
    	
    	return usersMapper.getDetaukIdBillList(userBill);
    }
    //查看当前用户是否查看过当前报告
    public boolean checkSee(ProductParam productParam){
    	boolean flag;
    	UserBill userBill=new UserBill();
    	userBill.setDetailId(productParam.getDid());
    	userBill.setUserId(productParam.getUserId());
    	userBill.setType(productParam.getType());
    	List<UserBill> usercont=this.getDetaukIdBillList(userBill);
    	if (usercont.size()>0) {
    		flag=true;
		}else{
			flag=false;
		}
    	return flag;
    }
    //
	public boolean deductionIntegral(ProductParam productParam) {
		boolean flag=false;
		UserAccount userAccount=new UserAccount(productParam.getUserId());
		
		int count=this.checkAccount(userAccount);
		if (count>0) {
			int Points=0;
			int payPints=0;
			int FreePints=0;
			userAccount= this.findAccountByUserId(userAccount);
			if(productParam.getType()==0){
				//查看联系人 type=0   5积分
			flag=userAccount.getPay_points()>=5?true:false;
			Points=5;
			}else if(productParam.getType()==1){
				//查看报告 type=1 100积分
				flag=userAccount.getAll_points()>=100?true:false;
				Points=100;
			}
				if(flag){
					UserBill userBill=new UserBill();
					userBill.setUserId(productParam.getUserId());
					userBill.setTitle(productParam.getTitle());
					userBill.setType(productParam.getType());
					userBill.setDetailId(productParam.getDid());
					userBill.setPoints(-Points);
					this.insertUserBill(userBill);//插入交易记录
					if(productParam.getType()==1){
						if(userAccount.getFree_points()>=Points){
							FreePints=Points;
							payPints=0;
						}else{
							FreePints=userAccount.getFree_points();
							payPints=Points-userAccount.getFree_points();
						}
						
					}else if(productParam.getType()==0){
						FreePints=0;//查詢不扣系統贈送積分
						payPints=5;
					}
					userAccount.setFree_points(userAccount.getFree_points()-FreePints);
					userAccount.setPay_points(userAccount.getPay_points()-payPints);
					userAccount.setAll_points(userAccount.getAll_points()-payPints);
					 usersMapper.setAccount(userAccount);//修改当前金额
				}
			
			}
		
    	return flag;
	}
	//插入查询的记录
	public void insertUserSearchLog(UserSearchLog userSearchLog){
		 usersMapper.insertUserSearchLog(userSearchLog);
	}
	//显示查询的记录 历史user_search_log
   public List<UserSearchLog> getSearchLogListByUserId(UserSearchLog userSearchLog){
   	
   	return usersMapper.getSearchLogListByUserId(userSearchLog);
   }

   public Orders getOrder(Orders orders){
   	return usersMapper.getOrder(orders);
   }
    
}
