package com.gls.dao.mapper;

import java.util.List;

import com.gls.pojo.Orders;
import com.gls.pojo.UserAccount;
import com.gls.pojo.UserBill;
import com.gls.pojo.UserSearchLog;
import com.gls.pojo.Users;

public interface UsersMapper {
	
	public List<Users> seleUser4Phone(Users user);
	
	public int varidateUserPsword(Users user);
	//===============
	public void insertUser(Users user);

	public int checkUserName(Users user);

	public List<Users> getUserList(Users user);
	
	public Users getUserById(Users user);

	public void setUserStart(Users user);

	public int getUserCount(Users user);

	public void insertAccount(UserAccount userAccount);

	public void setAccount(UserAccount userAccount);

	public int checkAccount(UserAccount userAccount);

	public UserAccount findAccountByUserId(UserAccount userAccount);

	public void insertUserBill(UserBill userBill);

	public List<UserBill> getUserBillList(UserBill userBill);

	public int getUserBillListCount(UserBill userBill);

	public void insertOrders(Orders orders);
	
	public List<UserBill> getDetaukIdBillList(UserBill userBill);
	
	public void insertUserSearchLog(UserSearchLog userSearchLog);
	
	public List<UserSearchLog> getSearchLogListByUserId(UserSearchLog userSearchLog);
	
	public Orders getOrder(Orders order);
	
	

}