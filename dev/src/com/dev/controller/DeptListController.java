package com.dev.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dev.service.DeptService;
import com.dev.vo.DeptBeans;

public class DeptListController implements Controller{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//파라미터받고
				
		
		//서비스 호출
		ArrayList<DeptBeans> list = DeptService.getInstance().selectAll();
		
		//결과페이지 리턴
		request.setAttribute("list", list);
		
		return "/dept/deptList.jsp";
	}	
}
