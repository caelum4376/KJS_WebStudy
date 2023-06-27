<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
		JSTL (598page)
		=> 태그로 자바 문법을 만든 태그 라이브러리
		=> core : 자바 제어문, 변수선언, 화면 이동
		   ----
		   조건문
		   		<if test="조건문">
		   			조건문이 true일때 실행
		   		</if>
		   선택문
		   		<choose>
		   			<when test="">
		   			</when>
		   			<when test="">
		   			</when>
		   			<when test="">
		   			</when>
		   			<otherwise></otherwise> => else/default
		   		</choose>
		   반복문
		   		=> 일반 for
		   			<c:forEach var="i" begin="1" end="10" step="1">
		   				반복 수행문장
		   			</c:forEach>
	   			=> for-each
	   				<c:forEach var="vo" item="list">
		   				반복 수행문장
	   				</c:forEach>
		=> fmt : 날짜변환, 숫자변환
		=> fn : String클래스의 메소드이용
 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>자바</h1>
<%
	for (int i=1; i<10; i++) {
%>
		<%= i %>&nbsp;
<%
	}
%>
<h1>JSTL</h1>
<core:forEach var="i" begin="1" end="10" step="1">
	${ i }&nbsp;
</core:forEach>
</body>
</html>