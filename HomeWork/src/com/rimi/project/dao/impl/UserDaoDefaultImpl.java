package com.rimi.project.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.rimi.project.bean.User;
import com.rimi.project.dao.UserDao;
import com.rimi.project.util.JDBCUtil;

public class UserDaoDefaultImpl implements UserDao{

	@Override
	public boolean insertUser(User user) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		List<User> users = new ArrayList<User>();
		User user = new User();
		user.setUserName("luc");
		user.setPassword("123");
		users.add(user);
		return users;
	}

	@Override
	public User getUserByUserNameAndPwd(String userName, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean changeUserPassword(User user, String newPassword) {
		String sql = "update user set password = ? where id = ?";
		Object[] params = new Object[] {newPassword,user.getId()};
		return JDBCUtil.preUpdate(sql, params);
	}

	@Override
	public boolean deleteUserById(String userId) {
		// TODO Auto-generated method stub
		return false;
	}

}
