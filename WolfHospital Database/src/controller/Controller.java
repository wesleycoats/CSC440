package controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class Controller {

	private static final String URL = "jdbc:mysql://localhost:3306/wolfhospital";
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	
	private static Connection conn;
	
	public static void main(String[] args) {
		
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (Exception e) {
			
		}
	}

}
