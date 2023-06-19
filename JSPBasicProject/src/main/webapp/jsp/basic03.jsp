<%--
		<%! %> : 선언문 =>메소드 선언 => 클래스 변경
					-----------------------------------
						선언문
					-----------------------------------
					
					}
		<%= %>
		<% %>
		-------		public 
		
					-----------------------------------
						<%= %> : out.println(<%= %>)
						<% %>
					-----------------------------------
					}
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%!
	public int add(int a, int b) {
		return a+b;
	}
%>
<%
	int a=10;
	int b=20;
	int c = add(a,b);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<center>결과 : <%= c %></center>
</body>
</html>