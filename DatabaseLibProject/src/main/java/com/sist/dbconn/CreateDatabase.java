package com.sist.dbconn;
import java.util.*;
import java.sql.*;
public class CreateDatabase {
	private Connection conn;
	private final String URL = "jdbc:oracle:thin:@211.238.142.122:1521:XE";
	public CreateDatabase() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 연결
	public Connection getConnection() {
		try {
			conn = DriverManager.getConnection(URL, "hr", "happy");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	// 해제
	public void disConnection(Connection conn, PreparedStatement ps) {
		try {
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.clearWarnings();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
