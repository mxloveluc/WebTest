package com.rimi.project.service;

public interface UserService {

	boolean checkUser(String userName,String password);
	
	boolean register(String userName,String password);
}
