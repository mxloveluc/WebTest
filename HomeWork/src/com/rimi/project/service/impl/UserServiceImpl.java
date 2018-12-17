package com.rimi.project.service.impl;

import com.rimi.project.bean.User;
import com.rimi.project.dao.UserDao;
import com.rimi.project.dao.impl.UserDaoMysqlImpl;
import com.rimi.project.service.UserService;

public class UserServiceImpl implements UserService{
	// 数据访问对象
	UserDao userDao = new UserDaoMysqlImpl();
	
	@Override
	public boolean checkUser(String userName, String password) {

		User user = userDao.getUserByUserNameAndPwd(userName, password);
		if(null != user) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean register(String userName, String password) {
		// 创建插入数据库用的user对象
		User user = new User();
		user.setPassword(password);
		user.setUserName(userName);
		return userDao.insertUser(user);
	}

}
