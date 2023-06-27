<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
		EL (Expression Language) : 표현식
		${출력물} => <%= %>
		  ------ 변수를 출력하는 것이 아니다
		  request에 있는 값, session, application, param
		=> 연산자
		   이항연산자
		   ----------
		   산술연산자 (+, -, *, /, %)
		   		+는 산술만 처리 (문자열 결합은 안된다 : +=)
		   		/는 정수/정수 = 실수, 0으로 나눌 수 없다
		   		=> Null값은 0으로 취급
		   			/(div) ${10 div 2} , ${10/2}
		   			%(mod) ${10 mod 2} , ${10%2}
		   비교연산자 (==, !=, <, >, <=, >=)
		   		문자열이나 날짜 비교시에 동일하게 사용한다 "홍길동" == ""
		   		== (eq)
		   		!= (ne)
		   		<  (lt)
		   		>  (gt)
		   		<= (le)
		   		>= (ge)
		   논리연산자
		   		&&, ||, !
		   		and or  not
		   		
		   삼항연산자 : 조건?값:값
		   기타연산자 : Empty => null, 공백인 경우 처리 => 사용빈도는 거의 없다
		   
 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>산술연산자</h1>
<%-- 	&#36;{10+10}=${10+10}<br>
	&#36;{"10"+10}=${"10"+10}<br> "숫자" => Integer.parseInt
	&#36;{"10"+=10}=${"10"+=10}<br> += 문자열 결합 
	&#36;{10/3}=${10/3}<br>
 
	&#36;{10 div 3}=${10 div 3}<br> 
	&#36;{10 % 3}=${10 % 3}<br> 
	&#36;{10 mod 3}=${10 mod 3}<br> 
	null값은 0으로 인식
	&#36;{null + 10}=${null + 10}<br> 
	비교연산자, 논리연산자, 부정연산자 : 조건문에서 사용
	 
	&#36;{"홍길동"=="심청이"}=${"홍길동"=="심청이"}<br>
	&#36;{"홍길동"!="심청이"}=${"홍길동"!="심청이"}<br>
	&#36;{"홍길동" < "심청이"}=${"홍길동" < "심청이"}<br>
	&#36;{"홍길동" > "심청이"}=${"홍길동" > "심청이"}<br>
	&#36;{"홍길동" <= "심청이"}=${"홍길동" <= "심청이"}<br>
	&#36;{"홍길동" >= "심청이"}=${"홍길동" >= "심청이"}<br>
	
	
		논리연산자 (조건) 논리연산 (조건)
		&& : 범위를 포함
		|| : 범위를 벗어난 경우
	
	&#36;{10>6 && 5==6} = ${10>6 && 5==6}<br>
	&#36;{10>6 || 5==6} = ${10>6 || 5==6} --%>
</body>
</html>