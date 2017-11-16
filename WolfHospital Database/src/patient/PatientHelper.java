package patient;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import staff.StaffDB;

public class PatientHelper {
    private final Connection conn;

    public static final int  NUMBER_OF_ATTRIBUTES = 8;

    public PatientHelper ( final Connection conn ) {
        this.conn = conn;
    }

    public void add ( final Scanner scan ) {
        final PatientDB pdb = new PatientDB( conn );
        while ( true ) {
            System.out.println(
                    "Enter the patient information in the following format: Id, Name, SSN, Date of Birth (YYYY-MM-DD), Gender, Phone Number, Address, Status" );
            System.out.print( "> " );
            final String in = scan.nextLine().toLowerCase();
            if ( in.equals( "b" ) || in.equals( "back" ) ) {
                return;
            }
            final String[] inputArray = in.split( "," );
            if ( inputArray.length == NUMBER_OF_ATTRIBUTES ) {
                final Patient p = new Patient();
                try {
                    final int id = Integer.parseInt( inputArray[0].trim() );
                    if ( pdb.getById( id ) == null ) {
                        p.setId( id );
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
                p.setSsn( inputArray[1].trim() );
                p.setName( inputArray[2].trim() );
                try {
                    p.setDob( Date.valueOf( inputArray[3].trim() ) );
                }
                catch ( final IllegalArgumentException e ) {
                    System.out.println( "Invalid Date" );
                    continue;
                }
                p.setGender( inputArray[4].trim() );
                p.setPhone( inputArray[5].trim() );
                p.setAddress( inputArray[6].trim() );
                p.setStatus( inputArray[7].trim() );
                if ( pdb.insert( p ) ) {
                    System.out.println( "Patient successfully added" );
                }
                else {
                    System.out.println( "Error adding patient" );
                }
                return;
            }
            else {
                System.out.println( "Invalid Formatting" );
            }
        }
    }

    /**
     * Continuously prompts the user to add new patient until the user sends
     * commit. Uses a transaction to ensure that everything is added without
     * errors.
     *
     * @param scan
     *            A scanner for user input
     */
    public void addMultiple ( final Scanner scan ) {
        final PatientDB pdb = new PatientDB( conn );

        // Prompt for input and explain format
        System.out.println(
                "Enter the patient information in the following format: Id, Name, SSN, Date of Birth (YYYY-MM-DD), Gender, Phone Number, Address, Status" );
        System.out.println( "Enter c-Commit when all patients are added or b-Back to return and discard all changes" );

        // Disable auto-commit to start the transaction
        try {
            conn.setAutoCommit( false );
        }
        catch ( final SQLException e ) {
            System.out.println( "An error occured when communicating with the database" );
            return;
        }

        // Take input until the user inputs commit or back
        while ( true ) {
            System.out.print( "> " );
            final String in = scan.nextLine().trim().toLowerCase();

            // User finishes inputing data and runs commit
            if ( in.equals( "c" ) || in.equals( "commit" ) ) {
                try {
                    // Commit transaction
                    conn.commit();
                    // Set SQL back to using auto commit
                    conn.setAutoCommit( true );
                    System.out.println( "Transaction Completed Successfully" );
                }
                catch ( final SQLException e ) {
                    System.out.println( "An error occured when communicating with the database" );
                }
                return;

                // User wants to discard the data and go back
            }
            else if ( in.equals( "b" ) || in.equals( "back" ) ) {
                try {
                    // Rollback all changes
                    conn.rollback();
                    // Set SQL back to using auto commit
                    conn.setAutoCommit( true );
                    System.out.println( "Transaction rolled back" );
                }
                catch ( final SQLException e ) {
                    System.out.println( "An error occured when communicating with the database" );
                }
                return;

                // Parse user input into a patient object
            }
            else {
                final String[] inputArray = in.split( "," );
                // Ensure that the input has the proper number of attributes
                if ( inputArray.length == NUMBER_OF_ATTRIBUTES ) {
                    final Patient p = new Patient();
                    // Add each attribute to the patient object
                    try {
                        final int id = Integer.parseInt( inputArray[0].trim() );
                        if ( pdb.getById( id ) == null ) {
                            p.setId( id );
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
                    p.setSsn( inputArray[1].trim() );
                    p.setName( inputArray[2].trim() );
                    try {
                        p.setDob( Date.valueOf( inputArray[3].trim() ) );
                    }
                    catch ( final IllegalArgumentException e ) {
                        System.out.println( "Invalid Date" );
                        continue;
                    }
                    p.setGender( inputArray[4].trim() );
                    p.setPhone( inputArray[5].trim() );
                    p.setAddress( inputArray[6].trim() );
                    p.setStatus( inputArray[7].trim() );

                    // Insert the data into the database
                    if ( pdb.insert( p ) ) {
                        continue;
                        // If the insert fails, rollback the changes
                    }
                    else {
                        System.out.println( "Transaction Failed. Rolling Back Changes" );
                        try {
                            conn.rollback();
                            conn.setAutoCommit( true );
                        }
                        catch ( final SQLException e ) {
                            System.out.println( "An error occured when communicating with the database" );
                        }
                        return;
                    }
                }
                else {
                    System.out.println( "Invalid Formatting" );
                    System.out.println(
                            "Enter the patient information in the following format: Id, Name, SSN, Date of Birth (YYYY-MM-DD), Gender, Phone Number, Address, Status" );
                    System.out.println(
                            "Enter d-Done when all patients are added or b-Back to return and discard all changes" );
                    continue;
                }
            }
        }
    }

    public void update ( final Scanner scan ) {
        final PatientDB pdb = new PatientDB( conn );
        Patient p = null;
        int id;
        while ( true ) {
            System.out.println( "Enter the id of the patient entry to update" );
            System.out.print( "> " );
            final String in = scan.nextLine().trim().toLowerCase();
            if ( in.equals( "b" ) || in.equals( "back" ) ) {
                return;
            }
            try {
                id = Integer.parseInt( in );
                p = pdb.getById( id );
                if ( p == null ) {
                    System.out.println( "No patient found with that ID" );
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
                    "Enter new data for this entry in the following format: Id, Name, SSN, Date of Birth (YYYY-MM-DD), Gender, Phone Number, Address, Status" );
            System.out.println( "Enter * to keep an entry the same" );
            System.out.print( "> " );
            final String in = scan.nextLine().toLowerCase();
            if ( in.equals( "b" ) || in.equals( "back" ) ) {
                return;
            }
            final String[] inputArray = in.split( "," );
            if ( inputArray.length == NUMBER_OF_ATTRIBUTES ) {
                if ( !inputArray[0].trim().equals( "*" ) ) {
                    try {
                        final int newId = Integer.parseInt( inputArray[0].trim() );
                        if ( pdb.getById( newId ) == null ) {
                            p.setId( newId );
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
                }
                if ( !inputArray[1].trim().equals( "*" ) ) {
                    p.setName( inputArray[1].trim() );
                }
                if ( !inputArray[2].trim().equals( "*" ) ) {
                    p.setName( inputArray[2].trim() );
                }
                if ( !inputArray[3].trim().equals( "*" ) ) {
                    try {
                        p.setDob( Date.valueOf( inputArray[3].trim() ) );
                    }
                    catch ( final IllegalArgumentException e ) {
                        System.out.println( "Invalid Date" );
                        continue;
                    }
                }
                if ( !inputArray[4].trim().equals( "*" ) ) {
                    p.setGender( inputArray[4].trim() );
                }
                if ( !inputArray[5].trim().equals( "*" ) ) {
                    p.setPhone( inputArray[5].trim() );
                }
                if ( !inputArray[6].trim().equals( "*" ) ) {
                    p.setAddress( inputArray[6].trim() );
                }
                if ( !inputArray[7].trim().equals( "*" ) ) {
                    p.setStatus( inputArray[7].trim() );
                }
                if ( pdb.update( id, p ) ) {
                    System.out.println( "Patient successfully updated" );
                }
                else {
                    System.out.println( "Error updating patient" );
                }
                return;
            }
            else {
                System.out.println( "Invalid Formatting" );
            }
        }
    }

    public void delete ( final Scanner scan ) {
        final PatientDB pdb = new PatientDB( conn );
        int id;
        while ( true ) {
            System.out.println( "Enter the id of the patient entry to delete" );
            System.out.print( "> " );
            final String in = scan.nextLine().trim().toLowerCase();
            if ( in.equals( "b" ) || in.equals( "back" ) ) {
                return;
            }
            try {
                id = Integer.parseInt( in );
            }
            catch ( final NumberFormatException e ) {
                System.out.println( "Invalid id" );
                continue;
            }
            if ( pdb.getById( id ) != null ) {
                if ( pdb.deleteById( id ) ) {
                    System.out.println( "Patient entry successfully deleted" );
                }
                else {
                    System.out.println( "Patient entry could not be deleted" );
                }
                return;
            }
            else {
                System.out.println( "No entry found with that ID" );
            }
        }
    }

    /**
     * Gets information about all the patients a given doctor is currently
     * responsible for.
     *
     * @param scan
     *            used to get user input
     */
    public void getPatientsOfDoctor ( final Scanner scan ) {
        int id;
        ArrayList<Patient> patients;
        final PatientDB pdb = new PatientDB( conn );
        final StaffDB sdb = new StaffDB( conn );

        while ( true ) {
            System.out.println( "Enter doctor id or b-back:" );
            System.out.print( "> " );
            final String in = scan.nextLine().trim().toLowerCase();

            if ( in.equals( "b" ) || in.equals( "back" ) ) {
                return;
            }
            try {
                id = Integer.parseInt( in );
            }
            catch ( final NumberFormatException e ) {
                System.out.println( "Invalid id" );
                continue;
            }

            if ( sdb.getById( id ) != null ) {
                if ( ( patients = pdb.getDoctorPatients( id ) ) != null ) {
                    for ( final Patient p : patients ) {
                        System.out.printf( "Name: %s\n", p.getName() );
                        System.out.printf( "\tDateOfBirth: %s", p.getDob() );
                        System.out.printf( "\tGender: %s", p.getGender() );
                        System.out.printf( "\tPhone: %s", p.getPhone() );
                        System.out.printf( "\tAddress: %s", p.getAddress() );
                        System.out.printf( "\tStatus: %s\n\n", p.getStatus() );

                    }
                    return;
                }
                else {
                    System.out.println( "Doctor not responsible for any patients!" );
                    return;
                }
            }
            else {
                System.out.println( "Not a valid doctor id!" );
            }
        }
    }
}
