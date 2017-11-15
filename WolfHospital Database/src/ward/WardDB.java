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
                    + "capcity_two = ?, capacity_three = ?, charges_per_day = ?, nurse_id = ? WHERE id = ?;" );
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
}
