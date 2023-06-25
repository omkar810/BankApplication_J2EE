<%@page import="java.io.PrintWriter"%>
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
<title>BelieveBank</title>
</head>
<body>
	 <!-- navbar -->
        <div class="nav-link">
            <nav class="navbar navbar-expand-lg navbar-light">
                <a class="navbar-brand" href="#"><span class="logo">BelieveBank</span></a>
                <button class="navbar-toggler" type="button" data-toggle="collapse"
                    data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
                    aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav center">
                    <li class="nav-item">
                            <a class="nav-link" href="ViewAll.jsp">
                                Customer List</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="UpdateById.html">
                                Update by Id</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="AddUser.html">
                                Add Customer</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="ViewByAccNo.html">
                                View by A/C no.</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="logoutAdmin.jsp">
                                Logout</a>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
	<%
	String idString = request.getParameter("id");
	int id = Integer.parseInt(idString);
	Class.forName("com.mysql.cj.jdbc.Driver");
	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "Krishna@123");
	PreparedStatement ps = con.prepareStatement("select * from account where id=?");
	ps.setInt(1, id);
	ResultSet rs = ps.executeQuery();
	if (rs.next()) {
	%>
    <div class="form-create-customer">
            <form action="UpdateUser" method="post" autocomplete="off">
                <h4>Update Customer</h4>
                <label>Id:</label>
                <input type="number" name="id" value=<%=rs.getInt(1)%> required readonly="readonly"><br>
                <label>Name:</label>
                <input type="text" name="name" value=<%=rs.getString(2)%> required ><br>
                <label>Age:</label>
                <input type="number" name="age" value=<%=rs.getInt(3)%> required><br>
                <label>A/C number:</label>
                <input type="number" name="accno" value=<%=rs.getLong(4)%> required readonly="readonly"><br>
                <label>Pin:</label>
                <input type="password" name="pin" value=<%=rs.getInt(5)%> required readonly="readonly"><br>
                <label>Balance:</label>
                <input type="number" name="balance" value=<%=rs.getDouble(6)%> required readonly="readonly"><br>
                <label>Address:</label>
                <textarea name="address" cols="23" rows="3" style="resize: none;" required><%=rs.getString(7)%></textarea>
                <input type="submit" value="Update" class="button button-err">
            </form>
        </div>
	<%
	} else {%>
	<h3 align='center' style='color: red'>Id not Found..</h3><br><h5 align='center' style='color: blue'><a href="UpdateById.html"><<< Go Back..</a></h5>
	<% }
	%>

</body>
</html>