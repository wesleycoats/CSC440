package ward;

import org.junit.Before;
import org.junit.Test;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import static org.junit.Assert.*;

public class WardDBTest {

    private static final String URL      = "jdbc:mysql://localhost:3306/wolfhospital";
    private static final String DRIVER   = "com.mysql.jdbc.Driver";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private WardDB db;
    private static Connection conn;

    @Before
    public void setUp() throws Exception {
        try {
            Class.forName( DRIVER );
            conn = DriverManager.getConnection( URL, USERNAME, PASSWORD );
            db = new WardDB(conn);
        }
        catch ( final Exception e ) {
            e.printStackTrace();
        }
    }

    @Test
    public void getById() throws Exception {
        fail("tbd");
    }

    @Test
    public void insert() throws Exception {
        fail("tbd");
    }

    @Test
    public void update() throws Exception {
        fail("tbd");
    }

    @Test
    public void deleteById() throws Exception {
        fail("tbd");
    }

    @Test
    public void checkAvailableWardsAndBeds() throws Exception {
        Ward ward1 = new Ward( 5002, 0, 1, 0, 60, 1002 );
        Ward ward2 = new Ward( 5003, 0, 0, 1, 63, 1002 );
        Ward ward3 = new Ward( 5004, 0, 1, 0, 60, 1002 );
        ResultSet rs = db.checkAvailableWardsAndBeds();

        while (rs.next()) {
            int ward_num = rs.getInt("id");
            int total = rs.getInt("total");
            int capacity1 = rs.getInt( "capacity_one" );
            int capacity2 = rs.getInt( "capacity_two" );
            int capacity3 = rs.getInt( "capacity_three" );

            if ((ward1.getWardNum() == ward_num ||
                    ward2.getWardNum() == ward_num ||
                    ward3.getWardNum() == ward_num)) {
                if ((capacity1 == 1 && total < 1) ||
                        (capacity2 == 1 && total < 2) ||
                        (capacity3 == 1 && total < 3)) {
                    assertTrue(true);
                } else {
                    fail("capacity not correct");
                }

            } else {
                assertTrue(false);
            }
        }
    }
}