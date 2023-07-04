package com.sist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import com.sist.common.CreateDataBase;

public class FoodDAO {
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db = new CreateDataBase();
	private static FoodDAO dao;
	
	// 싱글턴
	public static FoodDAO newInstance() {
		if (dao==null)
			dao = new FoodDAO();
		return dao;
	}
	
	public List<FoodVO> foodListData() {
		List<FoodVO> list = new ArrayList<FoodVO>();
		try {
			db.getConnection();
			String sql = "SELECT fno, poster, name, rownum "
						+"FROM food_location "
						+"WHERE rownum <= 20";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				FoodVO vo = new FoodVO();
				vo.setFno(rs.getInt(1));
				String poster = rs.getString(2);
				poster = poster.substring(0, poster.indexOf("^"));
				poster = poster.replace("#", "&");
				vo.setPoster(poster);
				vo.setName(rs.getString(3));
				
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return list;
	}
	
	public FoodVO foodDetailData(int fno) {
		FoodVO vo = new FoodVO();
		
		try {
			conn = db.getConnection();
			String sql = "SELECT fno, cno, name, score, poster, address, type, parking, time, menu, phone, price "
						+"FROM food_house "
						+"WHERE fno=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, fno);
			ResultSet rs = ps.executeQuery();
			rs.next();
			vo.setFno(rs.getInt(1));
			vo.setCno(rs.getInt(2));
			vo.setName(rs.getString(3));
			vo.setScore(rs.getDouble(4));
			vo.setPoster(rs.getString(5));
			vo.setAddress(rs.getString(6));
			vo.setType(rs.getString(7));
			vo.setParking(rs.getString(8));
			vo.setTime(rs.getString(9));
			vo.setMenu(rs.getString(10));
			vo.setPhone(rs.getString(11));
			vo.setPrice(rs.getString(12));
			
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		
		return vo;
	}
	
}
