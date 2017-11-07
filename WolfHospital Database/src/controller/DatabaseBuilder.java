package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class to handle resetting and creating the WolfHospital database, as well as
 * generating sample data to use in database demonstration. Executing a query is
 * done in a private helper method since a lot of the set up and tear down was
 * the same. This is not handled as a transaction, but instead as a regular
 * single query. Other methods are public and static so that they can be called
 * elsewhere in the program since they will likely be helpful for resetting the
 * expected state of the database.
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
    private static final String URL    = "jdbc:mysql://localhost:3306";
    /** JDBC driver */
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    /** Database username */
    private static final String USER   = "root";
    /** Database password */
    private static final String PW     = "";

    public static void main ( final String[] args ) {
        dropDB();
        createDB();
        createTables();
        generateData();
        System.exit( 0 );
    }

    /** Deletes the database and all contents */
    public static void dropDB () {
        executeQuery( "DROP DATABASE wolfhospital" );
    }

    /** Creates the database */
    public static void createDB () {
        executeQuery( "CREATE DATABASE wolfhospital" );
    }

    /** Creates all tables to be used in database */
    public static void createTables () {
        // TODO: add other tables
        
        executeQuery(
                "CREATE TABLE staff(" + 
                "id INT PRIMARY KEY AUTO_INCREMENT," + 
                "name NVARCHAR2(128) NOT NULL," + 
                "date_of_birth DATE NOT NULL," + 
                "gender NVARCHAR2(10) NOT NULL," + 
                "phone NVARCHAR2(20) NOT NULL," + 
                "address NVARCHAR2(128) NOT NULL," + 
                "department NVARCHAR2(128) NOT NULL," + 
                "title NVARCHAR2(128) NOT NULL," + 
                "professional_title NVARCHAR2(128) NOT NULL);"
                ,
                "CREATE TABLE patient(" + 
                "id INT PRIMARY KEY AUTO_INCREMENT," + 
                "name NVARCHAR2(128) NOT NULL," + 
                "patientSSN NVARCHAR2(11)," + 
                "date_of_birth DATE NOT NULL," + 
                "gender NVARCHAR2(10) NOT NULL," + 
                "status NVARCHAR2(30) NOT NULL);"
        );
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
