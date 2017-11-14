package checkIn;

import java.sql.Connection;
import java.sql.Date;
import java.util.Scanner;

public class CheckInHelper {
private Connection conn;
	
	public static final int NUMBER_OF_ATTRIBUTES = 8;
	
	public CheckInHelper(Connection conn) {
		this.conn = conn;
	}
	
	public void add(Scanner scan) {
		CheckInDB pdb = new CheckInDB(conn);
		while(true) {
			System.out.println("Enter the patient information in the following format: Id, Name, SSN, Date of Birth (YYYY-MM-DD), Gender, Phone Number, Address, Status");
			System.out.print("> ");
			String in = scan.nextLine().toLowerCase();
			if (in.equals("b") || in.equals("back")) {
				return;
			}
			String[] inputArray = in.split(",");
			if (inputArray.length == NUMBER_OF_ATTRIBUTES) {
				CheckIn p = new CheckIn();
				try {
					int id = Integer.parseInt(inputArray[0].trim());
					if(pdb.getById(id) == null) {
						p.setId(id);
					} else {
						System.out.println("An entry already exists with that id");
						continue;
					}
				} catch (NumberFormatException e) {
					System.out.println("Invalid Id");
					continue;
				}
				p.setSsn(inputArray[1].trim());
				p.setName(inputArray[2].trim());
				try {
					p.setDob(Date.valueOf(inputArray[3].trim()));
				} catch (IllegalArgumentException e) {
					System.out.println("Invalid Date");
					continue;
				}
				p.setGender(inputArray[4].trim());
				p.setPhone(inputArray[5].trim());
				p.setAddress(inputArray[6].trim());
				p.setStatus(inputArray[7].trim());
				if (pdb.insert(p)) {
					System.out.println("CheckIn successfully added");
				} else {
					System.out.println("Error adding patient");
				}
				return;
			} else {
				System.out.println("Invalid Formatting");
			}
		}
	}
	
	public void update(Scanner scan) {
		CheckInDB pdb = new CheckInDB(conn);
		CheckIn p = null;
		int id;
		while(true) {
			System.out.println("Enter the id of the patient entry to update");
			System.out.print("> ");
			String in = scan.nextLine().trim().toLowerCase();
			if (in.equals("b") || in.equals("back")) {
				return;
			}
			try {
				id = Integer.parseInt(in);
				p = pdb.getById(id);
				if (p == null) {
					System.out.println("No patient found with that ID");
					continue;
				}
				break;
			} catch (NumberFormatException e) {
				System.out.println("Invalid id");
			}
		}
		while(true) {
			System.out.println("Enter new data for this entry in the following format: Id, Name, SSN, Date of Birth (YYYY-MM-DD), Gender, Phone Number, Address, Status");
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
						int newId = Integer.parseInt(inputArray[0].trim());
						if(pdb.getById(newId) == null) {
							p.setId(newId);
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
					p.setName(inputArray[1].trim());
				}
				if (!inputArray[3].trim().equals("*")) {
					try {
						p.setDob(Date.valueOf(inputArray[3].trim()));
					} catch (IllegalArgumentException e) {
						System.out.println("Invalid Date");
						continue;
					}
				}
				if (!inputArray[4].trim().equals("*")) {
					p.setGender(inputArray[4].trim());	
				}
				if (!inputArray[5].trim().equals("*")) {
					p.setPhone(inputArray[5].trim());
				}
				if (!inputArray[6].trim().equals("*")) {
					p.setAddress(inputArray[6].trim());
				}
				if (!inputArray[7].trim().equals("*")) {
					p.setStatus(inputArray[7].trim());
				}
				if(pdb.update(id, p)) {
					System.out.println("CheckIn successfully updated");
				} else {
					System.out.println("Error updating patient");
				}
				return;
			} else {
				System.out.println("Invalid Formatting");
			}
		}
	}
	
	public void delete(Scanner scan) {
		CheckInDB pdb = new CheckInDB(conn);
		int id;
		while(true) {
			System.out.println("Enter the id of the patient entry to delete");
			System.out.print("> ");
			String in = scan.nextLine().trim().toLowerCase();
			if (in.equals("b") || in.equals("back")) {
				return;
			}
			try {
				id = Integer.parseInt(in);
			} catch (NumberFormatException e) {
				System.out.println("Invalid id");
				continue;
			}
			if(pdb.getById(id) != null) {
				if(pdb.deleteById(id)) {
					System.out.println("CheckIn entry successfully deleted");
				} else {
					System.out.println("CheckIn entry could not be deleted");
				}
				return;
			} else {
				System.out.println("No entry found with that ID");
			}
		}
	}
}
