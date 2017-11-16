package staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StaffDB {
	
	/** The database connection to use */
    private final Connection conn;

    /**
     * Constructor
     * @param conn The database connection to use
     */
    public StaffDB ( final Connection conn ) {
        this.conn = conn;
    }

    /**
     * Returns a staff object with the given id
     * @param id The id of the staff member
     * @return The staff member with the given id
     */
    public Staff getById ( final int id ) {
        Staff s = null;
        try {
            final PreparedStatement stmt = conn.prepareStatement( "SELECT * FROM staff WHERE id = ?;" );
            stmt.setInt( 1, id );
            final ResultSet rs = stmt.executeQuery();
            if ( rs.next() ) {
                s = new Staff();
                s.setId( id );
                s.setName( rs.getString( "name" ) );
                s.setAge( rs.getDate( "date_of_birth" ) );
                s.setDateOfBirth( rs.getDate( "date_of_birth" ) );
                s.setGender( rs.getString( "gender" ) );
                s.setPhoneNumber( rs.getString( "phone" ) );
                s.setAddress( rs.getString( "address" ) );
                s.setDepartment( rs.getString( "department" ) );
                s.setJobTitle( rs.getString( "job_title" ) );
                s.setProfessionalTitle( rs.getString( "professional_title" ) );
            }
        }
        catch ( final SQLException e ) {
            // TODO put something here
            e.printStackTrace();
        }
        return s;
    }

    /**
     * Inserts a staff entry into the database
     * @param s The staff object to insert
     * @return True if the entry was successfully inserted
     */
    public boolean insert ( final Staff s ) {
        try {
            final PreparedStatement stmt = conn.prepareStatement( "INSERT INTO staff VALUES (?,?,?,?,?,?,?,?,?);" );
            stmt.setInt( 1, s.getId() );
            stmt.setString( 2, s.getName() );
            stmt.setDate( 3, s.getDateOfBirth() );
            stmt.setString( 4, s.getGender() );
            stmt.setString( 5, s.getPhoneNumber() );
            stmt.setString( 6, s.getAddress() );
            stmt.setString( 7, s.getDepartment() );
            stmt.setString( 8, s.getJobTitle() );
            stmt.setString( 9, s.getProfessionalTitle() );
            return ( stmt.executeUpdate() > 0 );
        }
        catch ( final SQLException e ) {
            // TODO put something here
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates the staff entry with the given id
     * @param id The id of the staff to update
     * @param s The staff object to update with
     * @return True if the entry was successfully updated
     */
    public boolean update ( final int id, final Staff s ) {
        try {
            final PreparedStatement stmt = conn
                    .prepareStatement( "UPDATE staff SET id = ?, name = ?, date_of_birth = ?, "
                            + "gender = ?, phone = ?, " + "address = ?, department = ?, "
                            + "job_title = ?, professional_title = ? " + "WHERE id = ?;" );
            stmt.setInt( 1, s.getId() );
            stmt.setString( 2, s.getName() );
            stmt.setDate( 3, s.getDateOfBirth() );
            stmt.setString( 4, s.getGender() );
            stmt.setString( 5, s.getPhoneNumber() );
            stmt.setString( 6, s.getAddress() );
            stmt.setString( 7, s.getDepartment() );
            stmt.setString( 8, s.getJobTitle() );
            stmt.setString( 9, s.getProfessionalTitle() );
            stmt.setInt( 10, id );
            return ( stmt.executeUpdate() > 0 );
        }
        catch ( final SQLException e ) {
            // TODO put something here
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes the staff with the given id
     * @param id The id of the staff to delete
     * @return True if the staff was successfully deleted
     */
    public boolean deleteById ( final int id ) {
        try {
            final PreparedStatement stmt = conn.prepareStatement( "DELETE FROM staff WHERE id = ?;" );
            stmt.setInt( 1, id );
            return ( stmt.executeUpdate() > 0 );
        }
        catch ( final SQLException e ) {
            // TODO put something here
            e.printStackTrace();
        }
        return false;
    }

	/**
	 * Gets all information on staff grouped by their department.
	 *
	 * @return A list of staff info grouped by department.
	 */
	public ArrayList<Staff> getStaffInfoGroupedByDepartment(int orderby) {
		String str = null;
		try {
			switch (orderby) {
				case 1:
					str = "SELECT * FROM staff ORDER BY department";
					break;
				case 2:
					str = "SELECT * FROM staff ORDER BY job_title";
					break;
				case 3:
					str = "SELECT * FROM staff ORDER BY professional_title";
					break;
				default:
					str = "SELECT * FROM staff ORDER BY department";
					break;
			};

			final PreparedStatement stmt = conn.prepareStatement( str );
			ResultSet rs = stmt.executeQuery();

			ArrayList<Staff> staff_info = new ArrayList<>();
			while (rs.next()) {
				Staff s = new Staff();
				s.setId(rs.getInt("id"));
				s.setName(rs.getString("name"));
				s.setDateOfBirth(rs.getDate("date_of_birth"));
				s.setGender(rs.getString("gender"));
				s.setPhoneNumber(rs.getString("phone"));
				s.setAddress(rs.getString("address"));
				s.setDepartment(rs.getString("department"));
				s.setJobTitle(rs.getString("job_title"));
				s.setProfessionalTitle(rs.getString("professional_title"));

				staff_info.add(s);
			}
			return staff_info;
		}
		catch ( final SQLException e ) {
			// TODO put something here
			e.printStackTrace();
		}

		return null;
	}
}
