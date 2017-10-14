/**
 * 
 */
package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库连接器
 * 
 * @author WangZhiheng
 *
 */
public class DBConnecter {
	/**
	 * 数据库连接
	 */
	private Connection connection;

	/**
	 * 
	 */
	public DBConnecter() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取数据库连接
	 * 
	 * @param dbname
	 *            数据库名
	 * @return 数据库连接
	 */
	public Connection getConnection(String dbname) {
		try {
			connection = DriverManager.getConnection("jdbc:sqlite://d:/" + dbname + ".db");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

}
