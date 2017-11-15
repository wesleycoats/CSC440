package medicalRecord;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Random;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;

public class MedicalRecordDBTest {

    private final String      URL      = Controller.URL;
    private final String      DRIVER   = Controller.DRIVER;
    private final String      USERNAME = Controller.USERNAME;
    private final String      PASSWORD = Controller.PASSWORD;

    private static Connection conn;
    private MedicalRecordDB db;

    @Before
    public void setUp () throws Exception {
        try {
            Class.forName( DRIVER );
            conn = DriverManager.getConnection( URL, USERNAME, PASSWORD );
            db = new MedicalRecordDB( conn );
        }
        catch ( final Exception e ) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings ( "deprecation" )
    @Test
    public void testGetById () throws ClassNotFoundException, SQLException {

        MedicalRecord m = null;
        m = db.getById( 2001 );

        assertNotNull( m );
        assertEquals( 3001, m.getPatientId() );
        final Date d1 = new Date( 117, 9, 5 );
        assertEquals( d1, m.getStartDate() );
        final Date d2 = new Date( 117, 9, 31 );
        assertEquals( d2, m.getEndDate() );
        assertEquals( 1003, m.getDoctorId() );
        assertEquals( "TB blood test", m.getTestType() );
        assertEquals( "positive", m.getTestResult() );
        assertEquals( "antibiotics", m.getPrescription() );
        assertEquals( "Testing for TB", m.getDiagDetails() );
        assertEquals( "TB treatment", m.getTreatment() );
        assertEquals( 50, m.getConsultFee(), 0 );
        assertEquals( 75, m.getTestFee(), 0 );
        assertEquals( 199, m.getTreatmentFee(), 0 );
        assertEquals( 1004, m.getSpecialistId() );
    }

    @SuppressWarnings ( "deprecation" )
    @Test
    public void testInsert () throws ClassNotFoundException, SQLException {

        final Date d1 = new Date( 117, 8, 5 );
        final Date d2 = new Date( 117, 8, 31 );
        final Random ran = new Random();
        final int x = ran.nextInt( 9999 );
        final MedicalRecord m = new MedicalRecord( x, 3001, d1, d2, 1003, "test type", "results", "prescription",
                "details", "treatment", 1, 2, 3, 1004 );

        assertTrue( db.insert( m ) );
        final MedicalRecord s2 = db.getById( x );
        assertEquals( "test type", s2.getTestType() );
    }

    @SuppressWarnings ( "deprecation" )
    @Test
    public void testUpdate () throws ClassNotFoundException, SQLException {

        final Date d1 = new Date( 117, 8, 5 );
        final Date d2 = new Date( 117, 8, 31 );
        final Random ran = new Random();
        final int x = ran.nextInt( 9999 );
        final MedicalRecord m = new MedicalRecord( x, 3001, d1, d2, 1003, "test type", "results", "prescription",
                "details", "treatment", 1, 2, 3, 1004 );
        assertTrue( db.insert( m ) );

        final MedicalRecord m2 = new MedicalRecord( x, 3001, d1, d2, 1004, "test type2", "results2", "prescription2",
                "details2", "treatment2", 7, 8, 9, 1003 );

        assertTrue( db.update( x, m2 ) );
        final MedicalRecord m3 = db.getById( x );
        assertEquals( "test type2", m3.getTestType() );
    }

    @SuppressWarnings ( "deprecation" )
    @Test
    public void testDeleteById () throws ClassNotFoundException, SQLException {

        final Date d1 = new Date( 117, 8, 5 );
        final Date d2 = new Date( 117, 8, 31 );
        final Random ran = new Random();
        final int x = ran.nextInt( 9999 );
        final MedicalRecord m = new MedicalRecord( x, 3001, d1, d2, 1003, "test type", "results", "prescription",
                "details", "treatment", 1, 2, 3, 1004 );
        assertTrue( db.insert( m ) );
        assertTrue( db.deleteById( x ) );
        assertNull( db.getById( x ) );

    }

    @Test
    @SuppressWarnings("deprecation")
    public void getPatientHistory() throws Exception {
        final Date d1 = new Date( 117, 8, 05 );
        final Date d2 = new Date( 117, 11, 16 );

        ArrayList<MedicalRecord> medrecs = db.getPatientHistory(3001, d1, d2);

        for (MedicalRecord rec: medrecs) {
            assertTrue(rec.getPatientId() == 3001);
            assertTrue(rec.getStartDate().compareTo(d1) > 0);
            assertTrue(rec.getEndDate().compareTo(d2) < 0);
        }
    }
}
