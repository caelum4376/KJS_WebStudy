<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// sendRedirect는 값을 넘겨줌
	// response.sendRedirect("response03.jsp");
	RequestDispatcher rd = request.getRequestDispatcher("response03.jsp");
	
	// forward는 보내는게 아니고 불러들여서 덮어씌움
	rd.forward(request, response);
	System.out.println(request);
%>