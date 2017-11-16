package billingAccount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Random;

import controller.DatabaseBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.Controller;

import javax.xml.crypto.Data;

public class BillingAccountDBTest {

    private final String      URL      = Controller.URL;
    private final String      DRIVER   = Controller.DRIVER;
    private final String      USERNAME = Controller.USERNAME;
    private final String      PASSWORD = Controller.PASSWORD;

    private static Connection conn;

    private BillingAccountDB  db;

    @Before
    public void setUp () throws Exception {
        try {
            Class.forName( DRIVER );
            conn = DriverManager.getConnection( URL, USERNAME, PASSWORD );
            db = new BillingAccountDB( conn );
        }
        catch ( final Exception e ) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetById () throws ClassNotFoundException, SQLException {

        BillingAccount b = null;
        b = db.getById( 8001 );

        assertNotNull( b );
    }

    @SuppressWarnings ( "deprecation" )
    @Test
    public void getBillingAccount() throws Exception {
        assertNotNull(db.getBillingAccount( 8001 ));
    }

    @SuppressWarnings ( "deprecation" )
    @Test
    public void testInsert () throws ClassNotFoundException, SQLException {

        final Date d1 = new Date( 115, 05, 10 );
        final Random ran = new Random();
        final int x = ran.nextInt( 9999 );
        final BillingAccount b = new BillingAccount( x, 3001, 1001, d1, "123-123-6543", "card", "99 ABC St , NC 27" );

        assertTrue( db.insert( b ) );
        final BillingAccount s2 = db.getById( x );
        assertEquals( "card", s2.getPmtType() );
        db.deleteById(x);
    }

    @SuppressWarnings ( "deprecation" )
    @Test
    public void insertNoId() throws Exception {
        final Date d1 = new Date( 115, 05, 10 );
        final BillingAccount b = new BillingAccount(1001, 3001, d1, "123-123-6543", "card", "99 ABCDE St , NC 27" );

        assertTrue( db.insertNoId( b ) );
        final BillingAccount s2 = db.getById( 8003 );
        assertEquals( "card", s2.getPmtType() );
        db.deleteById(8003);
    }

    @SuppressWarnings ( "deprecation" )
    @Test
    public void testUpdate () throws ClassNotFoundException, SQLException {

        final Date d1 = new Date( 1155, 05, 10 );
        final Random ran = new Random();
        final int x = ran.nextInt( 9999 );
        final BillingAccount b = new BillingAccount( x, 3001, 1001, d1, "000-123-6543", "card", "99 ABC St , NC 27" );
        assertTrue( db.insert( b ) );

        final BillingAccount b2 = new BillingAccount( x, 3001, 1001, d1, "999-123-6543", "something",
                "xx ABC St , NC 27" );

        assertTrue( db.update( x, b2 ) );
        final BillingAccount b3 = db.getById( x );
        assertEquals( "something", b3.getPmtType() );
        db.deleteById(x);
    }

    @SuppressWarnings ( "deprecation" )
    @Test
    public void testDeleteById () throws ClassNotFoundException, SQLException {

        final Date d1 = new Date( 85, 05, 10 );
        final Random ran = new Random();
        final int x = ran.nextInt( 9999 );
        final BillingAccount b = new BillingAccount( x, 3001, 1001, d1, "999-123-6543", "something",
                "xx ABC St , NC 27" );

        assertTrue( db.insert( b ) );
        assertTrue( db.deleteById( x ) );
        assertNull( db.getById( x ) );
        db.deleteById(x);
    }

}
