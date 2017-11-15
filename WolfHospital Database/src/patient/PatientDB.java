package patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientDB {
    private final Connection conn;

    public PatientDB ( final Connection conn ) {
        this.conn = conn;
    }

    public Patient getById ( final int id ) {
        Patient s = null;
        try {
            final PreparedStatement stmt = conn.prepareStatement( "SELECT * FROM patient WHERE id = ?;" );
            stmt.setInt( 1, id );
            final ResultSet rs = stmt.executeQuery();
            if ( rs.next() ) {
                s = new Patient();
                s.setId( id );
                s.setName( rs.getString( "name" ) );
                s.setSsn( rs.getString( "patientSSN" ) );
                s.setAge( rs.getDate( "date_of_birth" ) );
                s.setDob( rs.getDate( "date_of_birth" ) );
                s.setGender( rs.getString( "gender" ) );
                s.setPhone( rs.getString( "phone" ) );
                s.setAddress( rs.getString( "address" ) );
                s.setStatus( rs.getString( "status" ) );
            }
        }
        catch ( final SQLException e ) {
            // TODO put something here
            e.printStackTrace();
        }
        return s;
    }

    public boolean insert ( final Patient s ) {
        try {
            final PreparedStatement stmt = conn.prepareStatement( "INSERT INTO patient VALUES (?,?,?,?,?,?,?,?);" );
            stmt.setInt( 1, s.getId() );
            stmt.setString( 2, s.getName() );
            stmt.setString( 3, s.getSsn() );
            stmt.setDate( 4, s.getDob() );
            stmt.setString( 5, s.getGender() );
            stmt.setString( 6, s.getPhone() );
            stmt.setString( 7, s.getAddress() );
            stmt.setString( 8, s.getStatus() );

            return ( stmt.executeUpdate() > 0 );
        }
        catch ( final SQLException e ) {
            // TODO put something here
            e.printStackTrace();
        }
        return false;
    }

    public boolean update ( final int id, final Patient s ) {
        try {
            final PreparedStatement stmt = conn
                    .prepareStatement( "UPDATE patient SET id = ?, name = ?, patientSSN = ?, "
                            + "date_of_birth = ?, gender = ?, phone = ?, " + "address = ?, status = ? WHERE id = ?;" );
            stmt.setInt( 1, s.getId() );
            stmt.setString( 2, s.getName() );
            stmt.setString( 3, s.getSsn() );
            stmt.setDate( 4, s.getDob() );
            stmt.setString( 5, s.getGender() );
            stmt.setString( 6, s.getPhone() );
            stmt.setString( 7, s.getAddress() );
            stmt.setString( 8, s.getStatus() );
            stmt.setInt( 9, id );
            return ( stmt.executeUpdate() > 0 );
        }
        catch ( final SQLException e ) {
            // TODO put something here
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteById ( final int id ) {
        try {
            final PreparedStatement stmt = conn.prepareStatement( "DELETE FROM patient WHERE id = ?;" );
            stmt.setInt( 1, id );
            return ( stmt.executeUpdate() > 0 );
        }
        catch ( final SQLException e ) {
            // TODO put something here
            e.printStackTrace();
        }
        return false;
    }
}
