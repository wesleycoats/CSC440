package staff;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.Date;

import org.junit.Test;

import controller.Controller;

public class StaffDBTest {

    private final String      URL      = Controller.URL;
    private final String      DRIVER   = Controller.DRIVER;
    private final String      USERNAME = Controller.USERNAME;
    private final String      PASSWORD = Controller.PASSWORD;

    private static Connection conn;

    @Test
    public void testGetById () {

    }

    @Test
    public void testInsert () {
        final Date d1 = new Date( 1985, 06, 10 );
        final Staff s = new Staff( 1001, "Sarah", d1, "F", "530", "99 ABC St , NC 27", "Emergency", "Surgeon",
                "Senior Surgeon" );
        final StaffDB sdb = new StaffDB( conn );
    }

    @Test
    public void testUpdate () {
        fail( "Not yet implemented" );
    }

    @Test
    public void testDeleteById () {
        fail( "Not yet implemented" );
    }

}
