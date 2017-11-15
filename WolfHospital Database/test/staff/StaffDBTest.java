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
import ward.WardDB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.util.ArrayList;

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

    @Test
    @SuppressWarnings("deprecation")
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

    @Test
    @SuppressWarnings ( "deprecation" )
    public void getStaffInfoGroupedByDepartment() throws Exception {
        ArrayList<Staff> staff_info = sdb.getStaffInfoGroupedByDepartment(1);
        final Date d1 = new Date( 1981, 10, 16 );
        final Staff operator = new Staff( 1001, "Simpson", d1, "F", "919", "21 ABC St , NC 27", "Billing", "Biller",
                "Accounts Supervisor" );

        final Date d2 = new Date( 1972, 07, 15 );
        final Staff nurse1 = new Staff( 1002, "David", d2, "M", "123", "22 ABC St , NC 27", "Casuality", "Nurse",
                "Senior Nurse" );

        final Date d3 = new Date( 1982, 02, 18 );
        final Staff nurse2 = new Staff( 1005, "Ruth", d3, "F", "456", "23 ABC St , NC 27", "Casuality", "Nurse",
                "Assistant Nurse" );

        final Date d4 = new Date( 1977, 01, 01 );
        final Staff doctor1 = new Staff( 1003, "Lucy", d4, "F", "631", "42 ABC St , NC 27", "Intensive Care", "Doctor",
                "Senior Surgeon" );

        final Date d5 = new Date( 1976, 03, 27 );
        final Staff doctor2 = new Staff( 1004, "Joseph", d5, "M", "327", "51 ABC St , NC 27", "Pulmonary", "Doctor",
                "Pulmonologist" );

        assertTrue(staff_info.contains(operator));
        assertTrue(staff_info.contains(nurse1));
        assertTrue(staff_info.contains(nurse2));
        assertTrue(staff_info.contains(doctor1));
        assertTrue(staff_info.contains(doctor2));
    }

}
