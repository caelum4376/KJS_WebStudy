package com.sist.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Model {
	String execute(HttpServletRequest request, HttpServletResponse response);
}
