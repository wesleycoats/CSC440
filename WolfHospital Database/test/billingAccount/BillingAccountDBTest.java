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

import org.junit.Before;
import org.junit.Test;

import controller.Controller;

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
    public void testInsert () throws ClassNotFoundException, SQLException {

        final Date d1 = new Date( 115, 05, 10 );
        final Random ran = new Random();
        final int x = ran.nextInt( 9999 );
        final BillingAccount b = new BillingAccount( x, 3001, 1001, d1, "123-123-6543", "card", "99 ABC St , NC 27" );

        assertTrue( db.insert( b ) );
        final BillingAccount s2 = db.getById( x );
        assertEquals( "card", s2.getPmtType() );
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

    }

}
