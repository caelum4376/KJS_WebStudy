package com.sist.dao;
// 오라클만 연결 => SELECT, UPDATE, INSERT, DELETE
import java.util.*;
import java.sql.*;
public class BoardDAO {
	// 연결객체
	private Connection conn;
	
	// 송수신 객체 (오라클 (SQL문장 전송), 실행 결과값을 읽어 온다)
	private PreparedStatement ps;
	
	// 모든 사용자가 1개의 DAO만 사용할 수 있게 만든다 (싱글턴)
	private static BoardDAO dao;
	
	// 오라클 연결 주소 => 상수
	private final String URL = "jdbc:oracle:thin:@211.238.142.122:1521:XE";
	
	// 1. 드라이버 종료
	public BoardDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 2. 싱글턴 => new 생성 => heap에서 계속 누적 => 오라클 연결되고 있다
	// 메모리 누수나, Connection객체 생성갯수를 제한
	// 한개의 메모리만 사용이 가능하게 만든다
	// 서버 프로그램, 데이터베이스 프로그램에서 주로 사용
	// *** Spring은 모든 객체가 싱글턴이다
	public static BoardDAO newInstance() {
		if (dao == null) {
			dao = new BoardDAO();
		}
		return dao;
	}
	
	// 3. 오라클 연결
	public void getConnection() {
		try {
			// conn hr/happy => 오라클 연결
			conn = DriverManager.getConnection(URL, "hr", "happy");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 4. 오라클 해제
	public void disConnection() {
		try {
			if (ps!=null) ps.close();
			if (conn!=null) conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	////////////////////////////////=====> 필수 =====> 클래스화 (라이브러리)
	
	// 5. 기능
	// 5-1. 목록출력 => 페이지 나누기 (인라인뷰) SELECT
	// 1page => 10개
	// => BoardVO (게시물 1개)
	public List<BoardVO> boardListData(int page) {
		List<BoardVO> list = new ArrayList<BoardVO>();
		try {
			// 1. 연결
			getConnection();
			
			// 2. SQL문장 생성
			String sql = "SELECT no, subject, name, TO_CHAR(regdate, 'YYYY-MM-DD'), hit, num "
						+"FROM (SELECT no, subject, name, regdate, hit, rownum AS num "
						+"FROM (SELECT no, subject, name, regdate, hit "
						+"FROM freeboard ORDER BY no DESC)) "
						+"WHERE num BETWEEN ? AND ?";
			// rownum 중간에서 데이터를 추출할 수 없다
			
			// 3. SQL문장 전송
			ps = conn.prepareStatement(sql);
			
			// 4. 사용자가 요청한 데이터를 첨부
			// 4-1. ?에 값을 채운다
			/*
			 * 		1page	1
			 * 		2page	11
			 * 		3page	21
			 */
			int rowSize = 10;
			int start = (page*rowSize)-(rowSize-1);
			int end = page*rowSize;
			
			ps.setInt(1, start);
			ps.setInt(2, end);
			
			// 5. 실행요청 후 결과값을 받는다
			ResultSet rs = ps.executeQuery();
			
			// 6. 받은 결과값을 list에 첨부
			while (rs.next()) {
				BoardVO vo = new BoardVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setDbday(rs.getString(4));
				vo.setHit(rs.getInt(5));
				list.add(vo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 해제
			disConnection();
		}
		
		return list;
	}
	
	// 5-1-1. 총페이지 구하기
	public int boardTotalPage() {
		int total=0;
		
		try {
			// 연결
			getConnection(); // 반복 => 메소드
			
			// SQL문장 제작
			// 43/10.0 => 4.3 => 5
			String sql = "SELECT CEIL(COUNT(*)/10.0) FROM freeboard";
			
			// 내장함수 => 용도
			ps = conn.prepareStatement(sql);
			
			// 실행을 요청
			ResultSet rs = ps.executeQuery();
			rs.next(); // 값이 출력되어있는 위치로 커서 이동
			total = rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 해제
			disConnection();
		}
		return total;
	}
	
	// 5-2. 상세보기 => 조회수 증가(UPDATE), 상세볼 게시물 읽기(SELECT)
	public BoardVO boardDetailData(int no) {
		BoardVO vo = new BoardVO();
		try {
			getConnection();
			String sql = "UPDATE freeboard SET "
						+"hit = hit+1 "
						+"WHERE no = " + no;
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			
			sql = "SELECT no, name, subject, content, TO_CHAR(regdate, 'yyyy-MM-dd'), hit "
				 +"FROM freeboard "
				 +"WHERE no = " + no;
			
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			vo.setDbday(rs.getString(5));
			vo.setHit(rs.getInt(6));
			
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
		
		return vo;
	}
	
	// 5-3. 게시물 등록 => INSERT
	// 용도 (SQL문장 사용법, HTML 태그 => 웹사이트)
	public void boardInsert(BoardVO vo) {
		try {
			getConnection();
			String sql = "INSERT INTO freeboard(no, name, subject, content, pwd) "
						+"VALUES(fb_no_seq.nextval, ?, ?, ?, ?)";
			
			ps = conn.prepareStatement(sql);
			
			// 실행 요청전에 ?에 값을 채운다
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getPwd());
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
	}
	
	// 5-4. 수정 (UPDATE) => 먼저 입력된 게시물 읽기, 실제 수정(비밀번호 검색)
	// 5-4-1. 먼저 입력된 게시물 읽기
	public BoardVO boardUpdateData(int no) {
		BoardVO vo=new BoardVO();
		try {
			getConnection();
			String sql = "SELECT name, subject, content "
				 +"FROM freeboard "
				 +"WHERE no = " + no;
			
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			vo.setName(rs.getString(1));
			vo.setSubject(rs.getString(2));
			vo.setContent(rs.getString(3));
			
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
		
		return vo;
	}
	
	// 5-4-2. 실제 수정(비밀번호 검색)
	public boolean boardUpdate(BoardVO vo) {
		boolean bCheck=false; // 비밀번호 => 본인 여부 확인
		try {
			 getConnection();
			 String sql = "SELECT pwd FROM freeboard "
					 	 +"WHERE no = " + vo.getNo();
			 ps = conn.prepareStatement(sql);

			 ResultSet rs = ps.executeQuery();
			 rs.next();
			 String db_pwd = rs.getString(1);
			 rs.close();
			 
			 if (db_pwd.equals(vo.getPwd())) {
				 // 삭제
//				 sql = "UPDATE freeboard SET "
//					  +"name = '?', subject = '?', content = '?' "
//					  +"WHERE no = ?";
//				 ps = conn.prepareStatement(sql);
//				 
//				 ps.setString(1, vo.getName());
//				 ps.setString(2, vo.getSubject());
//				 ps.setString(3, vo.getContent());
//				 ps.setInt(4, vo.getNo());
				 
				 sql = "UPDATE freeboard SET "
					  +"name = '" + vo.getName() + "', "
					  +"subject = '" + vo.getSubject() + "', "
					  +"content = '" + vo.getContent() + "' "
					  +"WHERE no = " + vo.getNo();
				 ps = conn.prepareStatement(sql);
				 bCheck = true;
				 ps.executeUpdate();
			 }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
		
		return bCheck;
	}
	
	// 5-5. 삭제 (DELETE) => 비밀번호 검색
	public boolean boardDelete(int no, String pwd) {
		boolean bCheck=false; // 비밀번호 => 본인 여부 확인
		
		try {
			 getConnection();
			 String sql = "SELECT pwd FROM freeboard "
					 	 +"WHERE no = " + no;
			 ps = conn.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery();
			 rs.next();
			 String db_pwd = rs.getString(1);
			 rs.close();
			 
			 if (db_pwd.equals(pwd)) {
				 // 삭제
				 bCheck = true;
				 sql = "DELETE FROM freeboard "
					  +"WHERE no = " + no;
				 ps = conn.prepareStatement(sql);
				 ps.executeUpdate();
			 }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
		
		return bCheck;
	}
	
	// 5-6. 찾기 (이름, 제목, 내용) => LIKE
}
