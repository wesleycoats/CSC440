package checkIn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Random;

import java.sql.ResultSet;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;

public class CheckInDBTest {

    private final String      URL      = Controller.URL;
    private final String      DRIVER   = Controller.DRIVER;
    private final String      USERNAME = Controller.USERNAME;
    private final String      PASSWORD = Controller.PASSWORD;

    private static Connection conn;
    private CheckInDB         db;

    @Before
    public void setUp () throws Exception {
        try {
            Class.forName( DRIVER );
            conn = DriverManager.getConnection( URL, USERNAME, PASSWORD );
            db = new CheckInDB( conn );
        }
        catch ( final Exception e ) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings ( "deprecation" )
    @Test
    public void testGetById () throws ClassNotFoundException, SQLException {

        CheckIn c = null;
        c = db.getById( 1001 );

        assertNotNull( c );
        assertEquals( 3001, c.getPatientId() );
        final Date d1 = new Date( 117, 9, 05 );
        assertEquals( d1, c.getStartDate() );
        assertNull( c.getEndDate() );
        assertEquals( 5001, c.getWardId() );
        assertEquals( 1, c.getBedNum() );
        assertEquals( 20, c.getFee(), 0 );
    }

    @SuppressWarnings ( "deprecation" )
    @Test
    public void testInsert () throws ClassNotFoundException, SQLException {

        final Date d1 = new Date( 115, 05, 10 );
        final Random ran = new Random();
        final int x = ran.nextInt( 9999 );
        final CheckIn c = new CheckIn( x, 3001, d1, null, 5001, 3, 15 );

        assertTrue( db.insert( c ) );
        final CheckIn c2 = db.getById( x );
        assertEquals( 15, c2.getFee(), 0 );
    }

    @SuppressWarnings ( "deprecation" )
    @Test
    public void testUpdate () throws ClassNotFoundException, SQLException {

        final Date d1 = new Date( 115, 05, 10 );
        final Random ran = new Random();
        final int x = ran.nextInt( 9999 );
        final CheckIn c = new CheckIn( x, 3001, d1, null, 5001, 3, 5 );
        assertTrue( db.insert( c ) );

        final CheckIn c2 = new CheckIn( x, 3001, d1, null, 5001, 2, 45 );
        assertTrue( db.update( x, c2 ) );
        final CheckIn c3 = db.getById( x );
        assertEquals( 45, c3.getFee(), 0 );
    }

    @SuppressWarnings ( "deprecation" )
    @Test
    public void testDeleteById () throws ClassNotFoundException, SQLException {

        final Date d1 = new Date( 85, 05, 10 );
        final Random ran = new Random();
        final int x = ran.nextInt( 9999 );
        final CheckIn c = new CheckIn( x, 3001, d1, null, 5001, 3, 99 );

        assertTrue( db.insert( c ) );
        assertTrue( db.deleteById( x ) );
        assertNull( db.getById( x ) );

    }

    @Test
    public void getPatientsPerMonth() throws Exception {
        ResultSet rs = db.getPatientsPerMonth();

        while (rs.next()) {
            String year = rs.getString("y");
            float ppm = rs.getFloat("ppm");

            if (year.equals("2017")) {
                assertTrue(ppm == (float) 16.67);
            } else if (year.equals("2018")) {
                assertTrue(ppm == (float) 8.33);
            } else {
                assertTrue(false);
            }
        }
    }

}
