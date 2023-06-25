<%@page import="java.io.PrintWriter"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
	integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js"
	integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js"
	integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
	crossorigin="anonymous"></script>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="stylesheet" href="./style/navbar.css">
<link rel="stylesheet" href="./style/forms.css">
<style>
.form-user-deposit {
	height: 320px;
	width: 350px;
	background-color: rgb(233, 233, 233);
	margin: 3% auto;
	text-align: center;
	padding: 40px;
	border-radius: 15px;
	box-shadow: 7px 7px 7px rgb(189, 189, 189);
}

h3 {
	text-align: center;
}
</style>
<title>BelieveBank</title>
</head>
<body>
	<%
	Long accnoObj = (Long) session.getAttribute("accno");
	long accnoSession = (long) accnoObj;
	Integer objPin = (Integer) session.getAttribute("pin");
	int pinSession = (int) objPin;
	String accnoS = request.getParameter("accno");
	String amountS = request.getParameter("amount");
	String pinS = request.getParameter("pin");
	long accno = Long.parseLong(accnoS);
	double amount = Double.parseDouble(amountS);
	int pin = Integer.parseInt(pinS);
	Class.forName("com.mysql.cj.jdbc.Driver");
	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "Krishna@123");
	PreparedStatement ps = con.prepareStatement("select balance from account where accountnumber=? and pin=?");
	ps.setLong(1, accnoSession);
	ps.setInt(2, pin);
	ResultSet rs = ps.executeQuery();
	PrintWriter pw = response.getWriter();
	RequestDispatcher rd = request.getRequestDispatcher("sendmoney.html");
	if (rs.next()) {
		PreparedStatement psReceiver = con.prepareStatement("select balance from account where accountnumber=?");
		psReceiver.setLong(1, accno);
		ResultSet rsReceiver = psReceiver.executeQuery();
		if (rsReceiver.next()) {
			if (rs.getDouble(1) >= amount) {
		double avlBal = rs.getDouble(1) - amount;
		PreparedStatement psForSender = con.prepareStatement("update account set balance=? where accountnumber=?");
		psForSender.setDouble(1, avlBal);
		psForSender.setLong(2, accnoSession);
		psForSender.execute();
		PreparedStatement psForReceiver = con
				.prepareStatement("update account set balance=? where accountnumber=?");
		double updBal = rsReceiver.getDouble(1) + amount;
		psForReceiver.setDouble(1, updBal);
		psForReceiver.setLong(2, accno);
		psForReceiver.execute();
		RequestDispatcher rdSuccess = request.getRequestDispatcher("useroption.html");
		rdSuccess.include(request, response);
		pw.write("<h3 style='color: green'>Successfully sent..</h3>");
		con.close();
			} else {
		rd.include(request, response);
		pw.write("<h3 style='color: red'>Insufficient balance..</h3>");
			}
		} else {
			rd.include(request, response);
			pw.write("<h3 style='color: red'>Invalid Receiver account number..</h3>");
		}
	} else {
		rd.include(request, response);
		pw.write("<h3 style='color: red'>Invalid pin..</h3>");
	}
	%>

</body>
</html>