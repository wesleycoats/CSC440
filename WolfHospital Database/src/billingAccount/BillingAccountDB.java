package billingAccount;

import ward.WardDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BillingAccountDB {

    private final Connection conn;

    public BillingAccountDB ( final Connection conn ) {
        this.conn = conn;
    }

    public BillingAccount getById ( final int id ) {
        BillingAccount b = null;
        try {
            final PreparedStatement stmt = conn.prepareStatement( "SELECT * FROM billing_account WHERE id = ?;" );
            stmt.setInt( 1, id );
            final ResultSet rs = stmt.executeQuery();
            if ( rs.next() ) {
                b = new BillingAccount();
                b.setId( id );
                b.setPatientId( rs.getInt( "patient_id" ) );
                b.setCheckinId( rs.getInt( "check_in_id" ) );
                b.setDate( rs.getDate( "visit_date" ) );
                b.setPayerSsn( rs.getString( "payerSSN" ) );
                b.setPmtType( rs.getString( "payment_type" ) );
                b.setAddress( rs.getString( "billing_address" ) );

            }
        }
        catch ( final SQLException e ) {
            // TODO put something here
            e.printStackTrace();
        }
        return b;
    }

    /**
     * Returns the billing account with all the fees included.
     *
     * @param id patient id
     * @return billing account
     */
    public ArrayList<BillingAccount> getBillingAccount (final int id ) {
        ArrayList<BillingAccount> bas = null;
        BillingAccount b = null;
        String str = "SELECT * FROM billing_account INNER JOIN medical_record "
                + "WHERE YEAR(start_date) = YEAR(visit_date) AND "
                + "MONTH(start_date) = MONTH(visit_date) AND DAY(start_date) = DAY(visit_date) "
                + "AND billing_account.patient_id = ?;";
        try {
            final PreparedStatement stmt = conn.prepareStatement( str );
            stmt.setInt( 1, id );
            final ResultSet rs = stmt.executeQuery();
            bas = new ArrayList<>();
            while ( rs.next() ) {
                b = new BillingAccount();
                b.setId( id );
                b.setPatientId( rs.getInt( "patient_id" ) );
                b.setCheckinId( rs.getInt( "check_in_id" ) );
                b.setDate( rs.getDate( "visit_date" ) );
                b.setPayerSsn( rs.getString( "payerSSN" ) );
                b.setPmtType( rs.getString( "payment_type" ) );
                b.setAddress( rs.getString( "billing_address" ) );
                b.setTestFee( rs.getFloat("test_fee") );
                b.setTreatmentFee( rs.getFloat("treatment_fee") );
                b.setConsultingFee( rs.getFloat("consultation_fee") );

                bas.add(b);
            }
        }
        catch ( final SQLException e ) {
            // TODO put something here
            e.printStackTrace();
        }
        return bas;
    }

    public boolean insert ( final BillingAccount b ) {
        try {
            WardDB wdb = new WardDB(conn);
            ResultSet rs = wdb.checkAvailableWardsAndBeds();

            if (!rs.next()) {
                System.out.println("No wards available could not make billing account.");
                return false;
            }

            String str = "INSERT INTO billing_account(id, patient_id, check_in_id, "
                    + "visit_date, payerSSN, payment_type, billing_address) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?) ;";
            final PreparedStatement stmt = conn
                    .prepareStatement( str );
            stmt.setInt(1, b.getId());
            stmt.setInt( 2, b.getPatientId() );
            stmt.setInt( 3, b.getCheckinId() );
            stmt.setDate( 4, b.getDate() );
            stmt.setString( 5, b.getPayerSsn() );
            stmt.setString( 6, b.getPmtType() );
            stmt.setString( 7, b.getAddress() );
            return ( stmt.executeUpdate() > 0 );
        }
        catch ( final SQLException e ) {
            // TODO put something here
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertNoId ( final BillingAccount b ) {
        try {
            WardDB wdb = new WardDB(conn);
            ResultSet rs = wdb.checkAvailableWardsAndBeds();

            if (!rs.next()) {
                System.out.println("No wards available could not make billing account.");
                return false;
            }

            String str = "INSERT INTO billing_account(patient_id, check_in_id, "
                    + "visit_date, payerSSN, payment_type, billing_address) "
                    + "VALUES (?, ?, ?, ?, ?, ?) ;";
            final PreparedStatement stmt = conn
                    .prepareStatement( str );
            stmt.setInt( 1, b.getPatientId() );
            stmt.setInt( 2, b.getCheckinId() );
            stmt.setDate( 3, b.getDate() );
            stmt.setString( 4, b.getPayerSsn() );
            stmt.setString( 5, b.getPmtType() );
            stmt.setString( 6, b.getAddress() );
            return ( stmt.executeUpdate() > 0 );
        }
        catch ( final SQLException e ) {
            // TODO put something here
            e.printStackTrace();
        }
        return false;
    }

    public boolean update ( final int id, final BillingAccount b ) {
        try {
            final PreparedStatement stmt = conn
                    .prepareStatement( "UPDATE billing_account SET patient_id = ?, check_in_id = ?, visit_date = ?, "
                            + "payerSSN = ?, payment_type = ?, billing_address= ? WHERE id = ?;" );
            stmt.setInt( 1, b.getPatientId() );
            stmt.setInt( 2, b.getCheckinId() );
            stmt.setDate( 3, b.getDate() );
            stmt.setString( 4, b.getPayerSsn() );
            stmt.setString( 5, b.getPmtType() );
            stmt.setString( 6, b.getAddress() );
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
            final PreparedStatement stmt = conn.prepareStatement( "DELETE FROM billing_account WHERE id = ?;" );
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
