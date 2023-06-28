package com.sist.dao;
import java.sql.*;
import java.util.*;
// DBCP
import javax.sql.*;
import javax.naming.*;

public class FoodDAO {
	// 연결 객체 얻기
	private Connection conn;
	
	// SQL송수신
	private PreparedStatement ps;
	
	// 싱글턴
	private static FoodDAO dao;
	
	// 출력 갯수
	private final int ROW_SIZE = 20;
	
	// Pool영역에서 Connection
	public void getConnection() {
		// Connection 객체 주소를 메모리에 저장
		// 저장공간 => POOL 영역 (디렉토리형식으로 저장) => JNDI
		// 루트 => 저장공간
		// java://env/comp => c드라이브 => jdbc/oracle
		try {
			// 1. 탐색기를 연다
			Context init = new InitialContext();
			
			// 2. C드라이버 연결
			// lookup => 문자열(key) => 객체 찾기 (RMI) => Socket
			Context cdriver = (Context)init.lookup("java://comp/env");
			
			// 3. Connection 객체 찾기
			DataSource ds = (DataSource)cdriver.lookup("jdbc/oracle");
			
			// 4. Connection주소를 연결
			conn = ds.getConnection();
			
			// 282page
			// => 오라클 연결 시간을 줄인다
			// => Connection 객체가 일정하게 생성 => 관리
			
		} catch(Exception e) {
			
		}
	}
	
	// Connection객체 사용 후에 반환
	public void disConnection() {
		try {
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		} catch(Exception e) {
			
		}
	}
	
	// 싱글턴 객체 만들기
	public static FoodDAO newInstance() {
		if (dao==null) {
			dao = new FoodDAO();
		}
		return dao;
		
	}
	// 동일 => 오라클 연결로 SQL문장 실행
	public List<FoodBean> foodListData(int page) {
		List<FoodBean> list = new ArrayList<FoodBean>();
		
		try {
			// Connection의 주소를 얻어 온다
			getConnection();
			
			// SQL문장 전송
			String sql = "SELECT fno, poster, name, num "
						+"FROM (SELECT fno, poster, name, rownum as num "
						+"FROM (SELECT /*+ INDEX_ASC(food_location fl_fno_pk) */fno, poster, name "
						+"FROM food_location)) "
						+"WHERE num BETWEEN ? AND ?";
			ps = conn.prepareStatement(sql);
			int rowSize = ROW_SIZE;
			int start = (rowSize * page) - (rowSize - 1); // rownum은 1번부터 시작
			int end = rowSize * page;
			
			// ?에 값을 채운다
			ps.setInt(1, start);
			ps.setInt(2, end);
			
			// 실행요청
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				FoodBean vo = new FoodBean();
				vo.setFno(rs.getInt(1));
				vo.setName(rs.getString(3));
				String poster = rs.getString(2);
				poster = poster.substring(0, poster.indexOf("^"));
				poster = poster.replace("#", "&");
				vo.setPoster(poster);
				
				// list에 20개를 채운다
				list.add(vo);
			}
			rs.close();
		} catch(Exception e) {
			// 에러처리
			e.printStackTrace();
		} finally {
			// 반환
			disConnection();
		}
		
		return list;
	}
	
	public int foodTotalPage() {
		int totalpage = 0;
		try {
			getConnection();
			
			// SQL문장 전송
			String sql = "SELECT CEIL(COUNT(*)/" + ROW_SIZE + ") "
						+"FROM food_location";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			totalpage = rs.getInt(1);
			rs.close();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
		return totalpage;
	}
	
	// 상세보기
	public FoodBean foodDetailData(int fno) {
		FoodBean vo = new FoodBean();
		try {
			// connection 주소 열기
			getConnection();
			
			// sql문장 전송
			String sql = "SELECT fno, poster, name, tel, score, time, parking, type, price, menu, address "
						+"FROM food_location "
						+"WHERE fno = ?";
			ps = conn.prepareStatement(sql);
			
			// 실행전에 ?에 값을 채운다
			ps.setInt(1, fno);
			
			// 실행 후 결과값 읽기
			ResultSet rs = ps.executeQuery();
			
			// 데이터가 있는 메모리에 커서를 위치
			rs.next();
			vo.setFno(rs.getInt(1));
			vo.setPoster(rs.getString(2));
			vo.setName(rs.getString(3));
			vo.setTel(rs.getString(4));
			vo.setScore(rs.getDouble(5));
			vo.setTime(rs.getString(6));
			vo.setParking(rs.getString(7));
			vo.setType(rs.getString(8));
			vo.setPrice(rs.getString(9));
			vo.setMenu(rs.getString(10));
			vo.setAddress(rs.getString(11));
			
			rs.close();
			
			// BLOB, BFILE : rs.getInputStream()
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnection(); // 반환
		}
		
		return vo;
	}
}
