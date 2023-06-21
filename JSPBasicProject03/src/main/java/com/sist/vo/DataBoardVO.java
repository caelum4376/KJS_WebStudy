package com.sist.vo;
// Beans => 데이터를 모아서 관리 및 전송 (VO, DTO)
// private 변수, getter / setter => 캡슐화 방식 (데이터 보호)
/*
 * 		빈즈 연결
 * 		--------- jsp 액션태그
 * 		<jsp:useBean>
 * 		<jsp:setProperty>
 * 		<jsp:getProperty>
 * 		<jsp:param>
 * 		***<jsp:include>
 * 		<jsp:forward>
 * 
 * 
 * 		~Bean => model1
 * 		~VO => Spring
 * 		~DTO => MyBatis
 */
import java.util.*;
public class DataBoardVO {
	private int no, hit, filesize;
	private String name, subject, content, pwd, filename, dbday;
	private Date regdate;
	
	public String getDbday() {
		return dbday;
	}
	public void setDbday(String dbday) {
		this.dbday = dbday;
	}
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public int getFilesize() {
		return filesize;
	}
	public void setFilesize(int filesize) {
		this.filesize = filesize;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
}
