<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
		pageContext = PageContext
		=> 내장객체 얻기 (사용빈도가 거의 없다)
		=> 웹 흐름
			include() => <jsp:include> : JSP여러개를 조립
			forword() => <jsp:forward> : 화면 이동
 --%>
<%
	String name = request.getParameter("name");
	String name2 = pageContext.getRequest().getParameter("name2");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>