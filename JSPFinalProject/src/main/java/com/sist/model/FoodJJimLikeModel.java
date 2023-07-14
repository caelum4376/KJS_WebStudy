package com.sist.model;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.controller.RequestMapping;
import com.sist.dao.*;
import com.sist.vo.*;
public class FoodJJimLikeModel {
	@RequestMapping("jjim/jjim_insert.do")
	public String jjim_insert(HttpServletRequest request, HttpServletResponse response) {
		String fno = request.getParameter("fno");
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		FoodJJimVO vo = new FoodJJimVO();
		vo.setId(id);
		vo.setFno(Integer.parseInt(fno));
		FoodJJimLikeDAO dao = FoodJJimLikeDAO.newInstance();
		dao.foodJJimInsert(vo);
		
		// 화면 이동 (서버)
		// sendRedirect() => 재호출 => .do (request를 초기화)
		// forward() => 새로운 데이터 전송 (request에 값을 담아서 전송)
		// return "redirect"
		return "redirect:../food/food_detail.do?fno="+fno;
	}
	
	@RequestMapping("jjim/jjim_cancel.do")
	public String jjim_cancel(HttpServletRequest request, HttpServletResponse response) {
		
		String no = request.getParameter("no");
		FoodJJimLikeDAO dao = FoodJJimLikeDAO.newInstance();
		dao.foodJJimCancel(Integer.parseInt(no));
		
		
		return "redirect:../mypage/mypage_jjim_list.do";
	}
	
	@RequestMapping("like/like_insert.do")
	public String like_insert(HttpServletRequest request, HttpServletResponse response) {
		String fno = request.getParameter("fno");
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		FoodLikeVO vo = new FoodLikeVO();
		vo.setId(id);
		vo.setFno(Integer.parseInt(fno));
		FoodJJimLikeDAO dao = FoodJJimLikeDAO.newInstance();
		dao.foodLikeInsert(vo);
		
		return "redirect:../food/food_detail.do?fno="+fno;
	}
}
