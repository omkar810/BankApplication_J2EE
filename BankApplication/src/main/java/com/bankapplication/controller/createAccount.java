package com.bankapplication.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bankapplication.helperclass.Helper;
import com.mysql.cj.xdevapi.PreparableStatement;

@WebServlet("/createuser")
public class createAccount extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     String idString=req.getParameter("id");
     String name=req.getParameter("name");
     String ageString=req.getParameter("age");
     String address=req.getParameter("address");
     String pinString=req.getParameter("pin");
     
     int id=Integer.parseInt(idString);
     int age=Integer.parseInt(ageString);
     int pin=Integer.parseInt(pinString);
     double balance=5000;
     Random rnd=new Random();
     long accno=rnd.nextLong(1000000000000l);
     PrintWriter pw=resp.getWriter();
     try {
    	 Class.forName("com.mysql.cj.jdbc.Driver");
    	 Connection con=Helper.getConnection();
    	 PreparedStatement psIdCheck=con.prepareStatement("select * from account where id=?");
    	 psIdCheck.setInt(1, id);
    	 ResultSet rs=psIdCheck.executeQuery();
    	 if(rs.next()) {
    		 RequestDispatcher rd=req.getRequestDispatcher("createuser.html");
             rd.include(req, resp);
             pw.write("<h4 align='center' style='color:red'>Id not available. <br>Please enter unique id.</h4>");
    	 }
    	 else {
    		 PreparedStatement ps=con.prepareStatement("insert into account(id,name,age,address,pin,balance,accountnumber) values(?,?,?,?,?,?,?)");
        	 ps.setInt(1, id);
        	 ps.setString(2, name);
        	 ps.setInt(3, age);
        	 ps.setString(4, address);
        	 ps.setInt(5, pin);
        	 ps.setDouble(6, balance);
        	 ps.setLong(7, accno);
        	 ps.execute();
        	 RequestDispatcher rd=req.getRequestDispatcher("loginuser.html");
             rd.include(req, resp);
             pw.write("<h4 align='center' style='color:green'>Account created Successfully..<br>"+"Your Account number is: "+accno+"</h4>");
		}
     }
     catch (Exception e) {
		System.out.println("Error");
	}
	}
}
