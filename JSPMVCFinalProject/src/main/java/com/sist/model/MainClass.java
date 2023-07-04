package com.sist.model;
import java.util.*;
import java.io.*;
import java.lang.reflect.Method;
class Board {
	@RequestMapping("list.do")
	public void boardList() {
		System.out.println("데이터 목록");
	}
	@RequestMapping("isnert.do")
	public void boardInsert() {
		System.out.println("데이터 추가");
	}
	@RequestMapping("update.do")
	public void boardUpdate() {
		System.out.println("데이터 수정");
	}
	@RequestMapping("delete.do")
	public void boardDelete() {
		System.out.println("데이터 삭제");
	}
	@RequestMapping("find.do")
	public void boardFind() {
		System.out.println("데이터 찾기");
	}
}
public class MainClass {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.print("직접 입력 : ");
//		Board board = new Board();
		String url = scan.next();
	//	if (url.equals("list.do")) {
	//		board.boardList();
	//	}
	//	if (url.equals("insert.do")) {
	//		board.boardInsert();
	//	}
	//	if (url.equals("update.do")) {
	//		board.boardUpdate();
	//	}
	//	if (url.equals("delete.do")) {
	//		board.boardDelete();
	//	}
	//	if (url.equals("find.do")) {
	//		board.boardFind();
	//	}
		try {
			Class clsName = Class.forName("com.sist.model.Board");
			
			// class의 정보 읽기
			Object obj = clsName.getDeclaredConstructor(null).newInstance();
			
			// class에 정의된 모든 메소드를 읽는다
			Method[] methods = clsName.getDeclaredMethods();
			
			for (Method m:methods) {
				// 메소드 위에 어노테이션 확인
				RequestMapping rm = m.getAnnotation(RequestMapping.class);
				if (rm.value().equals(url)) {
					m.invoke(obj,  null);
				}
			}
		} catch (Exception e) {
			
		}
	}
}
