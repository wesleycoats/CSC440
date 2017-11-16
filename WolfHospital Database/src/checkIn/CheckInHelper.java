package checkIn;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import patient.PatientDB;
import ward.WardDB;
import ward.WardHelper;

public class CheckInHelper {
	/** The database connection to use */
    private final Connection conn;

    /** The number of attributes for a check in entry */
    public static final int  NUMBER_OF_ATTRIBUTES = 7;

    /**
     * Constructor
     * @param conn The database connection to use
     */
    public CheckInHelper ( final Connection conn ) {
        this.conn = conn;
    }

    /**
     * Adds a new check in from user input
     * @param scan Used for user input
     */
    public void add ( final Scanner scan ) {
        final CheckInDB cdb = new CheckInDB( conn );
        while ( true ) {
            // User prompt
            System.out.println(
                    "Enter the check-in information in the following format: Id, Patient Id, Start Date (YYYY-MM-DD), "
                            + "End Date (YYY-MM-DD), Ward Id, Bed Number, Registration Fee" );
            System.out.print( "> " );
            // Back
            final String in = scan.nextLine().toLowerCase();
            if ( in.equals( "b" ) || in.equals( "back" ) ) {
                return;
            }
            final String[] inputArray = in.split( "," );
            if ( inputArray.length == NUMBER_OF_ATTRIBUTES ) {
                final CheckIn c = new CheckIn();
                // Id
                try {
                    final int id = Integer.parseInt( inputArray[0].trim() );
                    // Make sure id doesn't already exist
                    if ( cdb.getById( id ) == null ) {
                        c.setId( id );
                    }
                    else {
                        System.out.println( "An entry already exists with that id" );
                        continue;
                    }
                }
                catch ( final NumberFormatException e ) {
                    System.out.println( "Invalid Id" );
                    continue;
                }
                // Patient Id
                try {
                    final PatientDB pdb = new PatientDB( conn );
                    final int id = Integer.parseInt( inputArray[1].trim() );
                    // Make sure patient id exists
                    if ( pdb.getById( id ) == null ) {
                        System.out.println( "No patient exists with that id" );
                        continue;
                    }
                    else {
                        c.setPatientId( id );
                    }
                }
                catch ( final NumberFormatException e ) {
                    System.out.println( "Invalid Id" );
                    continue;
                }
                // Start date and End date
                try {
                    c.setStartDate( Date.valueOf( inputArray[2].trim() ) );
                    c.setEndDate( Date.valueOf( inputArray[3].trim() ) );
                }
                catch ( final IllegalArgumentException e ) {
                    System.out.println( "Invalid Date" );
                    continue;
                }
                // Ward Id
                try {
                    final WardDB wdb = new WardDB( conn );
                    final int id = Integer.parseInt( inputArray[4].trim() );
                    // Make sure ward already exists
                    if ( wdb.getById( id ) == null ) {
                        System.out.println( "No ward exists with that id" );
                        continue;
                    } else {
                        WardHelper wh = new WardHelper(conn);
                        int avaliability = wh.getAvaliability(id);
                        if(avaliability == -1) {
                        	continue;
                        } else if(avaliability <= 0) {
                        	System.out.println("There is no space left in that ward");
                        	continue;
                        }
                        c.setWardId( id );
                    }
                }
                catch ( final NumberFormatException e ) {
                    System.out.println( "Invalid Id" );
                    continue;
                }
                // Bed number and Registration fee
                try {
                    c.setBedNum( Integer.parseInt( inputArray[5].trim() ) );
                    c.setFee( Float.parseFloat( inputArray[6].trim() ) );
                }
                catch ( final NumberFormatException e ) {
                    System.out.println( "Invalid format" );
                }
                // Attempt to add the check in
                if ( cdb.insert( c ) ) {
                    System.out.println( "Check-in successfully added" );
                }
                else {
                    System.out.println( "Error adding check-in" );
                }
                return;
            }
            else {
                System.out.println( "Invalid Formatting" );
            }
        }
    }

    /**
     * Updates a check in from user input
     * @param scan Used for user input
     */
    public void update ( final Scanner scan ) {
        final CheckInDB cdb = new CheckInDB( conn );
        CheckIn c = null;
        int id;
        while ( true ) {
            System.out.println( "Enter the id of the check-in entry to update" );
            System.out.print( "> " );
            final String in = scan.nextLine().trim().toLowerCase();
            if ( in.equals( "b" ) || in.equals( "back" ) ) {
                return;
            }
            try {
                id = Integer.parseInt( in );
                c = cdb.getById( id );
                if ( c == null ) {
                    System.out.println( "No check-in found with that ID" );
                    continue;
                }
                break;
            }
            catch ( final NumberFormatException e ) {
                System.out.println( "Invalid id" );
            }
        }
        while ( true ) {
            System.out.println(
                    "Enter the check-in information in the following format: Id, Patient Id, Start Date (YYYY-MM-DD), "
                            + "End Date (YYY-MM-DD), Ward Id, Bed Number, Registration Fee" );
            System.out.println( "Enter * to keep an entry the same" );
            System.out.print( "> " );
            final String in = scan.nextLine().toLowerCase();
            if ( in.equals( "b" ) || in.equals( "back" ) ) {
                return;
            }
            final String[] inputArray = in.split( "," );
            if ( inputArray.length == NUMBER_OF_ATTRIBUTES ) {
                // Id
                if ( !inputArray[0].trim().equals( "*" ) ) {
                }
                try {
                    final int newId = Integer.parseInt( inputArray[0].trim() );
                    if ( cdb.getById( newId ) == null ) {
                        c.setId( newId );
                    }
                    else {
                        System.out.println( "An entry already exists with that id" );
                        continue;
                    }
                }
                catch ( final NumberFormatException e ) {
                    System.out.println( "Invalid Id" );
                    continue;
                }

                // Patient id
                if ( !inputArray[1].trim().equals( "*" ) ) {
                    try {
                        final PatientDB pdb = new PatientDB( conn );
                        final int newid = Integer.parseInt( inputArray[1].trim() );
                        if ( pdb.getById( newid ) == null ) {
                            System.out.println( "No patient exists with that id" );
                            continue;
                        }
                        else {
                            c.setPatientId( newid );
                        }
                    }
                    catch ( final NumberFormatException e ) {
                        System.out.println( "Invalid Id" );
                        continue;
                    }
                }
                // Start date
                if ( !inputArray[2].trim().equals( "*" ) ) {
                    try {
                        c.setStartDate( Date.valueOf( inputArray[2].trim() ) );
                    }
                    catch ( final IllegalArgumentException e ) {
                        System.out.println( "Invalid Date" );
                        continue;
                    }
                }
                // End date
                if ( !inputArray[3].trim().equals( "*" ) ) {
                    try {
                        c.setEndDate( Date.valueOf( inputArray[3].trim() ) );
                    }
                    catch ( final IllegalArgumentException e ) {
                        System.out.println( "Invalid Date" );
                        continue;
                    }
                }
                // Ward id
                if ( !inputArray[4].trim().equals( "*" ) ) {
                    try {
                        final WardDB wdb = new WardDB( conn );
                        final int newid = Integer.parseInt( inputArray[4].trim() );
                        if ( wdb.getById( newid ) == null ) {
                            System.out.println( "No ward exists with that id" );
                            continue;
                        }
                        else {
                        	WardHelper wh = new WardHelper(conn);
                            int avaliability = wh.getAvaliability(id);
                            if(avaliability == -1) {
                            	continue;
                            } else if(avaliability <= 0) {
                            	System.out.println("There is no space left in that ward");
                            	continue;
                            }
                            c.setWardId( newid );
                        }
                    }
                    catch ( final NumberFormatException e ) {
                        System.out.println( "Invalid Id" );
                        continue;
                    }
                }
                // Bed num
                if ( !inputArray[5].trim().equals( "*" ) ) {
                    try {
                        c.setBedNum( Integer.parseInt( inputArray[5].trim() ) );
                    }
                    catch ( final NumberFormatException e ) {
                        System.out.println( "Invalid format" );
                    }
                }
                // Reg fee
                if ( !inputArray[6].trim().equals( "*" ) ) {
                    try {
                        c.setFee( Float.parseFloat( inputArray[6].trim() ) );
                    }
                    catch ( final NumberFormatException e ) {
                        System.out.println( "Invalid format" );
                    }
                }
                if ( cdb.update( id, c ) ) {
                    System.out.println( "Check-in successfully updated" );
                }
                else {
                    System.out.println( "Error updating check-in" );
                }
                return;
            }
            else {
                System.out.println( "Invalid Formatting" );
            }
        }
    }

    /**
     * Call the functions to find the patients per month for the hospital.
     */
    public void findPPM () {
        ResultSet rs;
        final CheckInDB mdb = new CheckInDB( conn );
        rs = mdb.getPatientsPerMonth();
        try {
            if ( rs == null || !rs.next() ) {
                System.out.println( "Could not calculate patients per month." );
                System.out.print( "There could be no medical records in the database." );
            }
            else {
                do {
                    System.out.printf( "Year: %d Patients Per Month: %f\n", rs.getInt( "y" ), rs.getFloat( "ppm" ) );
                }
                while ( rs.next() );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
