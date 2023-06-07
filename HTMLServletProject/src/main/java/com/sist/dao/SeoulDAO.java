package com.sist.dao;
import java.util.*;
import java.sql.*;
import com.sist.dbconn.*;

public class SeoulDAO {
	private String[] tables = {
			"seoul_location",
			"seoul_nature",
			"seoul_shop",
			"seoul_hotel"
	};
	private Connection conn;
	private PreparedStatement ps;
	private CreateDatabase db = new CreateDatabase();
	private static SeoulDAO dao;
	
	// 1. 기능
	public List<SeoulVO> seoulListData(int page, int type) {
		List<SeoulVO> list = new ArrayList<SeoulVO>();
		try {
			conn = db.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		
		return list;
	}
	
	// 2. 총페이지 구하기
	public int seoulTotalage(int type) {
		int total=0;
		
		try {
			conn = db.getConnection();
			String sql = "SELECT CEIL(COUNT(*)/12.0) "
						+"FROM " + tables[type];
			/*
			 * ps.String(1, tables[type])
			 * FROM 'seoul_location'
			 * 
			 * Intert into table_name values(?, ?)...
			 * 홍길동 남자
			 * insert 
			 */
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return total;
	}
	
	// 3. 상세보기
	public SeoulVO seoulDetailData(int no, int type) {
		SeoulVO vo = new SeoulVO();
		try {
			conn = db.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return vo;
	}
}
