package com.example.demo.DAO;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DbUtil{

		
	public static Connection CreatesConnectionToMYSQL() throws Exception {
		
		Properties prop = new Properties();
		InputStream inputStream = DbUtil.class.getClassLoader().getResourceAsStream("application.properties");
		String url, user, password;              
		prop.load(inputStream);
        url = prop.getProperty("spring.datasource.url");
        user = prop.getProperty("spring.datasource.username");
        password = prop.getProperty("spring.datasource.password");
        
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection cx = DriverManager.getConnection
				(url
				,user
				,password);		
		return cx;
	}
	

}