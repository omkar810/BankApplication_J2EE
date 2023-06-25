package com.bankapplication.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bankapplication.helperclass.Helper;

@WebServlet("/UpdateUser")
public class UpdateUserForm extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     String idString=req.getParameter("id");
     int id=Integer.parseInt(idString);
     String name=req.getParameter("name");
     String ageString=req.getParameter("age");
     int age=Integer.parseInt(ageString);
     String address=req.getParameter("address");
     try {
    	 Class.forName("com.mysql.cj.jdbc.Driver");
    	 Connection con=Helper.getConnection();
    	 PreparedStatement psUpdate=con.prepareStatement("update account set name=?,age=?,address=? where id=?");
    	 psUpdate.setString(1, name);
    	 psUpdate.setInt(2, age);
    	 psUpdate.setString(3, address);
    	 psUpdate.setInt(4, id);
    	 psUpdate.execute();
    	 RequestDispatcher rd=req.getRequestDispatcher("AdminDashboard.html");
    	 PrintWriter pw=resp.getWriter();
    	 rd.include(req, resp);
    	 pw.write("<h4 align='center' style='color:green'>Successfully updated..</h4>");
     }
     catch (Exception e) {
		System.out.println("Something wrong");
	}
	}
}
