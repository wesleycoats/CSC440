package ward;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import staff.StaffDB;

public class WardHelper {

    /** The database connection to use */
    private final Connection conn;

    /** The number of attributes for a ward entry */
    public static final int  NUMBER_OF_ATTRIBUTES = 6;

    /**
     * Constructor
     *
     * @param conn
     *            The database connection to use
     */
    public WardHelper ( final Connection conn ) {
        this.conn = conn;
    }

    /**
     * Adds a new ward from user input
     *
     * @param scan
     *            Used for user input
     */
    public void add ( final Scanner scan ) {
        final WardDB wdb = new WardDB( conn );
        while ( true ) {
            System.out.println(
                    "Enter the ward information in the following format: Id, Capacity-1, Capacity-2, Capacity-3, Charges per Day, Nurse Id" );
            System.out.print( "> " );
            final String in = scan.nextLine().toLowerCase();
            if ( in.equals( "b" ) || in.equals( "back" ) ) {
                return;
            }
            final String[] inputArray = in.split( "," );
            if ( inputArray.length == NUMBER_OF_ATTRIBUTES ) {
                final Ward w = new Ward();
                // Id
                try {
                    final int id = Integer.parseInt( inputArray[0].trim() );
                    if ( wdb.getById( id ) == null ) {
                        w.setWardNum( id );
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
                // Capacity 1, 2 and 3
                try {
                    w.setCapacity1( Integer.parseInt( inputArray[1].trim() ) );
                    w.setCapacity2( Integer.parseInt( inputArray[2].trim() ) );
                    w.setCapacity3( Integer.parseInt( inputArray[3].trim() ) );
                }
                catch ( final NumberFormatException e ) {
                    System.out.println( "Invalid capacity format" );
                    continue;
                }
                // Charges per day
                try {
                    w.setCharge( Float.parseFloat( inputArray[4].trim() ) );
                }
                catch ( final NumberFormatException e ) {
                    System.out.println( "Invalid charges format" );
                    continue;
                }
                // Nurse Id
                try {
                    final StaffDB sdb = new StaffDB( conn );
                    final int id = Integer.parseInt( inputArray[5].trim() );
                    // Make sure patient id exists
                    if ( sdb.getById( id ) == null ) {
                        System.out.println( "No staff exists with that id" );
                        continue;
                    }
                    else {
                        w.setNurseId( id );
                    }
                }
                catch ( final NumberFormatException e ) {
                    System.out.println( "Invalid Id" );
                    continue;
                }
                // Attempt to add ward
                if ( wdb.insert( w ) ) {
                    System.out.println( "Ward successfully added" );
                }
                else {
                    System.out.println( "Error adding ward" );
                }
                return;
            }
            else {
                System.out.println( "Invalid Formatting" );
            }
        }
    }

    /**
     * Updates a ward entry from user input
     *
     * @param scan
     *            Used for user input
     */
    public void update ( final Scanner scan ) {
        final WardDB wdb = new WardDB( conn );
        Ward w = null;
        int id;
        while ( true ) {
            System.out.println( "Enter the id of the ward entry to update" );
            System.out.print( "> " );
            final String in = scan.nextLine().trim().toLowerCase();
            if ( in.equals( "b" ) || in.equals( "back" ) ) {
                return;
            }
            // Check id
            try {
                id = Integer.parseInt( in );
                w = wdb.getById( id );
                if ( w == null ) {
                    System.out.println( "No ward found with that ID" );
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
                    "Enter new data for this entry in the following format: Id, Capacity-1, Capacity-2, Capacity-3, Charges per Day, Nurse Id" );
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
                        if ( wdb.getById( newId ) == null ) {
                            w.setWardNum( newId );
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
                // Capacity 1
                if ( !inputArray[1].trim().equals( "*" ) ) {
                    try {
                        w.setCapacity1( Integer.parseInt( inputArray[1].trim() ) );
                    }
                    catch ( final NumberFormatException e ) {
                        System.out.println( "Invalid capacity format" );
                        continue;
                    }
                }
                // Capacity 2
                if ( !inputArray[2].trim().equals( "*" ) ) {
                    try {
                        w.setCapacity2( Integer.parseInt( inputArray[2].trim() ) );
                    }
                    catch ( final NumberFormatException e ) {
                        System.out.println( "Invalid capacity format" );
                        continue;
                    }
                }
                // Capacity 3
                if ( !inputArray[3].trim().equals( "*" ) ) {
                    try {
                        w.setCapacity3( Integer.parseInt( inputArray[3].trim() ) );
                    }
                    catch ( final NumberFormatException e ) {
                        System.out.println( "Invalid capacity format" );
                        continue;
                    }
                }
                // Charges
                if ( !inputArray[4].trim().equals( "*" ) ) {
                    try {
                        w.setCharge( Float.parseFloat( inputArray[4].trim() ) );
                    }
                    catch ( final NumberFormatException e ) {
                        System.out.println( "Invalid charges format" );
                        continue;
                    }
                }
                // Nurse id
                if ( !inputArray[5].trim().equals( "*" ) ) {
                    try {
                        final StaffDB sdb = new StaffDB( conn );
                        final int newid = Integer.parseInt( inputArray[5].trim() );
                        // Make sure patient id exists
                        if ( sdb.getById( newid ) == null ) {
                            System.out.println( "No staff exists with that id" );
                            continue;
                        }
                        else {
                            w.setNurseId( newid );
                        }
                    }
                    catch ( final NumberFormatException e ) {
                        System.out.println( "Invalid Id" );
                        continue;
                    }
                }
                // Update
                if ( wdb.update( id, w ) ) {
                    System.out.println( "Ward successfully updated" );
                }
                else {
                    System.out.println( "Error updating ward" );
                }
                return;
            }
            else {
                System.out.println( "Invalid Formatting" );
            }
        }
    }

    /**
     * Deletes a ward entry from user input
     *
     * @param scan
     *            Used for user input
     */
    public void delete ( final Scanner scan ) {
        final WardDB wdb = new WardDB( conn );
        int id;
        while ( true ) {
            System.out.println( "Enter the id of the ward entry to delete" );
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
            if ( wdb.getById( id ) != null ) {
                if ( wdb.deleteById( id ) ) {
                    System.out.println( "Ward entry successfully deleted" );
                }
                else {
                    System.out.println( "Ward entry could not be deleted" );
                }
                return;
            }
            else {
                System.out.println( "No entry found with that ID" );
            }
        }
    }

    /**
     * This gets the ward number and number of available beds in the ward.
     */
    public void availableWardsAndBeds () throws SQLException {
        final WardDB pdb = new WardDB( conn );
        final ResultSet rs = pdb.checkAvailableWardsAndBeds();
        final ArrayList<Integer> ids = new ArrayList<>();
        final ArrayList<Integer> totals = new ArrayList<>();

        while ( rs.next() ) {
            int total = rs.getInt( "total" );
            final int id = rs.getInt( "id" );
            final int capacity1 = rs.getInt( "capacity_one" );
            final int capacity2 = rs.getInt( "capacity_two" );
            final int capacity3 = rs.getInt( "capacity_three" );

            if ( capacity1 == 1 ) {
                if ( total > 1 ) {
                    total = 0;
                }
                else {
                    total = 1 - total;
                }
            }
            else if ( capacity2 == 1 ) {
                if ( total > 2 ) {
                    total = 0;
                }
                else {
                    total = 2 - total;
                }
            }
            else if ( capacity3 == 1 ) {
                if ( total > 3 ) {
                    total = 0;
                }
                else {
                    total = 3 - total;
                }
            }

            ids.add( id );
            totals.add( total );
        }

        if ( ids.size() == 0 ) {
            System.out.println( "No Available wards" );
        }

        for ( int i = 0; i < ids.size(); i++ ) {
            System.out.printf( "Ward Number: %d Available Beds: %d\n", ids.get( i ), totals.get( i ) );
        }

        System.out.println( "" );
    }

    public int getAvaliability ( final int wardId ) {
        final WardDB pdb = new WardDB( conn );
        final ResultSet rs = pdb.checkAvailableWardsAndBeds();
        final ArrayList<Integer> ids = new ArrayList<>();
        final ArrayList<Integer> totals = new ArrayList<>();

        try {
            while ( rs.next() ) {
                int total = rs.getInt( "total" );
                final int id = rs.getInt( "id" );
                final int capacity1 = rs.getInt( "capacity_one" );
                final int capacity2 = rs.getInt( "capacity_two" );
                final int capacity3 = rs.getInt( "capacity_three" );

                if ( capacity1 == 1 ) {
                    if ( total > 1 ) {
                        total = 0;
                    }
                    else {
                        total = 1 - total;
                    }
                }
                else if ( capacity2 == 1 ) {
                    if ( total > 2 ) {
                        total = 0;
                    }
                    else {
                        total = 2 - total;
                    }
                }
                else if ( capacity3 == 1 ) {
                    if ( total > 3 ) {
                        total = 0;
                    }
                    else {
                        total = 3 - total;
                    }
                }

                ids.add( id );
                totals.add( total );
            }
            for ( int i = 0; i < ids.size(); i++ ) {
                if ( ids.get( i ) == wardId ) {
                    if ( totals.get( i ) < 0 ) {
                        return 0;
                    }
                    else {
                        return totals.get( i );
                    }
                }
            }
            return 0;
        }
        catch ( final SQLException e ) {
            System.out.println( "Error Accessing Ward Information" );
            return -1;
        }
    }

    /**
     * Gets the number of open beds for each ward
     */
    public void wardUsage () throws SQLException {
        final WardDB pdb = new WardDB( conn );
        final ResultSet rs = pdb.getWardUsageStatus();
        final ArrayList<Integer> ids = new ArrayList<>();
        final ArrayList<Integer> totals = new ArrayList<>();

        while ( rs.next() ) {
            int total = rs.getInt( "total" );
            final int id = rs.getInt( "id" );
            final int capacity1 = rs.getInt( "capacity_one" );
            final int capacity2 = rs.getInt( "capacity_two" );
            final int capacity3 = rs.getInt( "capacity_three" );

            if ( capacity1 == 1 ) {
                if ( total > 1 ) {
                    total = 0;
                }
                else {
                    total = 1 - total;
                }
            }
            else if ( capacity2 == 1 ) {
                if ( total > 2 ) {
                    total = 0;
                }
                else {
                    total = 2 - total;
                }
            }
            else if ( capacity3 == 1 ) {
                if ( total > 3 ) {
                    total = 0;
                }
                else {
                    total = 3 - total;
                }
            }

            ids.add( id );
            totals.add( total );
        }

        if ( ids.size() == 0 ) {
            System.out.println( "No available beds in wards" );
        }

        for ( int i = 0; i < ids.size(); i++ ) {
            System.out.printf( "Ward Number: %d Available Beds: %d\n", ids.get( i ), totals.get( i ) );
        }

        System.out.println( "" );
    }

    /**
     * Gets the usage percent for each ward
     */
    public void wardUsagePercent () throws SQLException {
        final WardDB pdb = new WardDB( conn );
        final ResultSet rs = pdb.getWardUsageStatus();
        final ArrayList<Integer> ids = new ArrayList<>();
        final ArrayList<Double> percents = new ArrayList<>();
        final ArrayList<Integer> totals = new ArrayList<>();

        while ( rs.next() ) {
            int total = 0;
            final int id = rs.getInt( "id" );
            final int capacity1 = rs.getInt( "capacity_one" );
            final int capacity2 = rs.getInt( "capacity_two" );
            final int capacity3 = rs.getInt( "capacity_three" );

            if ( capacity1 == 1 ) {
                total += 1;
            }
            if ( capacity2 == 1 ) {
                total += 1;
            }
            if ( capacity3 == 1 ) {
                total += 1;
            }
            ids.add( id );
            totals.add( 3 - total );
            percents.add( ( total / 3.0 ) * 100.0 );
        }

        if ( ids.size() == 0 ) {
            System.out.println( "No wards in database!" );
        }

        for ( int i = 0; i < ids.size(); i++ ) {
            System.out.printf( "Ward Number: %d Available Beds: %d Ward Usage Percent: %.2f%%\n", ids.get( i ),
                    totals.get( i ), percents.get( i ) );
        }

        System.out.println( "" );
    }
}
