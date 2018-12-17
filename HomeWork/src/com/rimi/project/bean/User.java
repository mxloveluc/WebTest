package com.rimi.project.bean;

/*
 * 创建一个java bean的对象
 * 这里，对象属性和mysql数据库一一对应
 * 允许将mysql数据，存储在java虚拟机里面
 */
public class User {
	
	private String id;
	private String userName;
	private String password;
	
	public User(String id, String userName, String password) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
	}
	
	public User(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	
	public User() {
		super();
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
