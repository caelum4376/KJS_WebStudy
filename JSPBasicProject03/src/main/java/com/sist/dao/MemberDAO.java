package com.sist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.sist.vo.*;
public class MemberDAO {
		// 연결 객체 => Socket
		private Connection conn;
		
		// 송수신 (SQL ==> 결과값(데이터값))
		private PreparedStatement ps;
		
		// URL
		private final String URL = "jdbc:oracle:thin:@211.238.142.122:1521:XE";
		
		// 싱글턴
		// static => 저장공간이 한개
		private static MemberDAO dao;
		
		// 드라이버 등록
		public MemberDAO() {
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
		public static MemberDAO newInstance() {
			if (dao == null) {
				dao = new MemberDAO();
			}
			return dao;
		}
		
		// 기능
		// 로그인 처리
		public MemberVO isLogin(String id, String pwd) {
			MemberVO vo = new MemberVO();
			try {
				getConnection();
				
				// ID 존재여부 확인
				String sql = "SELECT COUNT(*) FROM jspMember "
							+"WHERE id=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, id);
				ResultSet rs = ps.executeQuery();
				rs.next();
				int count = rs.getInt(1);
				
				rs.close();
				
				// ID가 없는 상태
				if (count == 0) {
					vo.setMsg("NOID");
					
				// ID가 존재하는 상태
				} else {
					sql = "SELECT id, name, sex, pwd "
						 +"FROM jspMember "
						 +"WHERE id=?";
					ps = conn.prepareStatement(sql);
					ps.setString(1, id);
					rs = ps.executeQuery();
					rs.next();
					String db_id = rs.getString(1);
					String name = rs.getString(2);
					String sex = rs.getString(3);
					String db_pwd = rs.getString(4);
					
					rs.close();
					
					// 로그인
					if (db_pwd.equals(pwd)) {
						vo.setId(db_id);
						vo.setName(name);
						vo.setSex(sex);
						vo.setMsg("OK");
						
						
					// 비밀번호가 틀렸을때
					} else {
						vo.setMsg("NOPWD");
					}
				}
				
			} catch (Exception e) {
				
			} finally {
				disConnection();
			}
			
			return vo;
		}
}
