package staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffDB {
	private Connection conn;
	
	public StaffDB(Connection conn) {
		this.conn = conn;
	}
	
	public Staff getById(int id) {
		Staff s = null;
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM staff WHERE id = ?;");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				s = new Staff();
				s.setId(id);
				s.setName(rs.getString("name"));
				s.setDateOfBirth(rs.getDate("date_of_birth"));
				s.setGender(rs.getString("gender"));
				s.setPhoneNumber(rs.getString("phone"));
				s.setAddress(rs.getString("address"));
				s.setDepartment(rs.getString("department"));
				s.setJobTitle(rs.getString("job_title"));
				s.setProfessionalTitle(rs.getString("professional_title"));
			}
		} catch (SQLException e) {
			//TODO put something here
			e.printStackTrace();
		}
		return s;
	}
	
	public boolean insert(Staff s) {
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO staff VALUES (?,?,?,?,?,?,?,?,?);");
			stmt.setInt(1, s.getId());
			stmt.setString(2, s.getName());
			stmt.setDate(3, s.getDateOfBirth());
			stmt.setString(4, s.getGender());
			stmt.setString(5, s.getPhoneNumber());
			stmt.setString(6, s.getAddress());
			stmt.setString(7, s.getDepartment());
			stmt.setString(8, s.getJobTitle());
			stmt.setString(9, s.getProfessionalTitle());
			return (stmt.executeUpdate() > 0);
		} catch (SQLException e) {
			//TODO put something here
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(int id, Staff s) {
		try {
			PreparedStatement stmt = conn.prepareStatement("UPDATE staff SET id = ?, name = ?, date_of_birth = ?, "
															+ "gender = ?, phone = ?, "
															+ "address = ?, department = ?, "
															+ "job_title = ?, professional_title = ? "
															+ "WHERE id = ?;");
			stmt.setInt(1, s.getId());
			stmt.setString(2, s.getName());
			stmt.setDate(3, s.getDateOfBirth());
			stmt.setString(4, s.getGender());
			stmt.setString(5, s.getPhoneNumber());
			stmt.setString(6, s.getAddress());
			stmt.setString(7, s.getDepartment());
			stmt.setString(8, s.getJobTitle());
			stmt.setString(9, s.getProfessionalTitle());
			stmt.setInt(10, id);
			return (stmt.executeUpdate() > 0);
		} catch (SQLException e) {
			//TODO put something here
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteById(int id) {
		try {
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM staff WHERE id = ?;");
			stmt.setInt(1, id);
			return (stmt.executeUpdate() > 0);
		} catch (SQLException e) {
			//TODO put something here
			e.printStackTrace();
		}
		return false;
	}
}
