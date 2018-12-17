package com.rimi.project.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rimi.project.bean.User;
import com.rimi.project.dao.UserDao;
import com.rimi.project.util.JDBCUtil;
import com.rimi.project.util.Pool;

public class UserDaoMysqlImpl implements UserDao{

	@Override
	public boolean insertUser(User user) {
		String sql = "insert into user(username,password) values(?,?)";
		Object[] params = new Object[] {user.getUserName(),user.getPassword()};
		return JDBCUtil.preUpdate(sql, params);
	}

	/*
	 * (non-Javadoc)
	 * @see com.rimi.project.dao.UserDao#getAllUser()
	 * 从mysql数据库取回所有用户对象
	 */
	@Override
	public List<User> getAllUser() {
		String sql = "select * from user";
		
		// 利用JSBCUTil从数据库取回数据
		ResultSet resultSet = JDBCUtil.preSelect(sql, null);

		List<User> users = new ArrayList<>();
		try {
			while(resultSet.next()) {
				// 每一行数据表数据创建一个新的user对象
				User user = new User();
				// 将数据表数据写入user对象
				user.setId(resultSet.getString("id"));
				user.setUserName(resultSet.getString("username"));
				user.setPassword(resultSet.getString("password"));
				// 将一行数据对应的user对象加入到list集合
				users.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return users;
	}

	@Override
	public User getUserByUserNameAndPwd(String userName, String password) {
		String sql = "select * from user where username = ? and password = ?";
		Object[] params = new Object[] {userName,password};
		ResultSet resultSet = JDBCUtil.preSelect(sql, params);
		User user = null;
		try {
			while(resultSet.next()) {
				user = new User(resultSet.getString("id"), 
						resultSet.getString("username"),
						resultSet.getString("password"));
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return user;
	}

	@Override
	public boolean changeUserPassword(User user, String newPassword) {
		String sql = "update user set password = ? where id = ?";
		Object[] params = new Object[] {newPassword,user.getId()};
		return JDBCUtil.preUpdate(sql, params);
	}

	@Override
	public boolean deleteUserById(String userId) {
		String sql = "delete from user where id = ?";
		Object[] params = new Object[] {userId}; 
		return JDBCUtil.preUpdate(sql, params);
	}

}
