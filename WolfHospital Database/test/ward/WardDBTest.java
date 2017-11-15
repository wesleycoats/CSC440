package ward;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;

public class WardDBTest {

    private final String      URL      = Controller.URL;
    private final String      DRIVER   = Controller.DRIVER;
    private final String      USERNAME = Controller.USERNAME;
    private final String      PASSWORD = Controller.PASSWORD;

    private WardDB            db;
    private static Connection conn;

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
    public void getById () throws Exception {
        fail( "tbd" );
    }

    @Test
    public void insert () throws Exception {
        fail( "tbd" );
    }

    @Test
    public void update () throws Exception {
        fail( "tbd" );
    }

    @Test
    public void deleteById () throws Exception {
        fail( "tbd" );
    }

    @Test
    public void checkAvailableWardsAndBeds () throws Exception {
        final Ward ward1 = new Ward( 5002, 0, 1, 0, 60, 1002 );
        final Ward ward2 = new Ward( 5003, 0, 0, 1, 63, 1002 );
        final Ward ward3 = new Ward( 5004, 0, 1, 0, 60, 1002 );
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
