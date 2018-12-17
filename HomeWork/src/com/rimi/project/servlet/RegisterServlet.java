package com.rimi.project.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.rimi.project.service.UserService;
import com.rimi.project.service.impl.UserServiceImpl;

import sun.rmi.server.Dispatcher;

public class RegisterServlet extends HttpServlet{

	UserService userService = new UserServiceImpl();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 设置字符集
		 */
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		
		// 获取注册页面提交的参数
		String userName = req.getParameter("username");
		String password = req.getParameter("password");
		
		// 判断是否为空
		if(null != userName && !"".equals(userName)
				&& null != password && !"".equals(password)) {
			if(userService.register(userName, password)) {
				// 注册成功，跳转到登陆页面
				resp.sendRedirect(req.getContextPath() + "/html/login.html");
			} else {
				resp.sendRedirect(req.getContextPath() + "/html/register.html");
			}
		}
//		if(StringUtils.isEmpty(username)) {
//			
//		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 直接访问get方法，强制跳转注册页面
		RequestDispatcher dispatcher = req.getRequestDispatcher("html/register.html");
		dispatcher.forward(req, resp);
	}
}
