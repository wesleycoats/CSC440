package ward;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;

public class WardDBTest {

    private final String      URL      = Controller.URL;
    private final String      DRIVER   = Controller.DRIVER;
    private final String      USERNAME = Controller.USERNAME;
    private final String      PASSWORD = Controller.PASSWORD;

    private static Connection conn;

    private WardDB            db;

    @Before
    public void setUp () throws Exception {
        try {
            Class.forName( DRIVER );
            conn = DriverManager.getConnection( URL, USERNAME, PASSWORD );
            db = new WardDB( conn );
        }
        catch ( final Exception e ) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetById () throws ClassNotFoundException, SQLException {

        Ward w = null;
        w = db.getById( 5001 );

        assertNotNull( w );
        assertEquals( 1, w.getCapacity1() );
        assertEquals( 0, w.getCapacity2() );
        assertEquals( 0, w.getCapacity3() );
        assertEquals( 57, w.getCharge(), 0 );
        assertEquals( 1002, w.getNurseId() );
    }

    @Test
    public void testInsert () throws ClassNotFoundException, SQLException {

        final Random ran = new Random();
        final int x = ran.nextInt( 9999 );
        final Ward w = new Ward( x, 0, 0, 1, 18, 1002 );

        assertTrue( db.insert( w ) );
        final Ward s2 = db.getById( x );
        assertEquals( 18, s2.getCharge(), 0 );
    }

    @Test
    public void testUpdate () throws ClassNotFoundException, SQLException {

        final Random ran = new Random();
        final int x = ran.nextInt( 9999 );
        final Ward w = new Ward( x, 0, 0, 1, 18, 1002 );
        assertTrue( db.insert( w ) );

        final Ward w2 = new Ward( x, 1, 1, 0, 37, 1002 );

        assertTrue( db.update( x, w2 ) );
        final Ward w3 = db.getById( x );
        assertEquals( 37, w3.getCharge(), 0 );
    }

    @Test
    public void testDeleteById () throws ClassNotFoundException, SQLException {

        final Random ran = new Random();
        final int x = ran.nextInt( 9999 );
        final Ward w = new Ward( x, 0, 0, 1, 1000, 1002 );
        assertTrue( db.insert( w ) );
        assertTrue( db.deleteById( x ) );
        assertNull( db.getById( x ) );

    }

    @Test
    public void checkAvailableWardsAndBeds () throws Exception {
        final Random ran = new Random();
        final int x = ran.nextInt( 9999 );
        final Ward ward1 = new Ward( x, 0, 1, 0, 1, 1002 );

        final Random ran2 = new Random();
        final int x2 = ran2.nextInt( 9999 );
        final Ward ward2 = new Ward( x2, 0, 0, 1, 2, 1002 );

        final Random ran3 = new Random();
        final int x3 = ran3.nextInt( 9999 );
        final Ward ward3 = new Ward( x3, 0, 1, 0, 3, 1002 );

        final ResultSet rs = db.checkAvailableWardsAndBeds();

        while ( rs.next() ) {
            final int ward_num = rs.getInt( "id" );
            final int total = rs.getInt( "total" );
            final int capacity1 = rs.getInt( "capacity_one" );
            final int capacity2 = rs.getInt( "capacity_two" );
            final int capacity3 = rs.getInt( "capacity_three" );

            if ( ( ward1.getWardNum() == ward_num || ward2.getWardNum() == ward_num
                    || ward3.getWardNum() == ward_num ) ) {
                if ( ( capacity1 == 1 && total < 1 ) || ( capacity2 == 1 && total < 2 )
                        || ( capacity3 == 1 && total < 3 ) ) {
                    assertTrue( true );
                }
                else {
                    fail( "capacity not correct" );
                }

            }
            else {
                assertTrue( false );
            }
        }
    }
}
