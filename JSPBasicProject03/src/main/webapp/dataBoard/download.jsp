<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.io.*, java.net.*"%>
<%
	/*
	%E%ddd => 디코딩
	POST : setCharacterEncoding("UTF-8")
	GET : window10은 자동처리
	*/
	String fn = request.getParameter("fn");
	try {
		File file = new File("/Users/caelum/Downloads/"+fn);
		response.setHeader("Content-Disposition", "attachement; filename="+ URLEncoder.encode(fn, "UTF-8").replaceAll("\\+", "%20"));
		response.setContentLength((int)file.length());
		
		BufferedInputStream bis =
					new BufferedInputStream(new FileInputStream(file));
		BufferedOutputStream bos =
					new BufferedOutputStream(response.getOutputStream());
		
		int i=0;
		byte[] buffer = new byte[1024];
		while((i=bis.read(buffer, 0, 1024)) != -1) {
			bos.write(buffer, 0, i);
		}
		
		bis.close();
		bos.close();
		out.clear();
		out = pageContext.pushBody();
	} catch (Exception e) {
		e.printStackTrace();
	}
%>