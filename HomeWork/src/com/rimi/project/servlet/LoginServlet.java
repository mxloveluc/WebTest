package com.rimi.project.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.rimi.project.service.UserService;
import com.rimi.project.service.impl.UserServiceImpl;


public class LoginServlet extends HttpServlet{

	UserService userService = new UserServiceImpl();
	
	/*
	 * 登录使用post,目前只支持表单
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 设置字符集
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		
		// 获取用户名和密码
		String userName = req.getParameter("username");
		String password = req.getParameter("password");
		System.out.println(userName);
		System.out.println(password);
		
		if(userService.checkUser(userName, password)) {
//			resp.sendRedirect("/HomeWork/html/index.html");
//			RequestDispatcher requestDispatcher = req.getRequestDispatcher("html/index.html");
//			requestDispatcher.forward(req, resp);
			resp.getWriter().append("登陆成功");
		} else {
			resp.getWriter().append("登录失败");
		}
	}
	
	/*
	 *  调用get方法时，强制调用post方法
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	
}
