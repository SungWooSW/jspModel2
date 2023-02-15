<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
// Controller로 이동
response.sendRedirect("member?param=login"); //서블릿 링크가 member. param값은 login을 가지고 서블릿 링크가 member인 서블릿(MemberController.java)으로 이동

%>


</body>
</html>