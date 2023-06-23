<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*, java.util.*" 
    %>
<%--
		JSP
		---
		1) 지시자 : page / include / taglib
						   ----------------
						   page지시자 : JSP파일에 대한 정보
						   1. contentType : 브라우저에서 실행하는 타입
						   					--------------------------
						   					html => text/html
						   					xml => text/xml
						   					json => text/plain(*****)
						   					--------------------------
						   					Ajax / Vue / React
						   					------------------ RestFul
						   2. import : 여러번 사용이 가능, 라이브러리 읽기
						   3. errorPage : 에러시에 에러 출력 화면으로 이동
						   4. buffer : html태그를 저장하는 공간
						   				소스미리보기
						   				=> 사용자마다 한개만 생성 (브라우저가 연결해서 읽어가는 위치)
		2) 스크립트 : 자바 / HTML을 분리해서 소스코딩
		   -------- 자바언어 코딩 위치 (벗어나면 일반 텍스트로 인식)
		   <%! %> : 선언문 (멤버변수, 메소드 선언)
		   			=> 클래스 제작시 클래스 블록 => 사용빈도가 없다
		   			=> 자바클래스를 만들어서 메소드를 호출
		   <%  %> : 일반 메소드 영역 => 지역변수, 제어문, 메소드 호출...
		   			_jspService() {
		   			
		   				-------------
		   				JSP에 첨부하는 소스
		   				-------------
		   			}
		   <%= %> : 화면 출력 (변수, 문자열...) => out.println(<%= %>)
		   ------ out
		3) 내장 객체 : 미리 객체를 생성해서 필요한 위치에서 사용이 가능
			request : HttpServletRequest
					  사용자 정보 (요청정보, 추가정보, 브라우저 정보)
					  = 요청정보
					  	getParameter() : 사용자가 전송한 데이터를 한개만 받는다
					  		=> 문자열 : String
					  	getParameterValues() : 여러개를 동시에 받을 경우
					  		=> String[] : 체크박스 select의 멀티...
					  	setCharacterEncoding() : 디코딩
					  		=> POST에서만 사용
					  		=> GET은 자동화 처리 => windows10이상
					  		   ================= server.xml (한글처리, 포트)
					  		   8080은 프록시서버
					  = 추가정보 (MVC, MV) => 오라클에서 받은 값을 추가 => JSP
					  	setAttribute() : 기존에 있는 request값에 출력에 필요한 데이터를 추가해서 전송
					  	getAttribute() : 추가해서 보낸 데이터를 받을 경우에 사용
					  	-------------- session에도 있다
					  = 브라우저 정보
					  	getRemoteAddr() : 접속자의 IP
					  	getRequestURL(), getRequestURI(), getContextPath()
			response : 응답정보, 화면이동정보 => HttpServletResponse
					   --------
					   	= setContextType() => HTML, XML
							=> page지시자
						= addCookie() => 쿠키전송
						  ----------- 한번만 수행이 가능
						  화면이동정보
						  -----------
						  	필요시에 서버에서 화면을 요청화면이 아닌 다른 화면으로 이동
						  			 ---------------------------------------------------
						  			 login => main
						  			 insert => list
						  			 delete => list
						  			 update => detail
						  	=> sendRedirect() => GET방식, request를 초기화한 다음 이동
						Header 정보 : 실제 데이터 전송전에 처리
							setHeader() => 한글변환 / 다운로드시에 파일명, 크기 => 다운로드창을 보여준다 
			out : JSPWriter (출력버퍼관리)
					출력 (메모리)
					 println() <%= %>
					 
					메모리 확인
					 getBufferSize() : 총버퍼 사이즈
					 getRemaining() : 사용중인 버퍼 크기
					버퍼 삭제
					 clear()
			session
			application
			pageContext
			---------------- 필수
			config : web.xml처리 (환경설정)
			exception : try ~ catch
			page : this
		
 --%>
<%-- out 출력 (복잡한 HTML구조 --%>
<%-- 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
</body>
</html>
--%>
<%
	EmpDAO dao = new EmpDAO();
		List<EmpVO> list = dao.empListData();
		out.write("<!DOCTYPE html>");
		out.write("<html>");
		out.write("<head>");
		out.write("<meta charset=\"UTF-8\">");
		out.write("<title>Insert title here</title>");
		out.write("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.write("<style>");
		out.write(".container {margin-top:50px}");
		out.write(".row {margin:0px auto; width: 800px}");
		out.write("h1 {text-align: center;}");
		out.write("</style>");
		out.write("</head>");
		out.write("<body>");
		out.write("<div class=container>");
		out.write("<h1> 사원 목록 </h1>");
		out.write("<div class=row>");
		out.write("<table class=\"table table-striped\">");
		out.write("<tr class=danger>");
		out.write("<th class=text-center>사번</th>");
		out.write("<th class=text-center>이름</th>");
		out.write("<th class=text-center>직위</th>");
		out.write("<th class=text-center>입사일</th>");
		out.write("<th class=text-center>급여</th>");
		out.write("<th class=text-center>성과급</th>");
		out.write("<th class=text-center>부서명</th>");
		out.write("<th class=text-center>근무지</th>");
		out.write("<th class=text-center>호봉</th>");
		out.write("</tr>");
		for (EmpVO vo:list) {
			out.write("<tr>");
			out.write("<td class=text-center><a href=\"MainServlet?mode=2&empno=" + vo.getEmpno() + "\">" + vo.getEmpno() + "</a></td>");
			out.write("<td class=text-center><a href=\"MainServlet?mode=2&empno=" + vo.getEmpno() + "\">" + vo.getEname() + "</a></td>");
			out.write("<td class=text-center>" + vo.getJob() + "</td>");
			out.write("<td class=text-center>" + vo.getDbday() + "</td>");
			out.write("<td class=text-center>" + vo.getDbsal() + "</td>");
			out.write("<td class=text-center>" + vo.getComm() + "</td>");
			out.write("<td class=text-center>" + vo.getDvo().getDname() + "</td>");
			out.write("<td class=text-center>" + vo.getDvo().getLoc() + "</td>");
			out.write("<td class=text-center>" + vo.getSvo().getGrade() + "</td>");
			out.write("</tr>");
		}
		out.write("</table>");
		out.write("</div>");
		out.write("</div>");
		out.write("</body>");
		out.write("</html>");
 %>