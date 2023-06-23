<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*, com.sist.vo.*, java.io.*" %>
<jsp:useBean id="dao" class="com.sist.dao.DataBoardDAO"></jsp:useBean>
<%
	String no = request.getParameter("no");
	String pwd = request.getParameter("pwd");
	DataBoardVO vo = dao.dataBoardFileInfo(Integer.parseInt(no));
	
	// DAO연결
	boolean bCheck = dao.databoardDelete(Integer.parseInt(no), pwd);
	
	if (bCheck == true) {
		if (vo.getFilesize() != 0) {
			File file = new File("/Users/caelum/Downloads/"+vo.getFilename());
			file.delete();
		}
		response.sendRedirect("list.jsp");
	} else {
%>
		<script>
			alert("비밀번호가 틀립니다!!");
			history.back();
		</script>
<%
	}
%>