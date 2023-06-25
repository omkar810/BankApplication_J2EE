package com.bankapplication.helperclass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Helper {
   public static Connection getConnection() throws SQLException {
	   return DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","Krishna@123");
   }
}
