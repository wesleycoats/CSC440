package billingAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BillingAccountDB {

    private final Connection conn;

    public BillingAccountDB ( final Connection conn ) {
        this.conn = conn;
    }

    public BillingAccount getById ( final int id ) {
        final BillingAccount b = new BillingAccount();
        try {
            final PreparedStatement stmt = conn.prepareStatement( "SELECT * FROM billing_account WHERE id = ?;" );
            stmt.setInt( 1, id );
            final ResultSet rs = stmt.executeQuery();
            if ( rs.next() ) {
                b.setId( id );
                b.setCheckinId( rs.getInt( "check_in_id" ) );
                b.setDate( rs.getDate( "visit_date" ) );
                b.setPayerSsn( rs.getString( "payerSSN" ) );
                b.setPmtType( rs.getString( "payment_type" ) );
                b.setAddress( rs.getString( "address" ) );

            }
        }
        catch ( final SQLException e ) {
            // TODO put something here
            e.printStackTrace();
        }
        return b;
    }

    public boolean insert ( final BillingAccount b ) {
        try {
            final PreparedStatement stmt = conn.prepareStatement( "INSERT INTO billing_account VALUES (?,?,?,?,?,?);" );
            stmt.setInt( 1, b.getId() );
            stmt.setInt( 2, b.getCheckinId() );
            stmt.setDate( 3, b.getDate() );
            stmt.setString( 4, b.getPayerSsn() );
            stmt.setString( 5, b.getPmtType() );
            stmt.setString( 6, b.getAddress() );
            System.out.println( stmt );
            return ( stmt.executeUpdate() > 0 );
        }
        catch ( final SQLException e ) {
            // TODO put something here
            e.printStackTrace();
        }
        return false;
    }

    public boolean update ( final BillingAccount b ) {
        try {
            final PreparedStatement stmt = conn
                    .prepareStatement( "UPDATE billing_account SET check_in_id = ?, visit_date = ?, "
                            + "payerSSN = ?, payment_type = ?, billing_address= ? WHERE id = ?;" );
            stmt.setInt( 1, b.getCheckinId() );
            stmt.setDate( 2, b.getDate() );
            stmt.setString( 3, b.getPayerSsn() );
            stmt.setString( 4, b.getPmtType() );
            stmt.setString( 5, b.getAddress() );
            stmt.setInt( 9, b.getId() );
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
}
