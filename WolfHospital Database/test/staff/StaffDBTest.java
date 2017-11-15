package staff;

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

public class StaffDBTest {

    private final String      URL      = Controller.URL;
    private final String      DRIVER   = Controller.DRIVER;
    private final String      USERNAME = Controller.USERNAME;
    private final String      PASSWORD = Controller.PASSWORD;

    private static Connection conn;

    private StaffDB           sdb;

    @Before
    public void setUp () throws Exception {
        try {
            Class.forName( DRIVER );
            conn = DriverManager.getConnection( URL, USERNAME, PASSWORD );
            sdb = new StaffDB( conn );
        }
        catch ( final Exception e ) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings ( "deprecation" )
    @Test
    public void testGetById () throws ClassNotFoundException, SQLException {

        Staff s = null;
        s = sdb.getById( 1003 );

        assertNotNull( s );
        assertEquals( "Lucy", s.getName() );
        assertEquals( 40, s.getAge() );
        final Date d1 = new Date( 77, 0, 01 );
        assertEquals( d1, s.getDateOfBirth() );
        assertEquals( "F", s.getGender() );
        assertEquals( "631", s.getPhoneNumber() );
        assertEquals( "42 ABC St , NC 27", s.getAddress() );
        assertEquals( "Intensive Care", s.getDepartment() );
        assertEquals( "Doctor", s.getJobTitle() );
        assertEquals( "Senior Surgeon", s.getProfessionalTitle() );
    }

    @SuppressWarnings ( "deprecation" )
    @Test
    public void testInsert () throws ClassNotFoundException, SQLException {

        final Date d1 = new Date( 85, 05, 10 );
        final Random ran = new Random();
        final int x = ran.nextInt( 9999 );
        final Staff s = new Staff( x, "Sarah", d1, "F", "530", "99 ABC St , NC 27", "Emergency", "Surgeon",
                "Senior Surgeon" );

        assertTrue( sdb.insert( s ) );
        final Staff s2 = sdb.getById( x );
        assertEquals( "Sarah", s2.getName() );
    }

    @SuppressWarnings ( "deprecation" )
    @Test
    public void testUpdate () throws ClassNotFoundException, SQLException {

        final Date d1 = new Date( 85, 05, 10 );
        final Random ran = new Random();
        final int x = ran.nextInt( 9999 );
        final Staff s = new Staff( x, "Sarah", d1, "F", "530", "99 ABC St , NC 27", "Emergency", "Surgeon",
                "Senior Surgeon" );

        assertTrue( sdb.insert( s ) );

        final Staff s2 = new Staff( x, "whoever", d1, "F", "530", "99 ABC St , NC 27", "Emergency", "Surgeon",
                "Senior Surgeon" );

        assertTrue( sdb.update( x, s2 ) );
        final Staff s3 = sdb.getById( x );
        assertEquals( "whoever", s3.getName() );
    }

    @SuppressWarnings ( "deprecation" )
    @Test
    public void testDeleteById () throws ClassNotFoundException, SQLException {

        final Date d1 = new Date( 85, 05, 10 );
        final Random ran = new Random();
        final int x = ran.nextInt( 9999 );
        final Staff s = new Staff( x, "Sarah", d1, "F", "530", "99 ABC St , NC 27", "Emergency", "Surgeon",
                "Senior Surgeon" );

        assertTrue( sdb.insert( s ) );
        assertTrue( sdb.deleteById( x ) );
        assertNull( sdb.getById( x ) );

    }

}
