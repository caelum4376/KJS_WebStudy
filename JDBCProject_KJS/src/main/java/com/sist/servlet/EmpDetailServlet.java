package com.sist.servlet;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.dao.EmpDAO;
import com.sist.dao.EmpVO;

@WebServlet("/EmpDetailServlet")
// EmpDetailServlet => detail.jsp
public class EmpDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		// 사용자가 보내준 데이터 받기
		String empno = request.getParameter("empno");
		
		// ?empno=7788
		EmpDAO dao = new EmpDAO();
		EmpVO vo = dao.empDetailData(Integer.parseInt(empno));
		
		// vo에 있는 데이터를 출력
		PrintWriter out = response.getWriter();
		
		// HTML 출력 시작
		out.write("<html>");
		out.write("<head>");
		out.write("<meta charset=\"UTF-8\">");
		out.write("<title>Insert title here</title>");
		out.write("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.write("<style>");
		out.write(".container {margin-top:50px}");
		out.write(".row {margin:0px auto; width: 800px}");
		out.write("h1 {text-align: center;");
		out.write("");
		out.write("");
		out.write("");
		out.write("");
		out.write("</style>");
		out.write("</head>");
		out.write("<body>");
		out.write("<div class=container>");
		out.write("<h1>" + vo.getEname() + "사원의 개인정보 </h1>");
		out.write("<div class=row>");
		out.write("<table class=table>");
		out.write("<tr>");
		out.write("<th class=\"text-center info\" width=20%>사번</th>");
		out.write("<td class=\"text-center\" width=30%>" + vo.getEmpno() + "</th>");
		out.write("<th class=\"text-center info\" width=20%>이름</th>");
		out.write("<td class=\"text-center\" width=30%>" + vo.getEname() + "</th>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<th class=\"text-center info\" width=20%>직위</th>");
		out.write("<td class=\"text-center\" width=30%>" + vo.getJob() + "</th>");
		out.write("<th class=\"text-center info\" width=20%>입사일</th>");
		out.write("<td class=\"text-center\" width=30%>" + vo.getDbday() + "</th>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<th class=\"text-center info\" width=20%>급여</th>");
		out.write("<td class=\"text-center\" width=30%>" + vo.getDbsal() + "</th>");
		out.write("<th class=\"text-center info\" width=20%>성과급</th>");
		out.write("<td class=\"text-center\" width=30%>" + vo.getComm() + "</th>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<th class=\"text-center info\" width=20%>사수번호</th>");
		out.write("<td class=\"text-center\" width=30%>" + vo.getMgr() + "</th>");
		out.write("<th class=\"text-center info\" width=20%>부서명</th>");
		out.write("<td class=\"text-center\" width=30%>" + vo.getDvo().getDname() + "</th>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<th class=\"text-center info\" width=20%>근무지</th>");
		out.write("<td class=\"text-center\" width=30%>" + vo.getDvo().getLoc() + "</th>");
		out.write("<th class=\"text-center info\" width=20%>호봉</th>");
		out.write("<td class=\"text-center\" width=30%>" + vo.getSvo().getGrade() + "</th>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td colspan=4 class=text-right>");
		out.write("<a href=MainServlet?mode=4&empno=" + empno + " class=\"btn btn-xs btn-info\">수정</a>&nbsp;");
		out.write("<a href=# class=\"btn btn-xs btn-success\">삭제</a>&nbsp;");
		out.write("<a href=MainServlet class=\"btn btn-xs btn-primary\">목록</a>");
		out.write("</td>");
		out.write("</tr>");
		out.write("</table>");
		out.write("</div>");
		out.write("</div>");
		out.write("</body>");
		out.write("</html>");
	}

}