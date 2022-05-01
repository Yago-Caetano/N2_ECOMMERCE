package com.example.demo.Model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

//Reference: https://github.com/danielscarvalho/FTT-PEOPLE-WEB-DB

public class DbUtil {
	
	private static final String USERNAME = "root";
	
	private static final String PASSWORD = "123456";
	
	private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/n2_ecommerce";
	
	public static Connection CreatesConnectionToMYSQL() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		Connection cx = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
		
		return cx;
	}
}
