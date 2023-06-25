package com.bankapplication.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bankapplication.helperclass.Helper;

@WebServlet("/loginuser-req")
public class loginAccount extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fetchAccno = req.getParameter("accno");
		String fetchPin = req.getParameter("pin");

		long accno = Long.parseLong(fetchAccno);
		int pin = Integer.parseInt(fetchPin);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = Helper.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from account where accountnumber=? and pin=?");
			ps.setLong(1, accno);
			ps.setInt(2, pin);
			ResultSet rs = ps.executeQuery();
			PrintWriter pw = resp.getWriter();
			if (rs.next()) {
				HttpSession hs=req.getSession();
				hs.setAttribute("accno",accno);
				hs.setAttribute("pin",pin);
				RequestDispatcher rd = req.getRequestDispatcher("useroption.html");
				rd.forward(req, resp);
			} else {
				RequestDispatcher rd = req.getRequestDispatcher("loginuser.html");
				rd.include(req, resp);
				pw.write("<h4 align='center' style='color:red'>Invalid Credentials..</h4>");
			}

		} catch (Exception e) {
           System.out.println("Something wrong..");
		}
	}
}
