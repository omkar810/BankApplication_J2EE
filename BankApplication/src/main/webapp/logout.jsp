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
session.invalidate();
RequestDispatcher rd = request.getRequestDispatcher("loginuser.html");
rd.forward(request, response);
%>
</body>
</html>