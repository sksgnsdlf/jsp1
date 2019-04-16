<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h4>부서수정</h4>
<%--
	//단건조회
	String department_id= request.getParameter("department_id");
	DeptBeans beans = DeptDAO.getInstance().getDept(department_id);
	request.setAttribute("beans", beans);
		
--%>
<form action="/dev/updateDept.do" method="post">
	부서번호<input name="department_id" value="${beans.department_id}"/><br>
	부서명<input name="department_name"  value="${beans.department_name}"/><br>
	지역<input name="location_id" value="${beans.location_id}"/><br>
	매니저<input name="manager_id" value="${beans.manager_id}"/><br>
	<button>수정</button>
	<a href="deleteDept.do?department_id=${beans.department_id}">삭제</a>
	<br>	
</form>
</body>
</html>