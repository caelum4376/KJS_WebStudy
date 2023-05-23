package com.sist.dao;
import java.sql.*;
import java.util.*;
import com.sist.vo.*;
/*
 * 		1. 드라이버 등록
 * 		   -------- 오라클 연결하는 라이브러리 (ojdbc8.jar)
 * 		   OracleDriver => 메모리 할당
 * 		2. 오라클 연결
 * 		   Connection
 * 		3. SQL문장을 전송
 * 		   PreparedStatement
 * 		4. SQL문장 실행 요청
 * 		   = executeUpdate() => INSERT, UPDATE, DELETE
 * 			 --------------- COMMIT (AutoCommit)
 * 		   = executeQuery() => SELECT
 * 			 -------------- 결과값을 가지고 온다
 * 							------
 * 							ResultSet
 * 			ResultSet
 * 				String sql = "SELECT id, name, sex, age ";
 * 			---------------------------------------------
 * 				id			name		sex			age
 * 			---------------------------------------------
 * 				aaa			홍길동		남자		20 | firse() => next()
 * 														 위치변경   위치변경 후 데이터 읽기
 * 			getString(1) getString(2) getString(3) getInt(4)
 * 			getString("id") => myBatis
 * 			---------------------------------------------
 * 				bbb			심청이		여자		23
 * 			---------------------------------------------
 * 				ccc			박문수		남자		27 | last() => previous()
 * 			---------------------------------------------
 * 		5. 닫기
 * 			생성 역순으로 닫는다
 * 			rs.close(), ps.close(), conn.close()
 * 		----------------------------------------------------- 오라클 연결 (Servlet => JSP)
 */
public class FoodDAO {
	// 기능 => INSERT => 데이터 수집 (파일)
	private Connection conn; // 오라클 연결 (DB연결)
	private PreparedStatement ps; // SQL문장 전송 / 결과값 읽기
	
	// mySQL => jdbc:mysql://localhost/mydb
	private final String URL = "jdbc:oracle:thin:@211.238.142.122:1521:XE";
//	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	
	// DAO객체를 한개만 사용이 가능하게 만든다
	private static FoodDAO dao; // 싱글턴 패턴
	
	// 드라이버 설치 => 소프트웨어 (메모리 할당 요청) Class.forName()
	// 클래스의 정보를 전송
	// 드라이버 설치는 1번만 수행
	public FoodDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {}
	}
	
	// 오라클 연결
	public void getConnection() {
		try {
			// => 오라클 전송 : conn hr/happy
			conn = DriverManager.getConnection(URL, "hr", "happy");
		} catch (Exception e) {}
	}
	
	// 오라클 연결 종료
	public void disConnection() {
		try {
			if (ps != null) ps.close();
			if (conn != null) conn.close();
		} catch (Exception e) {}
	}
	
	// DAO객체를 1개만 생성해서 사용 => 메모리 누수현상을 방지 (싱글턴 패턴)
	// 싱글턴 / 팩토리 => 면접 (스프링 : 패턴8개)
	public static FoodDAO newInstance() {
		// newInstance(), getInstance() => 싱글턴
		if (dao == null) {
			dao = new FoodDAO();
		}
		return dao;
	}
	// -------------------------------------- 기본세팅 (모든 DAO)
	// 기능
	// 1. 데이터 수집 (INSERT)
	/*
	 * 		Statement => SQL => 생성과 동시에 데이터 추가
	 * 					"'"+name+"','"+sex+"','"+...
	 * 		PreparedStatement => 미리 SQL문장을 만들고 나중에 값을 채운다
	 * 			=> default
	 * 		CallableStatement => Procedure호출
	 */
	public void foodCategoryInsert(CategoryVO vo) {
		try {
			// 1. 연결
			getConnection();
			
			// 2. SQL문장 생성
			String sql = "INSERT INTO food_category VALUES("
						+"fc_cno_seq.nextval, ?, ?, ?, ?)";
			
			// 3. SQL문장을 오라클에 전송
			ps = conn.prepareStatement(sql);
			
			// 3-1. ?에 값을 채운다
			ps.setString(1, vo.getTitle()); // "'" + vo.getTitle() + "'"
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getPoster());
			ps.setString(4, vo.getLink());
			// 번호가 잘못되면 오류 발생, 데이터형이 다르면 오류
			
			// 4. SQL문장 실행명령 => SQL문장을 작성
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace(); // 에러 확인
		} finally {
			disConnection(); // 오라클 연결 해
		}
	}
	
	// 1-1. 실제 맛집 정보
	/*
	 * fno NUMBER,
	 * cno NUMBER,
	 * name varchar2(100) CONSTRAINT fh_name_nn NOT NULL,
	 * score NUMBER(2,1),
	 * address varchar2(300) CONSTRAINT fh_address_nn NOT NULL,
	 * phone varchar2(20) CONSTRAINT fh_phone_nn NOT NULL,
	 * TYPE varchar2(30) CONSTRAINT fh_type_nn NOT NULL,
	 * price varchar2(30),
	 * parking varchar2(30),
	 * menu clob,
	 * good NUMBER,
	 * soso NUMBER,
	 * bad NUMBER,
	 * poster varchar2(4000) CONSTRAINT fh_poster_nn NOT NULL,
	 * time varchar2(20)
	 */
	public void foodDataInsert(FoodVO vo) {
		try {
			// 1. 오라클 연결
			getConnection();
			
			// 2. SQL문장 제작
			String sql = "INSERT INTO food_house VALUES("
						+"fh_fno_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			// 3. 오라클 전송
			ps = conn.prepareStatement(sql);
			
			// 4. ?에 값을 넣는다
			ps.setInt(1, vo.getCno());
			ps.setString(2, vo.getName());
			ps.setDouble(3, vo.getScore());
			ps.setString(4, vo.getAddress());
			ps.setString(5, vo.getPhone());
			ps.setString(6, vo.getType());
			ps.setString(7, vo.getPrice());
			ps.setString(8, vo.getParking());
			ps.setString(9, vo.getTime());
			ps.setString(10, vo.getMenu());
			ps.setInt(11, vo.getGood());
			ps.setInt(12, vo.getSoso());
			ps.setInt(13, vo.getBad());
			ps.setString(14, vo.getPoster());
			
			// 5. 실행요청
			ps.executeUpdate(); // 자동 커밋
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
	}
	
	// 2. SELECT => 전체 데이터 읽기 => 30개 (한개당 => CategoryVO)
	// => Collection, 배열 => 브라우저로 30개를 전송
	// 브라우저 <==> 오라클 (X)
	// 브라우저 <==> 자바 <==> 오라클
	public List<CategoryVO> foodCategoryData() {
		List<CategoryVO> list = new ArrayList<CategoryVO>();
		try {
			// 1. 오라클 연결
			getConnection();
			
			// 2. SQL문장
			String sql = "SELECT cno, title, subject, poster, link "
						+"FROM food_category "
						+"ORDER BY cno";
			
			// 3. 오라클 전송
			ps = conn.prepareStatement(sql);
			
			// 4. 실행 후 결과값 받기
			ResultSet rs = ps. executeQuery();
			
			// rs에 있는 데이터를 list에 저장
			while (rs.next()) {
				CategoryVO vo = new CategoryVO();
				vo.setCno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setSubject(rs.getString(3));
				String poster = rs.getString(4);
				poster = poster.replace("#", "&");
				vo.setPoster(poster);
				vo.setLink("https://www.mangoplate.com"+rs.getString(5));
				
				list.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 오라클 닫기
			disConnection();
		}
		return list;
	}
	
	
	// 3. 상세보기 => WHERE
	
}
















