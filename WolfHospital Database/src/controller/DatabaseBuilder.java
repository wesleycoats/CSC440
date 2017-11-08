package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
    private static String       URL    = "jdbc:mysql://localhost:3306/";
    /** JDBC driver */
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    /** Database username */
    private static final String USER   = "root";
    /** Database password */
    private static final String PW     = "";

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
        System.exit( 0 );
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
        URL = "jdbc:mysql://localhost:3306/wolfhospital";
    }

    /** Creates all tables to be used in database */
    public static void createTables () {
        final String staff = "CREATE TABLE staff(id INT PRIMARY KEY AUTO_INCREMENT, name NVARCHAR(128) NOT NULL, "
                + "date_of_birth DATE NOT NULL, gender NVARCHAR(10) NOT NULL, phone NVARCHAR(20) NOT NULL, "
                + "address NVARCHAR(128) NOT NULL, department NVARCHAR(128) NOT NULL, job_title NVARCHAR(128) NOT NULL,"
                + "professional_title NVARCHAR(128) NOT NULL);";

        final String patient = "CREATE TABLE patient(id INT PRIMARY KEY AUTO_INCREMENT, name NVARCHAR(128) NOT NULL,"
                + "patientSSN NVARCHAR(11)," + "date_of_birth DATE NOT NULL, gender NVARCHAR(10) NOT NULL,"
                + "phone NVARCHAR(20) NOT NULL, address NVARCHAR(128) NOT NULL, status NVARCHAR(30) NOT NULL);";

        final String ward = "CREATE TABLE ward(ward_number INT PRIMARY KEY, capacity INT NOT NULL, charges_per_day FLOAT NOT NULL,"
                + "open_beds INT NOT NULL, nurse_id INT, CONSTRAINT ward_nurse_fk FOREIGN KEY(nurse_id) REFERENCES staff(id));";

        final String checkin = "CREATE TABLE check_in(id INT AUTO_INCREMENT, patient_id INT, start_date DATETIME,"
                + "CONSTRAINT check_in_pk PRIMARY KEY(id, patient_id, start_date),"
                + "CONSTRAINT check_in_patient_id_fk FOREIGN KEY(patient_id) REFERENCES patient(id) ON DELETE CASCADE,"
                + "end_date DATETIME, ward_number INT,"
                + "CONSTRAINT check_in_ward_num_fk FOREIGN KEY(ward_number) REFERENCES ward(ward_number),"
                + "bed_number INT NOT NULL, registration_fee FLOAT NOT NULL);";

        final String medicalRecord = "CREATE TABLE medical_record(id INT PRIMARY KEY AUTO_INCREMENT, patient_id INT,"
                + "CONSTRAINT med_rec_patient_id_fk FOREIGN KEY(patient_id) REFERENCES patient(id) ON DELETE CASCADE,"
                + "start_date DATETIME, end_date DATETIME, doctor_id INT, CONSTRAINT med_rec_doctor_id_fk FOREIGN KEY(doctor_id) REFERENCES staff(id),"
                + "test_type NVARCHAR(128) NOT NULL, test_results NVARCHAR(1024) NOT NULL,"
                + "prescription NVARCHAR(128), diagnosis_details NVARCHAR(1024), treatment NVARCHAR(128),"
                + "consultation_fee FLOAT NOT NULL, test_fee FLOAT, treatment_fee FLOAT, specialist_id INT,"
                + "CONSTRAINT staff_id_fk FOREIGN KEY(specialist_id) REFERENCES staff(id) ON DELETE CASCADE);";

        final String billingAccount = "CREATE TABLE billing_account(patient_id INT, check_in_id INT,"
                + "CONSTRAINT bill_acc_pk PRIMARY KEY(patient_id, check_in_id),"
                + "CONSTRAINT bill_acc_patient_id_fk FOREIGN KEY(patient_id) REFERENCES patient(id) ON DELETE CASCADE,"
                + "CONSTRAINT bill_acc_visit_date_fk FOREIGN KEY(check_in_id) REFERENCES check_in(id),"
                + "visit_date DATETIME, payerSSN NVARCHAR(11) NOT NULL, payment_type NVARCHAR(128) NOT NULL,"
                + "billing_address NVARCHAR(128) NOT NULL);";

        final String billingRecordCost = "CREATE TABLE billing_record_cost(type NVARCHAR(128) PRIMARY KEY, cost FLOAT NOT NULL);";

        final String billingRecord = "CREATE TABLE billing_record(patient_id INT, check_in_id INT, type NVARCHAR(128),"
                + "CONSTRAINT bill_rec_pk PRIMARY KEY(patient_id, check_in_id, type),"
                + "CONSTRAINT bill_rec_patient_id_fk FOREIGN KEY(patient_id) REFERENCES billing_account(patient_id) ON DELETE CASCADE,"
                + "CONSTRAINT bill_rec_visit_date_fk FOREIGN KEY(check_in_id) REFERENCES billing_account(check_in_id) ON DELETE CASCADE,"
                + "CONSTRAINT bill_rec_type_fk FOREIGN KEY(type) REFERENCES billing_record_cost(type));";

        executeQuery( staff, patient, ward, checkin, medicalRecord, billingAccount, billingRecordCost, billingRecord );
    }

    /** Populates tables with sample data */
    public static void generateData () {
        // TODO: write queries to insert data

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
