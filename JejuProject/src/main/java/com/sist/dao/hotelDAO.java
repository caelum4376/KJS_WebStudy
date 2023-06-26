package com.sist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class hotelDAO {
	// 연결 객체 => Socket
	private Connection conn;
	
	// 송수신 (SQL ==> 결과값(데이터값))
	private PreparedStatement ps;
	
	// URL
	private final String URL = "jdbc:oracle:thin:@211.238.142.122:1521:XE";
	
	// 싱글턴
	// static => 저장공간이 한개
	private static hotelDAO dao;
	
	// 드라이버 등록
	public hotelDAO() {
		try { 
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 오라클 연결
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(URL, "hr", "happy");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 오라클 해제
	public void disConnection() {
		try {
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 싱글턴 처리
	public static hotelDAO newInstance() {
		if (dao == null) {
			dao = new hotelDAO();
		}
		return dao;
	}
	
	// 기능
	// 숙소 카테고리 데이터 추가
	public void hotelCategoryInsert(hotelCategoryVO vo) {
		try {
			getConnection();
			String sql = "INSERT INTO HOTET_CATEGORY VALUES ("
						+ "?, ?, 2)";
			ps = conn.prepareStatement(sql);
			
			// ?에 값을 채운다
			ps.setInt(1, vo.getHcno());
			ps.setString(2, vo.getHcTitle());
			
			// 실행
			ps.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
	}
	
	// 숙소 데이터 추가
	public void hotelInsert(hotelVO vo) {
		try {
			getConnection();
			String sql = "INSERT INTO HOTET_DETAIL VALUES ("
						+ "hc_hdno_seq.nextval, ?, ?, ?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			
			// ?에 값을 채운다
			ps.setInt(1, vo.getHuno());
			ps.setString(2, vo.getName());
			ps.setString(3, vo.getAddr());
			ps.setString(4, vo.getContent());
			ps.setString(5, vo.getEtc());
			ps.setString(6, vo.getPoster());
			ps.setInt(7, vo.getHcno());
			
			// 실행
			ps.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
	}
	
	// 방 데이터 추가
	public void roomInsert(roomVO vo) {
		try {
			getConnection();
			String sql = "INSERT INTO ROOM VALUES ("
						+ "?, ?, ?, ?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			
			// ?에 값을 채운다
			ps.setString(1, vo.getRname());
			ps.setInt(2, vo.getAccount());
			ps.setInt(3, vo.getPrice());
			ps.setString(4, vo.getPerson());
			ps.setString(5, vo.getSturcture());
			ps.setString(6, vo.getSpecial());
			ps.setString(7, vo.getRposter());
			ps.setInt(8, vo.getHdno());
			
			// 실행
			ps.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
	}	
}
