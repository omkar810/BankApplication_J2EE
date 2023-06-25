<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.io.PrintWriter"%>
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
      Long acc=(Long)session.getAttribute("accno");
	  long accno=(long)acc;
	  Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "Krishna@123");
		PreparedStatement ps = con.prepareStatement("delete from account where accountnumber=?");
		ps.setLong(1, accno);
		ps.execute();
		PrintWriter pw=response.getWriter();
		RequestDispatcher rd=request.getRequestDispatcher("loginuser.html");
		rd.include(request, response);
		pw.write("<h3 style='color: green'+ align='center'>Account deleted Successfully..</h3>");
	%>
</body>
</html>