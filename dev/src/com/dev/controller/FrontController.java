package com.dev.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class FrontController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	String charset = null;
	HashMap<String, Controller> list = null;

	@Override
	public void init(ServletConfig sc) throws ServletException {

		charset = sc.getInitParameter("charset");

		list = new HashMap<String, Controller>();
		list.put("/memberInsert.do", new MemberInsertController());
		list.put("/memberSearch.do", new MemberSearchController());
		list.put("/memberUpdate.do", new MemberUpdateController());
		list.put("/memberDelete.do", new MemberDeleteController());
		list.put("/deptList.do", new DeptListController());
		list.put("/insertDept.do", new DeptInsertController());
		list.put("/updateDeptForm.do", new DeptUpdateFormController()); //수정홈
		list.put("/updateDept.do", new DeptUpdateController());
		list.put("/deleteDept.do", new DeptDeleteController());
		//list.put("/memberList.do", new MemberListController());

	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding(charset);
		// uri : /dev/memberList.do
		String url = request.getRequestURI();
		// /dev
		String contextPath = request.getContextPath();
		// = > /memberList.do
		String path = url.substring(contextPath.length());

		// 컨트롤러 찾기
		Controller subController = list.get(path); // ex) MemberListController
		// 컨트롤러 실행 ,다형성(실행결과가 다 다름), 참조하는 자식객체의 메서드를 호출
		// subController.execute(request, response);
		String view = subController.execute(request, response);
		if (view != null) {
			if (view.startsWith("redirect:")) {
				response.sendRedirect(view.substring(9));
			} else {
				HttpUtil.forward(request, response, view);
			}
		}
	}
}
