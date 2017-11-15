package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import billingAccount.BillingAccount;
import billingAccount.BillingAccountDB;
import checkIn.CheckIn;
import checkIn.CheckInDB;
import medicalRecord.MedicalRecord;
import medicalRecord.MedicalRecordDB;
import patient.Patient;
import patient.PatientDB;
import staff.Staff;
import staff.StaffDB;
import ward.Ward;
import ward.WardDB;

/**
 * Class to handle resetting and creating the WolfHospital database, as well as
 * generating sample data to use in database demonstration. Executing a query is
 * done in a private helper method since a lot of the set up and tear down was
 * the same. This is handled as a transaction (though not with
 * PreparedStatement). Other methods are public and static so that they can be
 * called elsewhere in the program since they will likely be helpful for
 * resetting the expected state of the database.
 *
 * Currently the DatabaseBuilder creates the database locally using the default
 * MySQL port 3306, and assumes user "root" with empty password. To run this on
 * your own machine, please change username and password appropriately, but do
 * not push these changes (or set them back to root and empty password before
 * pushing).
 *
 * @author ajfelion
 *
 */
public class DatabaseBuilder {
    /** Database url */
    private static String       URL    = Controller.BASE_URL;
    /** JDBC driver */
    private static final String DRIVER = Controller.DRIVER;
    /** Database username */
    private static final String USER   = Controller.USERNAME;
    /** Database password */
    private static final String PW     = Controller.PASSWORD;

    public static void main ( final String[] args ) {
        try {
            dropDB();
        }
        catch ( final Exception e ) {
            e.printStackTrace();
        }
        createDB();
        createTables();
        generateData();
    }

    /** Deletes the database and all contents */
    public static void dropDB () throws Exception {
        Class.forName( DRIVER );
        final Connection connection = DriverManager.getConnection( URL, USER, PW );
        final ResultSet resultSet = connection.getMetaData().getCatalogs();
        String dbName = null;
        // iterate each catalog in the ResultSet
        while ( resultSet.next() ) {
            // Get the database name, which is at position 1
            dbName = resultSet.getString( 1 );
        }
        resultSet.close();
        if ( dbName.equals( "wolfhospital" ) ) {
            executeQuery( "DROP DATABASE wolfhospital" );
        }
    }

    /**
     * Creates the database
     *
     * @throws Exception
     */
    public static void createDB () {
        executeQuery( "CREATE DATABASE wolfhospital" );
        URL = Controller.URL;
    }

    /** Creates all tables to be used in database */
    public static void createTables () {
        final String staff = "CREATE TABLE IF NOT EXISTS staff(id INT PRIMARY KEY AUTO_INCREMENT, name NVARCHAR(128) NOT NULL, "
                + "date_of_birth DATE NOT NULL, gender NVARCHAR(10) NOT NULL, phone NVARCHAR(20) NOT NULL, "
                + "address NVARCHAR(128) NOT NULL, department NVARCHAR(128) NOT NULL, job_title NVARCHAR(128) NOT NULL,"
                + "professional_title NVARCHAR(128) NOT NULL);";

        final String patient = "CREATE TABLE IF NOT EXISTS patient(id INT PRIMARY KEY AUTO_INCREMENT, name NVARCHAR(128) NOT NULL,"
                + "patientSSN NVARCHAR(12)," + "date_of_birth DATE NOT NULL, gender NVARCHAR(10) NOT NULL,"
                + "phone NVARCHAR(20) NOT NULL, address NVARCHAR(128) NOT NULL, status NVARCHAR(30) NOT NULL);";

        final String ward = "CREATE TABLE IF NOT EXISTS ward(id INT PRIMARY KEY, capacity_one INT NOT NULL, "
                + "capacity_two INT NOT NULL, capacity_three INT NOT NULL, charges_per_day FLOAT NOT NULL,"
                + "nurse_id INT, CONSTRAINT ward_nurse_fk FOREIGN KEY(nurse_id) REFERENCES staff(id));";

        final String checkin = "CREATE TABLE IF NOT EXISTS check_in(id INT AUTO_INCREMENT, patient_id INT, start_date DATETIME,"
                + "CONSTRAINT check_in_pk PRIMARY KEY(id, patient_id, start_date),"
                + "CONSTRAINT check_in_patient_id_fk FOREIGN KEY(patient_id) REFERENCES patient(id) ON DELETE CASCADE,"
                + "end_date DATETIME, ward_id INT,"
                + "CONSTRAINT check_in_ward_id_fk FOREIGN KEY(ward_id) REFERENCES ward(id),"
                + "bed_number INT NOT NULL, registration_fee FLOAT NOT NULL);";

        final String medicalRecord = "CREATE TABLE IF NOT EXISTS medical_record(id INT PRIMARY KEY AUTO_INCREMENT, patient_id INT,"
                + "CONSTRAINT med_rec_patient_id_fk FOREIGN KEY(patient_id) REFERENCES patient(id) ON DELETE CASCADE,"
                + "start_date DATETIME, end_date DATETIME, doctor_id INT, CONSTRAINT med_rec_doctor_id_fk FOREIGN KEY(doctor_id) REFERENCES staff(id),"
                + "test_type NVARCHAR(128) NOT NULL, test_results NVARCHAR(1024) NOT NULL,"
                + "prescription NVARCHAR(128), diagnosis_details NVARCHAR(1024), treatment NVARCHAR(128),"
                + "consultation_fee FLOAT NOT NULL, test_fee FLOAT, treatment_fee FLOAT, specialist_id INT,"
                + "CONSTRAINT staff_id_fk FOREIGN KEY(specialist_id) REFERENCES staff(id) ON DELETE CASCADE);";

        final String billingAccount = "CREATE TABLE IF NOT EXISTS billing_account(id INT AUTO_INCREMENT, patient_id INT, check_in_id INT,"
                + "CONSTRAINT bill_acc_pk PRIMARY KEY(id, patient_id, check_in_id),"
                + "CONSTRAINT bill_acc_patient_id_fk FOREIGN KEY(patient_id) REFERENCES patient(id) ON DELETE CASCADE,"
                + "CONSTRAINT bill_acc_check_in_id_fk FOREIGN KEY(check_in_id) REFERENCES check_in(id),"
                + "visit_date DATETIME, payerSSN NVARCHAR(12) NOT NULL, payment_type NVARCHAR(128) NOT NULL,"
                + "billing_address NVARCHAR(128) NOT NULL);";

        executeQuery( staff, patient, ward, checkin, medicalRecord, billingAccount );
    }

    /** Populates tables with sample data */
    @SuppressWarnings ( "deprecation" )
    public static void generateData () {
        final Date d1 = new Date( 81, 19, 16 );
        final Staff operator = new Staff( 1001, "Simpson", d1, "F", "919", "21 ABC St , NC 27", "Billing", "Biller",
                "Accounts Supervisor" );

        final Date d2 = new Date( 72, 06, 15 );
        final Staff nurse1 = new Staff( 1002, "David", d2, "M", "123", "22 ABC St , NC 27", "Casuality", "Nurse",
                "Senior Nurse" );

        final Date d3 = new Date( 82, 01, 18 );
        final Staff nurse2 = new Staff( 1005, "Ruth", d3, "F", "456", "23 ABC St , NC 27", "Casuality", "Nurse",
                "Assistant Nurse" );

        final Date d4 = new Date( 77, 00, 01 );
        final Staff doctor1 = new Staff( 1003, "Lucy", d4, "F", "631", "42 ABC St , NC 27", "Intensive Care", "Doctor",
                "Senior Surgeon" );

        final Date d5 = new Date( 76, 02, 27 );
        final Staff doctor2 = new Staff( 1004, "Joseph", d5, "M", "327", "51 ABC St , NC 27", "Pulmonary", "Doctor",
                "Pulmonologist" );

        final Ward ward = new Ward( 5001, 1, 0, 0, 57, 1002 );
        final Ward ward1 = new Ward( 5002, 0, 1, 0, 60, 1002 );
        final Ward ward2 = new Ward( 5003, 0, 0, 1, 63, 1002 );
        final Ward ward3 = new Ward( 5004, 0, 1, 0, 60, 1002 );

        final Date d6 = new Date( 117, 9, 05 );
        final CheckIn checkin = new CheckIn( 1001, 3001, d6, null, 5001, 1, 20 );

        final Date d7 = new Date( 86, 9, 22 );
        final Patient patient1 = new Patient( 3001, "John", null, d7, "M", "513", "81 ABC St , NC 27",
                "Treatment complete" );

        final Date d8 = new Date( 117, 9, 05 );
        final Date d9 = new Date( 117, 9, 31 );
        final MedicalRecord medicalRecord1 = new MedicalRecord( 2001, 3001, d8, d9, 1003, "TB blood test", "positive",
                "antibiotics", "Testing for TB", "TB treatment", 50, 75, 199, 1004 );

        final Date d10 = new Date( 117, 10, 01 );
        final Date d11 = new Date( 117, 10, 16 );
        final MedicalRecord medicalRecord2 = new MedicalRecord( 2002, 3001, d10, d11, 1003, "X-ray chest (TB) Advanced",
                "negative", "continue antibiotics", "Testing for TB", "Not required", 0, 125, 0, 1004 );

        final Date d12 = new Date( 85, 05, 10 );
        final BillingAccount b = new BillingAccount( 8001, 3001, 1001, d12, "123-123-1234", "card",
                "99 ABC St , NC 27" );
        final MedicalRecord medicalRecord4 = new MedicalRecord( 2004, 3002, d12, null, 1003, "X-ray chest (TB) Advanced",
                "negative", "continue antibiotics", "Testing for TB", "Not required", 0, 125, 0, 1004 );
        final Patient patient2 = new Patient( 3002, "Jonnathan", null, d7, "M", "513-564-6969", "81 ABC St , NC 27",
                "Treatment complete" );

        final Date d13 = new Date( 118, 11, 01 );
        final Date d14 = new Date( 118, 11, 16 );
        final MedicalRecord medicalRecord3 = new MedicalRecord( 2003, 3002, d13, d14, 1003, "X-ray chest (TB) Advanced",
                "negative", "continue antibiotics", "Testing for TB", "Not required", 0, 125, 0, 1004 );

        final Date d15 = new Date( 118, 11, 01 );
        final CheckIn checkin2 = new CheckIn( 1002, 3002, d15, null, 5001, 1, 20 );

        final Date d16 = new Date( 117, 10, 01 );
        final CheckIn checkin3 = new CheckIn( 1003, 3001, d16, null, 5001, 1, 20 );

        try {
            Class.forName( DRIVER );
            final Connection conn = DriverManager.getConnection( URL, USER, PW );
            final StaffDB staffDB = new StaffDB( conn );
            final WardDB wardDB = new WardDB( conn );
            final CheckInDB checkinDB = new CheckInDB( conn );
            final PatientDB patientDB = new PatientDB( conn );
            final MedicalRecordDB medDB = new MedicalRecordDB( conn );
            final BillingAccountDB billingDB = new BillingAccountDB( conn );

            staffDB.insert( operator );
            staffDB.insert( nurse1 );
            staffDB.insert( nurse2 );
            staffDB.insert( doctor1 );
            staffDB.insert( doctor2 );
            wardDB.insert( ward );
            wardDB.insert( ward1 );
            wardDB.insert( ward2 );
            wardDB.insert( ward3 );
            patientDB.insert( patient1 );
            patientDB.insert( patient2 );
            checkinDB.insert( checkin );
            checkinDB.insert( checkin2 );
            checkinDB.insert( checkin3 );
            medDB.insert( medicalRecord1 );
            medDB.insert( medicalRecord2 );
            billingDB.insert( b );
            medDB.insert( medicalRecord3 );
            medDB.insert( medicalRecord4 );
        }
        catch ( final Exception e ) {
            try {
                dropDB();
            }
            catch ( final Exception e2 ) {
                e.printStackTrace();
            }
            createDB();
            createTables();
            e.printStackTrace();
        }

    }

    /**
     * Helper method to execute variable number of queries as a single
     * transaction.
     */
    private static void executeQuery ( final String... queries ) {
        Connection conn = null;
        Statement stmt = null;
        try {
            // Register driver
            Class.forName( DRIVER );

            // Open connection
            conn = DriverManager.getConnection( URL, USER, PW );

            // Execute the queries as a transaction
            conn.setAutoCommit( false );
            stmt = conn.createStatement();
            for ( final String query : queries ) {
                stmt.executeUpdate( query );
            }

            // No errors, so commit the queries
            conn.commit();
        }
        catch ( final Exception e ) {
            // If something went wrong, so rollback the queries
            e.printStackTrace();
            try {
                conn.rollback();
            }
            catch ( final SQLException e1 ) {
                e1.printStackTrace();
            }
            System.exit( 1 );
        }
        finally {
            // Close everything
            try {
                if ( stmt != null ) {
                    stmt.close();
                }
                if ( conn != null ) {
                    conn.close();
                }
            }
            catch ( final Exception e ) {
                e.printStackTrace();
                System.exit( 1 );
            }
        }
    }
}
