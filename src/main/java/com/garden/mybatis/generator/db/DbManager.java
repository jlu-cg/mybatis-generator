package com.garden.mybatis.generator.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.garden.mybatis.generator.config.ConfigParser;

/**
 * 数据库管理
 * 
 * @author chengguang
 * @date 2018年3月5日 上午10:25:29
 */
public class DbManager {

	private Connection connection;

	private DbManager() {
		try {
			Class.forName(ConfigParser.getInstance().getGeneratorConfig().getJdbcType().getDriverClass());
			connection = DriverManager.getConnection(ConfigParser.getInstance().getGeneratorConfig().getJdbcType().getUrl(),
					ConfigParser.getInstance().getGeneratorConfig().getJdbcType().getUserName(),
					ConfigParser.getInstance().getGeneratorConfig().getJdbcType().getPassword());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public void close() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static DbManager getInstance() {
		return InnerClass.instance;
	}

	private static class InnerClass {
		public static final DbManager instance = new DbManager();
	}

	public static void main(String[] args) {

	}

}
