package com.sist.food;

import java.io.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;

/*
 * 	속성 => class
 */
@WebServlet("/FoodDetailServlet")
public class FoodDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 전송 => 브라우저 전송 (response)
		response.setContentType("text/html;charset=UTF-8");

		// 전페이지(FoodListServlet)에서 전송된 값을 받는다 => fno
		// request는 사용자가 전송한 데이터를 받을 때 사용
		// 요청 정보
		// 웹 => 객체 (request, response, session) => cookie
		String fno = request.getParameter("fno");

		FoodDAO dao = FoodDAO.newInstance();

		// vo에 저장된 데이터를 HTML에서 출력
		FoodVO vo = dao.foodDetailData(Integer.parseInt(fno));

		String addr = vo.getAddress();
		String addr1 = addr.substring(0, addr.lastIndexOf("지번"));
		String addr2 = addr.substring(addr.lastIndexOf("지번"));
		// 화면에 출력
		PrintWriter out = response.getWriter();

		// HTML을 출력 => 오라클에서 받은 결과값 출력
		out.println("<html>");
		out.println("<head>");
		out.println("<script type=\"text/javascript\" src=\"//dapi.kakao.com/v2/maps/sdk.js?appkey=9965c727d3306713c47391be682e4be9&libraries=services\"></script>");
		out.println(
				"<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<style>");
		out.println(".container{margin-top:50px}");
		out.println(".row{");
		out.println("margin:0px auto;");
		out.println("width:1024px;}");
		out.println("</style>");
		out.println("</head>");
		out.println("<body>");

		out.println("<div class=container>");
		out.println("<div class=row>");

		// 이미지 5개
		String poster = vo.getPoster();
		poster = poster.replace("#", "&");
		StringTokenizer st = new StringTokenizer(poster, "^");
		out.println("<table class=table>");
		out.println("<tr>");
		while (st.hasMoreTokens()) {
			out.println("<td>");
			out.println("<img src=" + st.nextToken() + " style=\"width:100%\">");
			out.println("</td>");
		}
		out.println("</tr>");
		out.println("</table>");

		out.println("</div>"); // row
		out.println("</div>"); // center

		out.println("<div class=container>");
		out.println("<div class=row>");

		// lg, sm, md, xs
		// 상세보기
		out.println("<div class=col-sm-8>");
		out.println("<table class=table>");
		out.println("<tr>");
		out.println("<td>");
		out.println("<h3>" + vo.getName() + "&nbsp;<span style=\"color:orange\">" + vo.getScore() + "</h3>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");

		out.println("<div style=\"height:20px\"></div>");

		out.println("<table class=table>");
		out.println("<tr>");
		out.println("<th width=25%>주소</th>");
		out.println("<td width=75%>" + addr1 + "<p>" + addr2 + "</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<th width=25%>전화</th>");
		out.println("<td width=75%>" + vo.getPhone() + "</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<th width=25%>음식종류</th>");
		out.println("<td width=75%>" + vo.getType() + "</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<th width=25%>가격대</th>");
		out.println("<td width=75%>" + vo.getPrice() + "</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<th width=25%>주차</th>");
		out.println("<td width=75%>" + vo.getParking() + "</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<th width=25%>영업시간</th>");
		out.println("<td width=75%>" + vo.getTime() + "</td>");
		out.println("</tr>");
		out.println("<tr>");
		if (!vo.getMenu().equals("no")) {
			out.println("<th width=25%>메뉴</th>");
			out.println("<td width=75%>");
			st = new StringTokenizer(vo.getMenu(), "원");
			out.println("<ul>");
			while (st.hasMoreTokens()) {
				out.println("<li>");
				out.println(st.nextToken() + "원");
				out.println("</li>");
			}
			out.println("</ul>");
			out.println("</td>");
			out.println("</tr>");
		}
		
		out.println("<tr>");
		out.println("<td class=text-right colspan=2>");
		out.println("<a href=# class=\"btn btn-xs btn-danger\">예약하기</a>");
		out.println("<a href=# class=\"btn btn-xs btn-success\">찜하기</a>");
		out.println("<a href=# class=\"btn btn-xs btn-warning\">좋아요</a>");
		out.println("<a href=\"FoodListServlet?cno="+vo.getCno()+"\" class=\"btn btn-xs btn-info\">목록</a>");
		out.println("</td>");
		out.println("</tr>");
		out.println("");
		out.println("");
		out.println("</table>");

		out.println("</div>");

		// 지도출력
		out.println("<div class=col-sm-4>");
		out.write("<div id=\"map\" style=\"width:100%;height:350px;\"></div>\n");
		out.write("<script>\n");
		out.write("		var mapContainer = document.getElementById('map'), // 지도를 표시할 div \n");
		out.write("		    mapOption = {\n");
		out.write("		        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표\n");
		out.write("		        level: 3 // 지도의 확대 레벨\n");
		out.write("		    };  \n");
		out.write("		\n");
		out.write("		// 지도를 생성합니다    \n");
		out.write("		var map = new kakao.maps.Map(mapContainer, mapOption); \n");
		out.write("		\n");
		out.write("		// 주소-좌표 변환 객체를 생성합니다\n");
		out.write("		var geocoder = new kakao.maps.services.Geocoder();\n");
		out.write("		\n");
		out.write("		// 주소로 좌표를 검색합니다\n");
		out.write("		geocoder.addressSearch('");
		out.write(addr1);
		out.write("', function(result, status) {\n");
		out.write("		\n");
		out.write("		    // 정상적으로 검색이 완료됐으면 \n");
		out.write("		     if (status === kakao.maps.services.Status.OK) {\n");
		out.write("		\n");
		out.write("		        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);\n");
		out.write("		\n");
		out.write("		        // 결과값으로 받은 위치를 마커로 표시합니다\n");
		out.write("		        var marker = new kakao.maps.Marker({\n");
		out.write("		            map: map,\n");
		out.write("		            position: coords\n");
		out.write("		        });\n");
		out.write("		\n");
		out.write("		        // 인포윈도우로 장소에 대한 설명을 표시합니다\n");
		out.write("		        var infowindow = new kakao.maps.InfoWindow({\n");
		out.write("		            content: '<div style=\"width:150px;text-align:center;padding:6px 0;\">");
		out.write(vo.getName());
		out.write("</div>'\n");
		out.write("		        });\n");
		out.write("		        infowindow.open(map, marker);\n");
		out.write("		\n");
		out.write("		        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다\n");
		out.write("		        map.setCenter(coords);\n");
		out.write("		    } \n");
		out.write("		});    \n");
		out.write("		</script>\n");
		out.println("</div>");
		out.println("");
		out.println("");
		out.println("");
		out.println("");

		out.println("</div>"); // row
		out.println("</div>"); // center
		out.println("</body>");
		out.println("</html>");

	}

}
