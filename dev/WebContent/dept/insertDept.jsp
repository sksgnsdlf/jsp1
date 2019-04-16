<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h4>부서등록</h4>
<form action="/dev/insertDept.do" method="post">
	부서번호<input name="department_id" /><br>
	부서명<input name="department_name" /><br>
	지역<input name="location_id" /><br>
	매니저<input name="manager_id" /><br>
	<button>등록</button><br>	
</form>
</body>
</html>