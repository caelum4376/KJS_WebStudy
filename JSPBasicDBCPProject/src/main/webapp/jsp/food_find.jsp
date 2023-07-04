<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*, com.sist.dao.*"%>
<%
// 0. 한글변환 (디코딩)
request.setCharacterEncoding("UTF-8");

// 1. 사용자가 전송한 데이터를 받는다 (page)
String strPage = request.getParameter("page");
String fd = request.getParameter("addr");
if (fd == null) {
	fd = "마포";
}

// 2. 실행과 동시에 페이지 전송이 불가능 => 첫페이지는 default 설정 (1)
if (strPage == null) {
	strPage = "1";
}

// 3. 현재페이지 지정
int curpage = Integer.parseInt(strPage);

// 4. 현재 페이지에 대한 데이터 읽기 (DAO => 오라클)
FoodDAO dao = FoodDAO.newInstance();
List<FoodBean> list = dao.foodFindData(curpage, fd);

// 5. 총페이지 읽기
int totalpage = dao.foodTotalPage(fd);

// 6. 블록별 처리
// [1] ~ [10], [11] ~ [20]
final int BLOCK = 10;

// 7. 시작위치
int startPage = ((curpage - 1) / BLOCK * BLOCK) + 1;

// 8. 끝위치
int endPage = ((curpage - 1) / BLOCK * BLOCK) + BLOCK;

if (endPage > totalpage) {
	endPage = totalpage;
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style type="text/css">
.container {
	margin-top: 50px;
}

.row {
	margin: 0px auto;
	width: 960px;
}
</style>
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
$(function(){
	$('#findBtn').click(function(){
		let addr = $('#addr').val();
		if (addr.trim() === "") {
			$('#addr').focus();
			return;
		}
		
		$('#frm').submit(); // 유효성 검사 (강제 입력)
	})
})
</script>
</head>
<body>
	<div class="container">
		<div class="row">
			<table class="table">
				<tr>
					<td>
						<form method="post" action="food_find.jsp" id="frm">
							<input type=text id="addr" name=addr size=20 class="input-sm"
								value="<%=fd%>"> 
							<input type=button id="findBtn" value="검색"
								class="btn btn-sm btn-danger">
						</form>
					</td>
				</tr>
			</table>
		</div>
		<div class="row">
			<%
			for (FoodBean vo : list) {
			%>
			<div class="col-md-3">
				<div class="thumbnail">
					<a href="#"> <img src="<%=vo.getPoster()%>" alt="Lights"
						style="width: 100%">
						<div class="caption">
							<p><%=vo.getName()%></p>
							<p><%=vo.getAddress()%></p>
						</div>
					</a>
				</div>
			</div>
			<%
			}
			%>
		</div>
		<div class="row">
			<div class="text-center">
				<ul class="pagination">
				<%
					if (startPage > 1) { // 1 11 21...
				%>
						<li><a href="food_find.jsp?page=<%=startPage-1%>&addr=<%=fd%>">&lt;</a></li>
				<%
					}
				%>
				<%
					for (int i=startPage; i<=endPage; i++) {
				%>
					<li <%=i==curpage?"class=active":"" %>><a href="food_find.jsp?page=<%=i%>&addr=<%=fd%>"><%= i %></a></li>
				<%
					}
				%>
				<%
					if (endPage < totalpage) {
				%>
						<li><a href="food_find.jsp?page=<%=endPage+1%>&addr=<%=fd%>">&gt;</a></li>
				<%
					}
				%>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>