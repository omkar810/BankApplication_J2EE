package com.bankapplication.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bankapplication.helperclass.Helper;
@WebServlet("/loginadmin-req")
public class LoginAdmin extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email=req.getParameter("email");
		String password=req.getParameter("password");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=Helper.getConnection();
			PreparedStatement psVerify=con.prepareStatement("select * from admin where email=? and password=?");
			psVerify.setString(1, email);
			psVerify.setString(2, password);
			ResultSet rs=psVerify.executeQuery();
			if(rs.next()) {
				HttpSession hs=req.getSession();
				hs.setAttribute("email", email);
				hs.setAttribute("password", password);
				RequestDispatcher rd=req.getRequestDispatcher("AdminDashboard.html");
				rd.forward(req, resp);
			}
			else {
				PrintWriter pw=resp.getWriter();
				RequestDispatcher rd=req.getRequestDispatcher("loginadmin.html");
				rd.include(req, resp);
				pw.write("<h4 align='center' style='color:red'>Invalid Credentials..</h4>");
			}
		} catch (Exception e) {
			System.out.println("Something wrong..");
		}
	}
}
