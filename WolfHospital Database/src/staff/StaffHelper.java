package staff;

import java.sql.Connection;
import java.sql.Date;
import java.util.Scanner;

public class StaffHelper {
	
	private Connection conn;
	
	public static final int NUMBER_OF_ATTRIBUTES = 9;
	
	public StaffHelper(Connection conn) {
		this.conn = conn;
	}
	
	public void add(Scanner scan) {
		StaffDB sdb = new StaffDB(conn);
		while(true) {
			System.out.println("Enter the staff information in the following format: Id, Name, Date of Birth (YYYY-MM-DD), Gender, Phone Number, Address, Department, Job Title, Professional Title");
			System.out.print("> ");
			String in = scan.nextLine().toLowerCase();
			if (in.equals("b") || in.equals("back")) {
				return;
			}
			String[] inputArray = in.split(",");
			if (inputArray.length == NUMBER_OF_ATTRIBUTES) {
				Staff s = new Staff();
				try {
					if(sdb.getById(Integer.parseInt(inputArray[0].trim())) != null) {
						s.setId(Integer.parseInt(inputArray[0].trim()));
					} else {
						System.out.println("An entry already exists with that id");
						continue;
					}
				} catch (NumberFormatException e) {
					System.out.println("Invalid Id");
					continue;
				}
				s.setName(inputArray[1].trim());
				try {
					s.setDateOfBirth(Date.valueOf(inputArray[2].trim()));
				} catch (IllegalArgumentException e) {
					System.out.println("Invalid Date");
					continue;
				}
				s.setGender(inputArray[3].trim());
				s.setPhoneNumber(inputArray[4].trim());
				s.setAddress(inputArray[5].trim());
				s.setDepartment(inputArray[6].trim());
				s.setJobTitle(inputArray[7].trim());
				s.setProfessionalTitle(inputArray[8].trim());
				if (sdb.insert(s)) {
					System.out.println("Staff successfully added");
				} else {
					System.out.println("Error adding staff");
				}
				return;
			} else {
				System.out.println("Invalid Formatting");
			}
		}
	}
	
	public void update(Scanner scan) {
		StaffDB sdb = new StaffDB(conn);
		Staff s = null;
		int id;
		while(true) {
			System.out.println("Enter the id of the staff entry to update");
			System.out.print("> ");
			String in = scan.nextLine().trim().toLowerCase();
			if (in.equals("b") || in.equals("back")) {
				return;
			}
			try {
				id = Integer.parseInt(in);
				s = sdb.getById(id);
				if (s == null) {
					System.out.println("No staff found with that ID");
					continue;
				}
				break;
			} catch (NumberFormatException e) {
				System.out.println("Invalid id");
			}
		}
		while(true) {
			System.out.println("Enter new data for this entry in the following format: Id, Name, Date of Birth (YYYY-MM-DD), Gender, Phone Number, Address, Department, Job Title, Professional Title");
			System.out.println("Enter * to keep an entry the same");
			System.out.print("> ");
			String in = scan.nextLine().toLowerCase();
			if (in.equals("b") || in.equals("back")) {
				return;
			}
			String[] inputArray = in.split(",");
			if (inputArray.length == NUMBER_OF_ATTRIBUTES) {
				if(!inputArray[0].trim().equals("*")) {
					try {
						if(sdb.getById(Integer.parseInt(inputArray[0].trim())) == null) {
							s.setId(Integer.parseInt(inputArray[0].trim()));
						} else {
							System.out.println("An entry already exists with that id");
							continue;
						}
					} catch (NumberFormatException e) {
						System.out.println("Invalid Id");
						continue;
					}
				}
				if (!inputArray[1].trim().equals("*")) {
					s.setName(inputArray[1].trim());
				}
				if (!inputArray[2].trim().equals("*")) {
					try {
						s.setDateOfBirth(Date.valueOf(inputArray[2].trim()));
					} catch (IllegalArgumentException e) {
						System.out.println("Invalid Date");
						continue;
					}
				}
				if (!inputArray[3].trim().equals("*")) {
					s.setGender(inputArray[3].trim());	
				}
				if (!inputArray[4].trim().equals("*")) {
					s.setPhoneNumber(inputArray[4].trim());
				}
				if (!inputArray[5].trim().equals("*")) {
					s.setAddress(inputArray[5].trim());
				}
				if (!inputArray[6].trim().equals("*")) {
					s.setDepartment(inputArray[6].trim());
				}
				if (!inputArray[7].trim().equals("*")) {
					s.setJobTitle(inputArray[7].trim());
				}
				if (!inputArray[8].trim().equals("*")) {
					s.setProfessionalTitle(inputArray[8].trim());
				}
				if(sdb.update(id, s)) {
					System.out.println("Staff successfully updated");
				} else {
					System.out.println("Error updating staff");
				}
				return;
			} else {
				System.out.println("Invalid Formatting");
			}
		}
	}
}