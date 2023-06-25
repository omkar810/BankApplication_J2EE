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
@WebServlet("/deposit")
public class DepositUser extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession hs=req.getSession();
		Long an=(Long)hs.getAttribute("accno");
		long accno=(long)an;
		String amt=req.getParameter("amount");
		double amount=Double.parseDouble(amt);
		PrintWriter pw=resp.getWriter();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = Helper.getConnection();
			PreparedStatement getBalance = con.prepareStatement("select balance from account where accountnumber=?");
			getBalance.setLong(1, accno);
			ResultSet rs=getBalance.executeQuery();
			rs.next();
			double newBalance=rs.getDouble(1)+amount;
			PreparedStatement ps = con.prepareStatement("update account set balance=? where accountnumber=?");
			ps.setDouble(1, newBalance);
			ps.setLong(2, accno);
			ps.execute();
			RequestDispatcher rd=req.getRequestDispatcher("useroption.html");
			rd.include(req, resp);
			pw.write("<h4 style='color:green' align='center'>Deposited successfully..</h4>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
