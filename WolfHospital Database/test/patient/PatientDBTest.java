package patient;

import static org.junit.Assert.fail;

import java.sql.Connection;

import org.junit.Test;

import controller.Controller;

public class PatientDBTest {
    private final String      URL      = Controller.URL;
    private final String      DRIVER   = Controller.DRIVER;
    private final String      USERNAME = Controller.USERNAME;
    private final String      PASSWORD = Controller.PASSWORD;

    private static Connection conn;

    @Test
    public void testGetById () {
        fail( "Not yet implemented" );
    }

    @Test
    public void testInsert () {
        fail( "Not yet implemented" );
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
