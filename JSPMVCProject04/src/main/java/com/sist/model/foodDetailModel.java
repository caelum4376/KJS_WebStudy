package com.sist.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sist.dao.*;
import java.util.*;
public class foodDetailModel implements Model {

	@Override
	public String handlerRequest(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("foodDetailModel : " + request);
		FoodDAO dao = FoodDAO.newInstance();
		String fno = request.getParameter("fno");
		FoodVO vo = dao.foodDetailData(Integer.parseInt(fno));
		String address = vo.getAddress();
		String addr1 = address.substring(0, address.lastIndexOf("지"));
		String addr2 = address.substring(address.lastIndexOf("지")+3);
		String temp = addr1.trim().substring(addr1.indexOf(" "));
		String addr3 = temp.trim().substring(0, temp.trim().indexOf(" "));
		request.setAttribute("fno", fno);
		request.setAttribute("vo", vo);
		request.setAttribute("addr1", addr1);
		request.setAttribute("addr2", addr2);
		request.setAttribute("addr3", addr3);
		
		return "food/food_detail.jsp";
	}

}
