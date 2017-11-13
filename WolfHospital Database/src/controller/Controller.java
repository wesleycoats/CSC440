package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

import staff.StaffHelper;

public class Controller {

	private static final String URL = "jdbc:mysql://localhost:3306/wolfhospital";
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	
	private static Connection conn;
	
	public static void main(String[] args) {
		
		Scanner scan = null;
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			String in = "";
			scan = new Scanner(System.in);
			while(true) {
				System.out.println("Enter a command (a-Add, u-Update, d-Delete, e-Exit)");
				System.out.print("> ");
				in = scan.nextLine();
				in = in.toLowerCase();
				if (in.equals("a") || in.equals("add")) {
					add(scan);
				} else if (in.equals("u") || in.equals("update")) {
					update(scan);
				} else if (in.equals("d") || in.equals("delete")) {
					delete();
				} else if (in.equals("e") || in.equals("exit")) {
					break;
				} else {
					System.out.println("Invalid command");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (scan != null) {
				scan.close();
			}
		}
	}
	
	private static void add(Scanner scan) {
		while(true) {
			System.out.println("What to add? (p-Patient, s-Staff, w-Ward, c-Check In, m-Medical Record, or b-Back to return)");
			System.out.print("> ");
			String in = scan.nextLine();
			in = in.toLowerCase();
			if(in.equals("p") || in.equals("patient")) {
				
			} else if (in.equals("s") || in.equals("staff")) {
				StaffHelper sh = new StaffHelper(conn);
				sh.add(scan);
				return;
			} else if (in.equals("w") || in.equals("ward")) {
			
			} else if (in.equals("c") || in.equals("check in")) {
			
			} else if (in.equals("m") || in.equals("medical record")) {
			
			} else if (in.equals("b") || in.equals("back")) {
				return;
			} else {
				System.out.println("Invalid Command");
			}
		}
	}

	private static void update(Scanner scan) {
		while(true) {
			System.out.println("What to update? (p-Patient, s-Staff, w-Ward, c-Check In, m-Medical Record, or b-Back to return)");
			System.out.print("> ");
			String in = scan.nextLine();
			in = in.toLowerCase();
			if(in.equals("p") || in.equals("patient")) {
				
			} else if (in.equals("s") || in.equals("staff")) {
				StaffHelper sh = new StaffHelper(conn);
				sh.update(scan);
				return;
			} else if (in.equals("w") || in.equals("ward")) {
			
			} else if (in.equals("c") || in.equals("check in")) {
			
			} else if (in.equals("m") || in.equals("medical record")) {
			
			} else if (in.equals("b") || in.equals("back")) {
				return;
			} else {
				System.out.println("Invalid Command");
			}
		}
	}

	private static void delete() {
		// TODO Auto-generated method stub
		
	}

}
