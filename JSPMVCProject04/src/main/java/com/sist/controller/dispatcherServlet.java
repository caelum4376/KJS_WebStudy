package com.sist.controller;

import java.io.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.model.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
// main.do, categoryy.do food_list.do, food_detail.do...
@WebServlet("*.do")
public class dispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String, Model> clsMap = new HashMap<String, Model>();
	public void init(ServletConfig config) throws ServletException {
		try {
			File file = new File("/Users/caelum/Documents/webDev/webStudy/JSPMVCProject04/src/main/webapp/WEB-INF/cmd.xml");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			// xml파서기
			Document doc = db.parse(file);
			Element beans = doc.getDocumentElement(); // 루트
			NodeList list = beans.getElementsByTagName("bean");
			for (int i=0; i<list.getLength(); i++) {
				Element bean = (Element)list.item(i);
				String id = bean.getAttribute("id");
				String cls = bean.getAttribute("class");
				
				Class clsName = Class.forName(cls);
				Object obj = clsName.getDeclaredConstructor().newInstance();
				
				clsMap.put(id, (Model)obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cmd = request.getRequestURI();
		cmd = cmd.substring(cmd.lastIndexOf("/")+1);
		System.out.println("Controller : " + request);
		Model model = clsMap.get(cmd);
		String jsp = model.handlerRequest(request, response);
		RequestDispatcher rd = request.getRequestDispatcher(jsp);
		rd.forward(request, response);
	}

}
