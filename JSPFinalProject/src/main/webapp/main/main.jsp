<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="../layout/styles/layout.css" rel="stylesheet" type="text/css" media="all">
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d3be6802e7c6f6cf640550fd67e44d41&libraries=services"></script>
</head>
<body id="top">
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="${ main_jsp }"></jsp:include>
<jsp:include page="footer.jsp"></jsp:include>
<script src="../layout/scripts/jquery.min.js"></script> 
<script src="../layout/scripts/jquery.backtotop.js"></script> 
<script src="../layout/scripts/jquery.mobilemenu.js"></script> 
<script src="../layout/scripts/jquery.flexslider-min.js"></script>
</body>
</html>