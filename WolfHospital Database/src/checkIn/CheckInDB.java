package checkIn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckInDB {
    private final Connection conn;

    public CheckInDB ( final Connection conn ) {
        this.conn = conn;
    }

    public CheckIn getById ( final int id ) {
        CheckIn s = null;
        try {
            final PreparedStatement stmt = conn.prepareStatement( "SELECT * FROM check_in WHERE id = ?;" );
            stmt.setInt( 1, id );
            final ResultSet rs = stmt.executeQuery();
            if ( rs.next() ) {
                s = new CheckIn();
                s.setId( id );
                s.setPatientId( rs.getInt( "patient_id" ) );
                s.setStartDate( rs.getDate( "start_date" ) );
                s.setEndDate( rs.getDate( "end_date" ) );
                s.setWardId( rs.getInt( "ward_id" ) );
                s.setBedNum( rs.getInt( "bed_number" ) );
                s.setFee( rs.getFloat( "registration_fee" ) );
            }
        }
        catch ( final SQLException e ) {
            // TODO put something here
            e.printStackTrace();
        }
        return s;
    }

    public boolean insert ( final CheckIn s ) {
        try {
            final PreparedStatement stmt = conn.prepareStatement( "INSERT INTO check_in VALUES (?,?,?,?,?,?,?);" );
            stmt.setInt( 1, s.getId() );
            stmt.setInt( 2, s.getPatientId() );
            stmt.setDate( 3, s.getStartDate() );
            stmt.setDate( 4, s.getEndDate() );
            stmt.setInt( 5, s.getWardId() );
            stmt.setInt( 6, s.getBedNum() );
            stmt.setFloat( 7, s.getFee() );
            return ( stmt.executeUpdate() > 0 );
        }
        catch ( final SQLException e ) {
            // TODO put something here
            e.printStackTrace();
        }
        return false;
    }

    public boolean update ( final int id, final CheckIn s ) {
        try {
            final PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE check_in SET patient_id = ?," + " start_date = ?, end_date = ?, ward_id = ?, "
                            + "bed_number = ?, registration_fee = ?, WHERE id = ?;" );
            stmt.setInt( 1, s.getPatientId() );
            stmt.setDate( 2, s.getStartDate() );
            stmt.setDate( 3, s.getEndDate() );
            stmt.setInt( 4, s.getWardId() );
            stmt.setInt( 5, s.getBedNum() );
            stmt.setFloat( 6, s.getFee() );
            stmt.setInt( 7, id );
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
            final PreparedStatement stmt = conn.prepareStatement( "DELETE FROM check_in WHERE id = ?;" );
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
