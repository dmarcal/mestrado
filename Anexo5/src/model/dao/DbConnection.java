package model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;


abstract class DbConnection {

	public static void DbException(String message) {
		throw new RuntimeException("Exception -> "+ message);
	}

	public static Connection getConnection() {
		Connection conn = null;
		if (conn == null) {
			try {
				Properties props = loadProperties();
				String url = props.getProperty("dburl");
				conn = DriverManager.getConnection(url, props);
			}
			catch (SQLException e) {
				DbException(e.getMessage());
			}
		}
		return conn;
	}
		
	public static ResultSet executeQuery(PreparedStatement st) {
		ResultSet rs = null;
		if (st != null) {
			try {
				rs = st.executeQuery();
			} catch (SQLException e) {
				DbException(e.getMessage());
			}
		}
		return rs;
	}
	
	public static int executeUpdate(PreparedStatement st) {
		int rowsAffected = 0;
		if (st != null) {
			try {
				rowsAffected = st.executeUpdate();
			} catch (SQLException e) {
				DbException(e.getMessage());
			}
		}
		return rowsAffected;
	}

	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		}
		catch (IOException e) {
			DbException(e.getMessage());
		}
		return null;
	}
 }
