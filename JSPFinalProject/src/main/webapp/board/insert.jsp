<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style type="text/css">
.row {
	margin: 0px auto;
	width: 600px;
}
</style>
</head>
<body>
	<div class="wrapper row3">
		<main class="container clear">
			<h2 class="sectiontitle">글쓰기</h2>
			<form method="post" action="../board/insert_ok.do">
			<table class="table">
			<tr>
				<th width="15%">이름</th>
				<td width="85%">
					<input type="text" name="name" size="20" class="input-sm">
				</td>
			</tr>
			<tr>
				<th width="15%">제목</th>
				<td width="85%">
					<input type="text" name="subject" size="20" class="input-sm">
				</td>
			</tr>
			<tr>
				<th width="15%">내용</th>
				<td width="85%">
					<textarea rows="10" cols="50" size="20" name="content"></textarea>
				</td>
			</tr>
			<tr>
				<th width="15%">비밀번호</th>
				<td width="85%">
					<input type="password" name="pwd" size="15" class="input-sm">
				</td>
			</tr>
			<tr>
				<td colspan="2" class="text-center">
					<input type="submit" value="글쓰기" class="btn btn-sm btn-success" id="insertBtn">
					<input type="button" value="취소" class="btn btn-sm btn-danger" onclick="javascript:history.back()">
				</td>
			</tr>
			</table>
			</form>
		</main>
	</div>
</body>
</html>