package com.dev.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.dev.service.MemberService;

public class MemberDeleteController implements Controller {
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Parameter ����
		String id = request.getParameter("id");

		// Service ��ü�� �޼��� ȣ��
		MemberService service = MemberService.getInstance();
		service.memberDelete(id);

		// Output View �������� �̵�
		//HttpUtil.forward(request, response, "/result/memberDeleteOutput.jsp");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print("<script>alert('ȸ������ ������ �Ϸ�Ǿ����ϴ�.');");
		out.print("location='memberList.do'");
		out.print("</script>");
		return null;
	}
}
