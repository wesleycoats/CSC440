package ward;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.SQLException;

import staff.StaffDB;

public class WardHelper {
    private final Connection conn;

    public static final int  NUMBER_OF_ATTRIBUTES = 6;

    public WardHelper ( final Connection conn ) {
        this.conn = conn;
    }

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
	 * This gets the ward number and number of available beds
	 * in the ward.
	 */
	public void availableWardsAndBeds() throws SQLException{
		WardDB pdb = new WardDB(conn);
		ResultSet rs = pdb.checkAvailableWardsAndBeds();
        ArrayList<Integer> ids = new ArrayList<>();
        ArrayList<Integer> totals = new ArrayList<>();

		while (rs.next()) {
			int total = rs.getInt("total");
			int id = rs.getInt("id");
			int capacity1 = rs.getInt( "capacity_one" );
			int capacity2 = rs.getInt( "capacity_two" );
			int capacity3 = rs.getInt( "capacity_three" );

            if (capacity1 == 1) {
                if (total > 1) {
                    total = 0;
                } else {
                    total = 1 - total;
                }
            } else if (capacity2 == 1) {
                if (total > 2) {
                    total = 0;
                } else {
                    total = 2 - total;
                }
            } else if (capacity3 == 1) {
                if (total > 3) {
                    total = 0;
                } else {
                    total = 3 - total;
                }
            }

			ids.add(id);
			totals.add(total);
		}

		if (ids.size() == 0) {
		    System.out.println("No Available wards");
        }

		for (int i = 0; i < ids.size(); i++) {
            System.out.printf("Ward Number: %d Available Beds: %d\n", ids.get(i), totals.get(i));
        }

		System.out.println("");
	}

    /**
     * Gets the number of open beds for each ward
     */
    public void wardUsage() throws SQLException{
        WardDB pdb = new WardDB(conn);
        ResultSet rs = pdb.getWardUsageStatus();
        ArrayList<Integer> ids = new ArrayList<>();
        ArrayList<Integer> totals = new ArrayList<>();

        while (rs.next()) {
            int total = rs.getInt("total");
            int id = rs.getInt("id");
            int capacity1 = rs.getInt( "capacity_one" );
            int capacity2 = rs.getInt( "capacity_two" );
            int capacity3 = rs.getInt( "capacity_three" );

            if (capacity1 == 1) {
                if (total > 1) {
                    total = 0;
                } else {
                    total = 1 - total;
                }
            } else if (capacity2 == 1) {
                if (total > 2) {
                    total = 0;
                } else {
                    total = 2 - total;
                }
            } else if (capacity3 == 1) {
                if (total > 3) {
                    total = 0;
                } else {
                    total = 3 - total;
                }
            }

            ids.add(id);
            totals.add(total);
        }

        if (ids.size() == 0) {
            System.out.println("No available beds in wards");
        }

        for (int i = 0; i < ids.size(); i++) {
            System.out.printf("Ward Number: %d Available Beds: %d\n", ids.get(i), totals.get(i));
        }

        System.out.println("");
    }

    /**
     * Gets the uasge percent for each ward
     */
    public void wardUsagePercent() throws SQLException{
        WardDB pdb = new WardDB(conn);
        ResultSet rs = pdb.getWardUsageStatus();
        ArrayList<Integer> ids = new ArrayList<>();
        ArrayList<Double> percents = new ArrayList<>();


        while (rs.next()) {
            int total = rs.getInt("total");
            int id = rs.getInt("id");
            int capacity1 = rs.getInt( "capacity_one" );
            int capacity2 = rs.getInt( "capacity_two" );
            int capacity3 = rs.getInt( "capacity_three" );

            if (capacity1 == 1) {
                if (total > 1) {
                    total = 0;
                } else {
                    total = 1 - total;
                }

                ids.add(id);
                percents.add(100.0);
            } else if (capacity2 == 1) {
                if (total > 2) {
                    total = 0;
                } else {
                    total = 2 - total;
                }
                ids.add(id);
                percents.add((total / 2.0) * 100);
            } else if (capacity3 == 1) {
                if (total > 3) {
                    total = 0;
                } else {
                    total = 3 - total;
                }
                ids.add(id);
                percents.add((total / 3.0) * 100);
            }
        }

        if (ids.size() == 0) {
            System.out.println("No wards in database!");
        }

        for (int i = 0; i < ids.size(); i++) {
            System.out.printf("Ward Number: %d Ward Usage Percent: %f%\n", ids.get(i), percents.get(i));
        }

        System.out.println("");
    }
}
