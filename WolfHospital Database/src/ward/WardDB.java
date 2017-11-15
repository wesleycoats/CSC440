package ward;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WardDB {
    private final Connection conn;

    public WardDB ( final Connection conn ) {
        this.conn = conn;
    }

    public Ward getById ( final int id ) {
        Ward s = null;
        try {
            final PreparedStatement stmt = conn.prepareStatement( "SELECT * FROM ward WHERE id = ?;" );
            stmt.setInt( 1, id );
            final ResultSet rs = stmt.executeQuery();
            if ( rs.next() ) {
                s = new Ward();
                s.setWardNum( id );
                s.setCapacity1( rs.getInt( "capacity_one" ) );
                s.setCapacity2( rs.getInt( "capacity_two" ) );
                s.setCapacity3( rs.getInt( "capacity_three" ) );
                s.setCharge( rs.getFloat( "charges_per_day" ) );
                s.setNurseId( rs.getInt( "nurse_id" ) );
            }
        }
        catch ( final SQLException e ) {
            // TODO put something here
            e.printStackTrace();
        }
        return s;
    }

    public boolean insert ( final Ward s ) {
        try {
            final PreparedStatement stmt = conn.prepareStatement( "INSERT INTO ward VALUES (?,?,?,?,?,?);" );
            stmt.setInt( 1, s.getWardNum() );
            stmt.setInt( 2, s.getCapacity1() );
            stmt.setInt( 3, s.getCapacity2() );
            stmt.setInt( 4, s.getCapacity3() );
            stmt.setFloat( 5, s.getCharge() );
            stmt.setInt( 6, s.getNurseId() );

            return ( stmt.executeUpdate() > 0 );
        }
        catch ( final SQLException e ) {
            // TODO put something here
            e.printStackTrace();
        }
        return false;
    }

    public boolean update ( final int id, final Ward s ) {
        try {
            final PreparedStatement stmt = conn.prepareStatement( "UPDATE ward SET capacity_one = ?, "
                    + "capacity_two = ?, capacity_three = ?, charges_per_day = ?, nurse_id = ? WHERE id = ?;" );
            stmt.setInt( 1, s.getCapacity1() );
            stmt.setInt( 2, s.getCapacity2() );
            stmt.setInt( 3, s.getCapacity3() );
            stmt.setFloat( 4, s.getCharge() );
            stmt.setInt( 5, s.getNurseId() );
            stmt.setInt( 6, id );
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
            final PreparedStatement stmt = conn.prepareStatement( "DELETE FROM ward WHERE id = ?;" );
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
     * This function queries the database for the wards with available beds
     *
     * @return The result set containing the ward_id and number of available
     *         beds.
     */
    public ResultSet checkAvailableWardsAndBeds () {
        try {
            final String str = "SELECT *, (SELECT COUNT(ward_id) FROM check_in "
                    + "WHERE ward_id = ward.id && (end_date > CURDATE() || end_date IS NULL)) AS total FROM ward HAVING "
                    + "(capacity_one='1' && total < 1) || " + "(capacity_two='1' &&  total < 2) || "
                    + "(capacity_three='1' && total < 3);";
            return conn.prepareStatement( str ).executeQuery();
        }
        catch ( final SQLException e ) {
            // TODO put something here
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Gets the number of available beds in all wards.
     *
     * @return the result set given by the db.
     */
    public ResultSet getWardUsageStatus() {
        try {
            String str = "SELECT *, (SELECT COUNT(ward_id) FROM check_in "
                    + "WHERE ward_id = ward.id && (end_date > CURDATE() || end_date IS NULL)) AS total FROM ward;";
            return conn.prepareStatement( str ).executeQuery();
        }
        catch ( final SQLException e ) {
            // TODO put something here
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Gets the number of available beds in all wards.
     *
     * @return the result set given by the db.
     */
    public ResultSet getWardUsagePercent() {
        try {
            String str = "SELECT *, (SELECT COUNT(ward_id) FROM check_in "
                    + "WHERE ward_id = ward.id) AS total FROM ward;";
            return conn.prepareStatement( str ).executeQuery();
        }
        catch ( final SQLException e ) {
            // TODO put something here
            e.printStackTrace();
        }

        return null;
    }
}
