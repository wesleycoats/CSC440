package examplePackage;

// Acknowledgments: This example is a modification of code provided
// by Dimitri Rakitine.
// Further modified by Shrikanth N C for MySql(MariaDB) support
// Relpace all $USER$ with your unity id and $PASSWORD$ with your 9 digit
// student id or updated password (if changed)

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CoffeeExample {

    static final String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/$USER$";

    public static void main ( final String[] args ) {
        try {

            // Load the driver. This creates an instance of the driver
            // and calls the registerDriver method to make MySql Thin
            // driver, available to clients.

            Class.forName( "org.mariadb.jdbc.Driver" );

            final String user = "$USER$";
            final String passwd = "$PASSWORD$";

            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            try {

                // Get a connection from the first driver in the
                // DriverManager list that recognizes the URL jdbcURL

                conn = DriverManager.getConnection( jdbcURL, user, passwd );

                // Create a statement object that will be sending your
                // SQL statements to the DBMS

                stmt = conn.createStatement();

                // stmt.executeUpdate("DROP TABLE COFFEES ");

                // Create the COFFEES table

                stmt.executeUpdate( "CREATE TABLE COFFEES " + "(COF_NAME VARCHAR(32), SUP_ID INTEGER, "
                        + "PRICE FLOAT, SALES INTEGER, TOTAL INTEGER)" );

                // Populate the COFFEES table

                stmt.executeUpdate( "INSERT INTO COFFEES " + "VALUES ('Colombian', 101, 7.99, 0, 0)" );

                stmt.executeUpdate( "INSERT INTO COFFEES " + "VALUES ('French_Roast', 49, 8.99, 0, 0)" );

                stmt.executeUpdate( "INSERT INTO COFFEES " + "VALUES ('Espresso', 150, 9.99, 0, 0)" );

                stmt.executeUpdate( "INSERT INTO COFFEES " + "VALUES ('Colombian_Decaf', 101, 8.99, 0, 0)" );

                stmt.executeUpdate( "INSERT INTO COFFEES " + "VALUES ('French_Roast_Decaf', 49, 9.99, 0, 0)" );

                // Get data from the COFFEES table

                rs = stmt.executeQuery( "SELECT COF_NAME, PRICE FROM COFFEES" );

                // Now rs contains the rows of coffees and prices from
                // the COFFEES table. To access the data, use the method
                // NEXT to access all rows in rs, one row at a time

                while ( rs.next() ) {
                    final String s = rs.getString( "COF_NAME" );
                    final float n = rs.getFloat( "PRICE" );
                    System.out.println( s + "  " + n );
                }

            }
            finally {
                close( rs );
                close( stmt );
                close( conn );
            }
        }
        catch ( final Throwable oops ) {
            oops.printStackTrace();
        }
    }

    static void close ( final Connection conn ) {
        if ( conn != null ) {
            try {
                conn.close();
            }
            catch ( final Throwable whatever ) {
            }
        }
    }

    static void close ( final Statement st ) {
        if ( st != null ) {
            try {
                st.close();
            }
            catch ( final Throwable whatever ) {
            }
        }
    }

    static void close ( final ResultSet rs ) {
        if ( rs != null ) {
            try {
                rs.close();
            }
            catch ( final Throwable whatever ) {
            }
        }
    }
}
