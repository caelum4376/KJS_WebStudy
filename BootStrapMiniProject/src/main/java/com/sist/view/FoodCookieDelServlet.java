package com.sist.view;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FoodCookieDelServlet")
public class FoodCookieDelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mode = request.getParameter("mode");
		String cno = request.getParameter("cno");
		String fno = request.getParameter("fno");
		
		// 네트워크 => 요청(Client:브라우저) / 응답(Server:Servlet)
		//				=> 요청처리에 필요한 데이터를 전송
		// 주소
		
		// Cookie가지고 오기
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i=0; i<cookies.length; i++) {
				if (cookies[i].getName().startsWith("food_"+fno)) {
					cookies[i].setPath("/");
					cookies[i].setMaxAge(0); // 삭제
					response.addCookie(cookies[i]);
					break;
				}
			}
		}

	// 이동
	response.sendRedirect("MainServlet?mode="+mode+"&cno="+cno);
	}

}
