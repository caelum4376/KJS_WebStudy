package com.sist.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
public class listModel implements Model {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		List<String> list = new ArrayList<String>();
		list.add("홍길동");
		list.add("심청이");
		list.add("박문수");
		list.add("강감찬");
		list.add("이순신");
		request.setAttribute("list", list);
		return "view/list.jsp";
		
	}

}
