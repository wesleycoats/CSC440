package patient;

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

public class PatientDBTest {

    private final String      URL      = Controller.URL;
    private final String      DRIVER   = Controller.DRIVER;
    private final String      USERNAME = Controller.USERNAME;
    private final String      PASSWORD = Controller.PASSWORD;

    private static Connection conn;

    private PatientDB         db;

    @Before
    public void setUp () throws Exception {
        try {
            Class.forName( DRIVER );
            conn = DriverManager.getConnection( URL, USERNAME, PASSWORD );
            db = new PatientDB( conn );
        }
        catch ( final Exception e ) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings ( "deprecation" )
    @Test
    public void testGetById () throws ClassNotFoundException, SQLException {

        Patient p = null;
        p = db.getById( 3001 );

        assertNotNull( p );
        assertEquals( "John", p.getName() );
        assertEquals( 31, p.getAge() );
        assertNull( p.getSsn() );
        final Date d1 = new Date( 86, 9, 22 );
        assertEquals( d1, p.getDob() );
        assertEquals( "M", p.getGender() );
        assertEquals( "513", p.getPhone() );
        assertEquals( "81 ABC St , NC 27", p.getAddress() );
        assertEquals( "Treatment complete", p.getStatus() );
    }

    @SuppressWarnings ( "deprecation" )
    @Test
    public void testInsert () throws ClassNotFoundException, SQLException {

        final Date d1 = new Date( 85, 05, 10 );
        final Random ran = new Random();
        final int x = ran.nextInt( 9999 );
        final Patient p = new Patient( x, "Patient", null, d1, "F", "530", "99 ABC St , NC 27", "Mid treatment" );

        assertTrue( db.insert( p ) );
        final Patient s2 = db.getById( x );
        assertEquals( "Patient", s2.getName() );
    }

    @SuppressWarnings ( "deprecation" )
    @Test
    public void testUpdate () throws ClassNotFoundException, SQLException {

        final Date d1 = new Date( 85, 05, 10 );
        final Random ran = new Random();
        final int x = ran.nextInt( 9999 );
        final Patient p = new Patient( x, "testpatient", null, d1, "F", "530", "99 ABC St , NC 27", "Mid treatment" );
        assertTrue( db.insert( p ) );

        final Patient p2 = new Patient( x, "whoever", "123-123-1234", d1, "M", "987", "00 ABC St , NC 27",
                "Laying in bed" );

        assertTrue( db.update( x, p2 ) );
        final Patient p3 = db.getById( x );
        assertEquals( "whoever", p3.getName() );
    }

    @SuppressWarnings ( "deprecation" )
    @Test
    public void testDeleteById () throws ClassNotFoundException, SQLException {

        final Date d1 = new Date( 85, 05, 10 );
        final Random ran = new Random();
        final int x = ran.nextInt( 9999 );
        final Patient p = new Patient( x, "delete", null, d1, "F", "530", "99 ABC St , NC 27", "Lost" );

        assertTrue( db.insert( p ) );
        assertTrue( db.deleteById( x ) );
        assertNull( db.getById( x ) );

    }

}
