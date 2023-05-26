package com.sist.servlet;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sist.dao.*;

@WebServlet("/BoardUpdateServlet")
public class BoardUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// 화면 출력 => HTML => 내용 수정 및 비밀번호 입력
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 입력폼 전송 => HTML (HTML을 브라우저로 보낸다)
		response.setContentType("text/html;charset=UTF-8");

		// 메모리에 HTML을 저장 => 브라우저에서 읽어서 출력
		PrintWriter out = response.getWriter();
		
		// 클라이언트
		// => BoardDetailServlet?no=1
		// a.jsp?page=1
		String no = request.getParameter("no");
		
		// 오라클에서 값을 얻어온다
		BoardDAO dao = BoardDAO.newInstance();
		BoardVO vo = dao.boardUpdateData(Integer.parseInt(no));
		
		out.println("<html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=stylesheet href=table.css>");
		out.println("</head>");
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>글 수정하기</h1>");

		// 입력된 데이터를 한번에 => action에 등록된 클래스로 전송
		// 메소드 => method=post => doPost()
		out.println("<form method=post action=BoardUpdateServlet>");
		out.println("<table width=700 class=table_content>");
		out.println("<tr>");
		out.println("<th width=15%>이름</th>");
		out.println("<td width=85%><input type=text name=name size=15 required value=\"" + vo.getName() + "\"></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<th width=15%>제목</th>");
		out.println("<td width=85%><input type=text name=subject size=50 required value=\"" + vo.getSubject() + "\"></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<th width=15%>내용</th>");
		out.println("<td width=85%><textarea rows=10 cols=50 name=content required >" + vo.getContent() + "</textarea></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<th width=15%>비밀번호</th>");
		out.println("<td width=85%><input type=password name=pwd size=10 required></td>");
		// 사용자에게 보여주면 안되는 데이터 => 화면 출력없이 데이터를 전송 => hidden
		out.println("<input type=hidden name=no value="+no+">");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td colspan-2 align=center>");
		out.println("<input type=submit value=수정>");
		out.println("<input type=button value=취소 onclick=\"javascript:history.back()\">");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</form>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
	}

	// 요청에 대한 처리 담당
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try {
			// 디코딩 => byte[]을 한글로 변환
			// 자바 => 2byte => 브라우저는 1byte로 받는다 => 2byte
			request.setCharacterEncoding("UTF-8");	// 한글변환
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 홍길동 => %ED%99%8D%EA%B8%B8%EB%8F%99& ==> 인코딩
		// %ED%99%8D%EA%B8%B8%EB%8F%99& => 홍길동 ==> 디코딩
		String no = request.getParameter("no");
		String name = request.getParameter("name");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		String pwd = request.getParameter("pwd");
		
		// 디코딩 => 한글이 있는 경우에만 사용
		// 숫자, 알파벳은 => 1,2byte 동시에 처리
		PrintWriter out = response.getWriter();
		
		BoardVO vo = new BoardVO();
		vo.setNo(Integer.parseInt(no));
		vo.setName(name);
		vo.setSubject(subject);
		vo.setContent(content);
		vo.setPwd(pwd);
		
		// 오라클로 UPDATE 요청
		BoardDAO dao = BoardDAO.newInstance();
		boolean bCheck = dao.boardUpdate(vo);
		
		if (bCheck == true) {
			response.sendRedirect("BoardListServlet");
		} else {

			out.println("<script>");
			out.println("alert(\"비밀번호가 틀립니다!\");");
			out.println("history.back();");
			out.println("</script>");
		}
		
	}

}
