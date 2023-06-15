package com.sist.dao;
import java.util.*;

import oracle.jdbc.OracleType;
import oracle.jdbc.internal.OracleTypes;

import java.sql.*;

public class StudentDAO {
	// 연결
	private Connection conn;
	
	// 함수(프로시저) 호출
	private CallableStatement cs;
	
	// Singleton
	private static StudentDAO dao;
	
	// URL
	private final String URL = "jdbc:oracle:thin:@211.238.142.122:1521:XE";
	
	// Driver등록
	public StudentDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 싱글턴 사용
	public static StudentDAO newInstance() {
		if (dao == null) {
			dao = new StudentDAO();
		}
		return dao;
	}
	
	
	// 데이터 추가
	public void studentInsert(StudentVO vo) {
		try {
			conn = DriverManager.getConnection(URL, "hr", "happy");
			// 함수 호출만 하면 된다
			String sql = "{CALL studentInsert(?,?,?,?)}";
			cs = conn.prepareCall(sql); // ERD => 메뉴얼
			
			// ?에 값을 채운 다음 실행
			cs.setString(1, vo.getName());
			cs.setInt(2, vo.getKor());
			cs.setInt(3, vo.getEng());
			cs.setInt(4, vo.getMath());
			
			// 실행 요청
			cs.executeQuery(); // executeQuery()로 실행한다
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (cs!=null) cs.close();
				if (conn!=null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// 데이터 수정
	public void studentUpdate(StudentVO vo) {
		try {
			conn = DriverManager.getConnection(URL, "hr", "happy");
			String sql = "{CALL studentUpdate(?,?,?,?,?)}";
			cs = conn.prepareCall(sql);
			
			// ?에 값을 채운다
			cs.setInt(1, vo.getHakbun());
			cs.setString(2, vo.getName());
			cs.setInt(3, vo.getKor());
			cs.setInt(4, vo.getEng());
			cs.setInt(5, vo.getMath());
			
			// 실행 요청
			cs.executeQuery();

			// 모든 테이블의 데이터를 페이징
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (cs!=null) cs.close();
				if (conn!=null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// 데이터 삭제
	public void studentDelete(int hakbun) {
		try {
			conn = DriverManager.getConnection(URL, "hr", "happy");
			String sql = "{CALL studentDelete(?)}";
			cs = conn.prepareCall(sql);
			
			// ?에 값을 채운다
			cs.setInt(1, hakbun);
			
			// 실행요청
			cs.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (cs!=null) cs.close();
				if (conn!=null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// 데이터 상세
	public StudentVO studentDetail(int hakbun) {
		StudentVO vo = new StudentVO();
		try {
			conn = DriverManager.getConnection(URL, "hr", "happy");
			String sql = "{CALL studentDetailData(?,?,?,?,?)}";
			cs = conn.prepareCall(sql);
			
			// ?에 값을 채운다
			cs.setInt(1, hakbun);
			
			// OUT 변수일 경우에 => 메모리에 저장한다
			cs.registerOutParameter(2, OracleTypes.VARCHAR);
			cs.registerOutParameter(3, OracleTypes.INTEGER);
			cs.registerOutParameter(4, OracleTypes.INTEGER);
			cs.registerOutParameter(5, OracleTypes.INTEGER);
			
			// 실행 요청
			cs.executeQuery();
			
			vo.setName(cs.getString(2));
			vo.setKor(cs.getInt(3));
			vo.setEng(cs.getInt(4));
			vo.setMath(cs.getInt(5));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (cs!=null) cs.close();
				if (conn!=null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return vo;
	}
	
	// 데이터 전체
	public List<StudentVO> studentListData() {
		List<StudentVO> list = new ArrayList<StudentVO>();
		try {
			conn = DriverManager.getConnection(URL, "hr", "happy");
			String sql = "{CALL studentListData(?)}";
			cs = conn.prepareCall(sql);
			
			// ?에 값을 채운다
			// registerOutParameter => 저장해 주는 공간
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			
			/*
			 * NUMBER => INTEGER/DOUBLE
			 * VARCHAR2, CHAR => VARCHAR
			 * CURSOR => CURSOR 
			 */
			
			// 실행요청
			cs.executeQuery();
			
			// 결과값을 받는다
			// Cursor => Object로 받아서 ResultSet으로 형변환을 한다
			ResultSet rs = (ResultSet)cs.getObject(1);
			
			while(rs.next()) {
				StudentVO vo = new StudentVO();
				vo.setHakbun(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setKor(rs.getInt(3));
				vo.setEng(rs.getInt(4));
				vo.setMath(rs.getInt(5));
				vo.setTotal(rs.getInt(6));
				vo.setAvg(rs.getDouble(7));
				list.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (cs!=null) cs.close();
				if (conn!=null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	// 중복 코드가 있는 경우 메소드화
}
