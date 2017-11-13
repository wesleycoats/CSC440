package ward;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WardDB2 {
    private final Connection conn;

    public WardDB2 ( final Connection conn ) {
        this.conn = conn;
    }

    public Ward2 getById ( final int id ) {
        final Ward2 s = new Ward2();
        try {
            final PreparedStatement stmt = conn.prepareStatement( "SELECT * FROM ward WHERE id = ?;" );
            stmt.setInt( 1, id );
            final ResultSet rs = stmt.executeQuery();
            if ( rs.next() ) {
                s.setId( id );
                s.setWardNum( rs.getInt( "ward_number" ) );
                s.setCapacity( rs.getInt( "capacity" ) );
                s.setCharge( rs.getFloat( "charges_per_day" ) );
                s.setOpenBeds( rs.getInt( "open_beds" ) );
                s.setNurseId( rs.getInt( "nurse_id" ) );
            }
        }
        catch ( final SQLException e ) {
            // TODO put something here
            e.printStackTrace();
        }
        return s;
    }

    public boolean insert ( final Ward2 s ) {
        try {
            final PreparedStatement stmt = conn.prepareStatement( "INSERT INTO ward VALUES (?,?,?,?,?,?);" );
            stmt.setInt( 1, s.getId() );
            stmt.setInt( 2, s.getWardNum() );
            stmt.setInt( 3, s.getCapacity() );
            stmt.setFloat( 4, s.getCharge() );
            stmt.setInt( 5, s.getOpenBeds() );
            stmt.setInt( 6, s.getNurseId() );

            return ( stmt.executeUpdate() > 0 );
        }
        catch ( final SQLException e ) {
            // TODO put something here
            e.printStackTrace();
        }
        return false;
    }

    public boolean update ( final Ward2 s ) {
        try {
            final PreparedStatement stmt = conn.prepareStatement( "UPDATE ward SET ward_number = ?, "
                    + "capcity = ?, charges_per_day = ?, open_beds = ?, " + "nurse_id = ? WHERE id = ?;" );
            stmt.setInt( 1, s.getWardNum() );
            stmt.setInt( 2, s.getCapacity() );
            stmt.setFloat( 3, s.getCharge() );
            stmt.setInt( 4, s.getOpenBeds() );
            stmt.setInt( 5, s.getNurseId() );
            stmt.setInt( 6, s.getWardNum() );
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
