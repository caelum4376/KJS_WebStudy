package com.sist.view;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.sql.*;
import com.sist.dao.*;
@WebServlet("/SeoulServlet")
public class SeoulServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		// type=1 => 명소, type=2 => 자연, type=3 => 쇼핑
		String type = request.getParameter("type");
		
		// DAO연결 후에 데이터를 읽어온다
		SeoulDAO dao = SeoulDAO.newInstance();
		List<SeoulVO> list = dao.seoulListData(Integer.parseInt(type));
		String[] title= {"", "서울 명소", "서울 자연 & 관광", "서울 쇼핑"};
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<style>");
		out.println(".container{margin-top:50px}");
		out.println(".row{");
		out.println("margin:0px auto;");
		out.println("width:1024px;}");
		out.println("</style>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=container>");
		
		out.println("<h1>"+title[Integer.parseInt(type)]+ "</h1>");
		out.println("<div class=row>");
		for (SeoulVO vo:list) {
			out.println("<div class=\"col-md-3\">"); // 한줄에 4개씩
			out.println("<div class=\"thumbnail\">");
			out.println("<a href=\"#\">");
			out.println("<img src=\""+ vo.getPoster() +"\" style=\"width:230px; height:200px; \">");
			out.println("<div class=\"caption\">");
			out.println("<p style=\"font-size:9px\">" + vo.getTitle() + "</p>");
			out.println("</div>");
			out.println("</a>");
			out.println("</div>");
			out.println("</div>");
		}
		out.println("</div>"); // row
		out.println("</div>"); // container
		out.println("</body>");
		out.println("</html>");
	}

}
