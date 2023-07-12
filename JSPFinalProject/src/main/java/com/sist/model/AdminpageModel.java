package com.sist.model;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.controller.RequestMapping;
import com.sist.dao.*;
import com.sist.vo.*;

public class AdminpageModel {
	@RequestMapping("adminpage/adminpage_main.do")
	public String adminpage_main(HttpServletRequest request, HttpServletResponse response) {
		
		
		request.setAttribute("adminpage_jsp", "../adminpage/adminpage_reserve.jsp");
		request.setAttribute("main_jsp", "../adminpage/adminpage_main.jsp");
		CommonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	
	@RequestMapping("adminpage/adminpage_reserve.do")
	public String adminpage_reserve(HttpServletRequest request, HttpServletResponse response) {
		
		ReserveDAO dao = ReserveDAO.newInstance();
		List<ReserveVO> list = dao.reserveAdminData();
		request.setAttribute("list", list);
		request.setAttribute("adminpage_jsp", "../adminpage/adminpage_reserve.jsp");
		request.setAttribute("main_jsp", "../adminpage/adminpage_main.jsp");
		CommonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
}
