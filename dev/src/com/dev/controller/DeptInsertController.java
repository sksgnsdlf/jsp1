package com.dev.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dev.service.DeptService;
import com.dev.vo.DeptBeans;

public class DeptInsertController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//파라미터 받고
		DeptBeans beans = new DeptBeans();
		beans.setDepartment_id(request.getParameter("department_id"));
		beans.setDepartment_name(request.getParameter("department_name"));
		beans.setManager_id(request.getParameter("manager_id"));
		beans.setLocation_id(request.getParameter("location_id"));
		//서비스 호출
		DeptService.getInstance().deptInsert(beans);		
		//결과 저장		
		//뷰페이지 리턴		
		return "redirect:/dev/deptList.do";
	}
 
}
