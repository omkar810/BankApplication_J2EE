package com.bankapplication.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bankapplication.helperclass.Helper;

@WebServlet("/createadmin")
public class CreateAdmin extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idString = req.getParameter("id");
		int id = Integer.parseInt(idString);
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		PrintWriter pw = resp.getWriter();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = Helper.getConnection();
			PreparedStatement psIdCheck = con.prepareStatement("select * from admin where id=?");
			psIdCheck.setInt(1, id);
			ResultSet rs = psIdCheck.executeQuery();
			RequestDispatcher rd = req.getRequestDispatcher("createadmin.html");
			Statement st = con.createStatement();
			ResultSet rsCheckEmail = st.executeQuery("select * from admin");
			while (rsCheckEmail.next()) {
				if (rsCheckEmail.getString(2).equals(email)) {
					rd.include(req, resp);
					pw.write("<h4 align='center' style='color:red'>Email id already exist..</h4>");
					return;
				}
			}
			if (rs.next()) {
				rd.include(req, resp);
				pw.write("<h4 align='center' style='color:red'>Id not available. <br>Please enter unique id.</h4>");
			}
			else {
				PreparedStatement psInsert = con.prepareStatement("insert into admin values(?,?,?)");
				psInsert.setInt(1, id);
				psInsert.setString(2, email);
				psInsert.setString(3, password);
				psInsert.execute();
				RequestDispatcher rdCreated = req.getRequestDispatcher("loginadmin.html");
				rdCreated.include(req, resp);
				pw.write("<h4 align='center' style='color:green'>Account created Successfully..</h4>");
			}
			
		} catch (Exception e) {
			System.out.println("Something wrong..");
		}

	}
}
