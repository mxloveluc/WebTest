package com.rimi.project.test;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.rimi.project.util.JDBCUtil;

public class Test {

	public static void main(String[] args) {
		ResultSet resultSet = JDBCUtil.preSelect("select * from user", null);
		try {
			while(resultSet.next()) {
				System.out.println(resultSet.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
