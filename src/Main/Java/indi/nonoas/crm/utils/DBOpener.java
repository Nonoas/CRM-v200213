package indi.nonoas.crm.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 获取数据库连接的类
 * @author Nonoas
 *
 */
public class DBOpener {
	
	private static final String url="jdbc:sqlite:data/mycrm.db";
	
	/**
	 * 私有构造器
	 */
	private DBOpener() {}

    static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取数据库连接对象
	 * @return Connection对象
	 */
	public static Connection getConnection() {
		Connection connection=null;
		try {
			connection=DriverManager.getConnection(url);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
}
