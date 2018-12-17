package com.rimi.project.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.sun.rowset.CachedRowSetImpl;
public class JDBCUtil {

	// 驱动包名+类名
	private static String driver = null;
	// 用户名
	private static String userName = null;
	// 密码
	private static String password = null;
	// 数据库地址
	// jdbc:mysql: 等同于http:
	private static String url = null;

	/*
	 *  静态代码块
	 *  当前类第一次被加载的java虚拟机时，加载，且只加载一次
	 */
	static {
		// 默认在类加载路径获取配置文件
		InputStream inputStream = JDBCUtil.class.getClassLoader().getResourceAsStream("datasource.properties");
		
		// 新建一个java properties工具类
		Properties properties = new Properties();
		
		try {
			// 用load将inputstream加载到Properties工具类
			properties.load(inputStream);
			/*
			 *  从properties文件读出参数值
			 */
			driver = properties.getProperty("driver");
			userName = properties.getProperty("username");
			password = properties.getProperty("password");
			url = properties.getProperty("url");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭inputstram
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// 加载驱动
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 使用默认配置建立连接
	 */
	public static Connection getConnection() {
		return getConnection(url, userName, password);
		// 下面等同于下面
/*		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(url, userName, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;*/
		
	}
	
	/*
	 * 自定义配置，建立连接
	 */
	public static Connection getConnection(String url,String userName,String password) {
		
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(url, userName, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	
	/*
	 *  不指定链接执行sql
	 */
	public static boolean update(String sql) {
		Connection connection = getConnection();
		return update(connection, sql);
	}
	
	/* 
	 *  允许通过给定的数据库连接，执行指定sql
	 */
	public static boolean update(Connection connection,String sql) {
		if(null == connection) {
			return false;
		}
		Statement statement = null;
		int row = 0;
		try {
			statement = connection.createStatement();
			row = statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResource(null,statement,connection);
		}
		if(row > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 *  从指定数据库连接进行查询
	 */
	public static ResultSet select(Connection connection,String sql) {
		if(null == connection) {
			return null;
		}
		Statement statement = null;
		ResultSet resultSet = null;
		CachedRowSetImpl cachedRowSetImpl = null;
		try {
			// 创建通道
			statement = connection.createStatement();
			// 获取结果集合
			resultSet = statement.executeQuery(sql);
			// 建立一个离线结果集
			cachedRowSetImpl = new CachedRowSetImpl();
			// 使用populate来拷贝数据到离线结果集(本质不是拷贝)
			cachedRowSetImpl.populate(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResource(resultSet, statement, connection);
		}
		return cachedRowSetImpl;
	}
	
	/*
	 * 使用默认数据库配置进行普通查询
	 */
	public static ResultSet select(String sql) {
		Connection connection = getConnection();
		return select(connection,sql);
	}
	
	/*
	 *  使用预通道，对指定数据库链接，进行查询
	 */
	public static ResultSet preSelect(Connection connection,String sql,Object[] params) {
		if(null == connection) {
			return null;
		}
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		CachedRowSetImpl cachedRowSetImpl = null;
		try {
			// 创建预通道
			preparedStatement = connection.prepareStatement(sql);
			// 绑定参数
			addParams(preparedStatement, params);
			// 执行sql
			resultSet = preparedStatement.executeQuery();
			//建立离线结果集
			cachedRowSetImpl = new CachedRowSetImpl();
			// 将resultset结果拷贝到离线结果集
			cachedRowSetImpl.populate(resultSet);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeResource(resultSet, preparedStatement, connection);
		}
		return cachedRowSetImpl;
	}
	
	/*
	 * 使用默认数据库配置进行预通道查询
	 */
	public static ResultSet preSelect(String sql,Object[] params) {
		Connection connection = getConnection();
		return preSelect(connection,sql,params);
	}
	 
	// 指定链接的预通道更新
	public static boolean preUpdate(Connection connection,String sql,Object[] params) {
		if(null == connection) {
			return false;
		}
		PreparedStatement preparedStatement = null;
		int row = 0;
		try {
			// 创建通道
			preparedStatement = connection.prepareStatement(sql);
			// 绑定参数
			addParams(preparedStatement, params);
			// 执行sql
			row = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResource(null, preparedStatement, connection);
		}

		if(row > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	//创建一个默认链接的预通道的更新
	public static boolean preUpdate(String sql,Object[] params) {
		Connection connection = getConnection();
		return preUpdate(connection, sql, params);
	}
	
	/*
	 *  利用循环对预通道绑定参数
	 */
	private static void addParams(PreparedStatement preparedStatement,Object[] params) {
		try {
			if(null != params) {
				for(int index = 0; index < params.length; index++) {
					
					// 根据实际的数据类型给预通道赋值
					if(params[index] instanceof Integer) {
						preparedStatement.setInt(index+1, (int) params[index]);
					} else {
						preparedStatement.setString(index+1, (String) params[index]);
					}
					
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void closeResource(ResultSet resultSet,
			Statement statement,
			Connection connection) {
		if(null != resultSet) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(null != statement) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(null != connection) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
