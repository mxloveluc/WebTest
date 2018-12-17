package com.rimi.project.dao;

import java.util.List;

import com.rimi.project.bean.User;

/*
 *  DAO: data access object数据访问对象
 *  使用过程中，DAO分两部分
 *  第一部分，DAO接口
 *  第二部分，DAO接口实现
 *  作用：从数据库读取数据
 *  	将数据存储到bean对象里面
 *  	返回用户所需要的bean对象等数据
 */
public interface UserDao {
	
	/* 插入一个user对象
	 * */
	boolean insertUser(User user);
	
	/*
	 * 读出所有user对象
	 */
	
	List<User> getAllUser();
	
	/* 
	 *  通过用户名和密码获取用户
	 */
	User getUserByUserNameAndPwd(String userName,String password);
	
	/*
	 *  修改密码
	 */
	boolean changeUserPassword(User user, String newPassword);
	
	/*
	 *  根据id删除用户
	 */
	boolean deleteUserById(String userId);
}
