package com.sist.dao;
import java.util.*;
import java.sql.*;
public class FoodDAO {
	// 연결 객체
		private Connection conn;
		
		// 송수신
		private PreparedStatement ps;
		
		// 오라클 URL주소 설정
		private final String URL="jdbc:oracle:thin:@211.238.142.122:1521:XE";
		
		// 싱글턴
		private static FoodDAO dao;
		
		// 1. 드라이버 등록 => 한 번 수행 => 시작관 동시에 한번 수행, 멤버변수 초기화 : 생성
		public FoodDAO() {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				
				// ClassNotFoundException => 체크예외처리 => 반드시 예외처리 한다
				// java.io, java.net, java.sql => 체크예외처리
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		// 2. 오라클 면접
		public void getConnection() {
			try {
				// conn hr/happy => 명령어를 오라클로 전송
				conn = DriverManager.getConnection(URL, "hr", "happy");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// 3. 오라클 해제
		public void disConnection() {
			try {
				if (ps!=null) { // 통신이 열려 있으면 닫는다
					ps.close();
				}
				if (conn!=null) { // exit => 오라클 닫기
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// 4. 싱글턴 설정 => static은 메모리 공간이 1개만 가지고 있다
		// 메모리누수 현상을 방지...
		// DAO => new를 이용해서 생성 => 사용하지 않는 DAO가 오라클을 연결하고 있다
		// 싱글턴은 데이터베이스에서는 필수 조건
		public static FoodDAO newInstance() {
			if (dao==null) {
				dao = new FoodDAO();
			}
			return dao;
		}
		
		public List<FoodVO> foodAllData() {
			List<FoodVO> list = new ArrayList<FoodVO>();
			try {
				getConnection();
				String sql = "SELECT name, address, poster, phone, type "
							+"FROM food_house "
							+"ORDER BY fno ASC";
				ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					FoodVO vo = new FoodVO();
					vo.setName(rs.getString(1));
					String addr = rs.getString(2);
					addr = addr.substring(0, addr.lastIndexOf("지번"));
					vo.setAddress(addr.trim());
					String poster = rs.getString(3);
					poster = poster.substring(0, poster.indexOf("^"));
					poster = poster.replace("#", "&");
					vo.setPoster(poster);
					vo.setTel(rs.getString(4));
					vo.setType(rs.getString(5));
					list.add(vo);
				}
				rs.close();
 			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				disConnection();
			}
			return list;
		}
}