package billingAccount;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

import checkIn.CheckInDB;
import patient.Patient;
import patient.PatientDB;

public class BillingAccountHelper {
    
	/** The database connection to use */
	private final Connection conn;

	/**
	 * Constructor
	 * @param conn The database connection to use
	 */
    public BillingAccountHelper ( final Connection conn ) {
        this.conn = conn;
    }

    /**
     * Prints out billing account.
     *
     * @param scan
     *            used for user input.
     */
    public void getBillingAccount ( final Scanner scan ) {
        final BillingAccountDB bdb = new BillingAccountDB( conn );
        int id;
        while (true) {
            System.out.println("Enter Patient id or b (back):");
            System.out.printf("> ");


            final String in = scan.nextLine().toLowerCase();
            if ( in.equals( "b" ) || in.equals( "back" ) ) {
                return;
            }
            try {
                id = Integer.parseInt( in );
                final ArrayList<BillingAccount> bas = bdb.getBillingAccount( id );

                if ( bas == null || bas.size() == 0 ) {
                    System.out.println( "There is no billing account for that id" );
                }
                else {
                    System.out.printf( "Patient ID: %d\n", id );
                    for ( final BillingAccount b : bas ) {
                        System.out.printf( "\tVisit date: %s Consultation fee: %.2f Test fee: %.2f Treatment fee: %.2f "
                                        + "Registration fee: %.2f Accommodation fee: %.2f\n",
                                b.getDate().toString(), b.getConsultingFee(), b.getTestFee(), b.getTreatmentFee(),
                                b.getRegistrationFee(), b.getAccomadationFee() );
                    }
                }
            }
            catch ( final NumberFormatException e ) {
                System.out.println( "Invalid id" );
            }
        }
    }
    
    /**
     * Adds a new billing account from user input
     * @param scan Used for user input
     */
    public void add ( final Scanner scan ) {
        final BillingAccountDB badb = new BillingAccountDB( conn );
        while ( true ) {
            System.out.println(
                    "Enter the billing account information in the following format: ID, Patient ID, Check In ID, Payer SSN, Payment Type, Billing Address" );
            System.out.print( "> " );
            final String in = scan.nextLine().toLowerCase();
            if ( in.equals( "b" ) || in.equals( "back" ) ) {
                return;
            }
            final String[] inputArray = in.split( "," );
            if ( inputArray.length == 6 ) {
                final BillingAccount p = new BillingAccount();
                try {
                    final int id = Integer.parseInt( inputArray[0].trim() );
                    if ( badb.getById( id ) == null ) {
                        p.setId( id );
                    }
                    else {
                        System.out.println( "An entry already exists with that id" );
                        continue;
                    }
                } catch ( final NumberFormatException e ) {
                    System.out.println( "Invalid Id" );
                    continue;
                }
                try {
                	PatientDB pdb = new PatientDB(conn);
                	if(pdb.getById(Integer.parseInt(inputArray[1].trim())) == null) {
                		System.out.println("No patient found with that id");
                		continue;
                	}
                	p.setPatientId( Integer.parseInt(inputArray[1].trim() ));
                } catch (NumberFormatException e) {
                	System.out.println("Invalid Patient Id");
                }
                try {
                	CheckInDB cdb = new CheckInDB(conn);
                	if(cdb.getById(Integer.parseInt(inputArray[2].trim())) == null) {
                		System.out.println("No check in found with that id");
                		continue;
                	}
                	p.setCheckinId( Integer.parseInt(inputArray[2].trim() ));
                	p.setDate(cdb.getById(Integer.parseInt(inputArray[2].trim())).getStartDate());
                } catch (NumberFormatException e) {
                	System.out.println("Invalid Check In Id");
                }
                p.setPayerSsn(inputArray[3].trim());
                p.setPmtType(inputArray[4].trim());
                p.setAddress(inputArray[5].trim());
                if ( badb.insert( p ) ) {
                    System.out.println( "Billing account successfully added" );
                }
                else {
                    System.out.println( "Error adding billing account" );
                }
                return;
            }
            else {
                System.out.println( "Invalid Formatting" );
            }
        }
    }
    
    /**
     * Updates a billing account from user input
     * @param scan Used for user input
     */
    public void update ( final Scanner scan ) {
        final BillingAccountDB badb = new BillingAccountDB( conn );
        BillingAccount p = null;
        int id;
        int pid;
        int cid;
        while ( true ) {
            System.out.println( "Enter the patient id and check in id of the billing account to update in the following format: id, patient id, check in id" );
            System.out.print( "> " );
            final String in = scan.nextLine().trim().toLowerCase();
            if ( in.equals( "b" ) || in.equals( "back" ) ) {
                return;
            }
            String[] inputArray = in.split(",");
            if(inputArray.length != 3) {
            	System.out.println("Invalid Formatting");
            	continue;
            }
            try {
            	id = Integer.parseInt(inputArray[0].trim());
                pid = Integer.parseInt( inputArray[1].trim() );
                cid = Integer.parseInt(inputArray[2].trim());
                p = badb.getByPatientAndCheckIn(id, pid, cid);
                if ( p == null ) {
                    System.out.println( "No billing account found with those IDs" );
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
                    "Enter the billing account information in the following format: Patient ID, Check In ID, Visit Date(YYYY-MM-DD), Payer SSN, Payment Type, Billing Address" );
            System.out.println( "Enter * to keep an entry the same" );
            System.out.print( "> " );
            final String in = scan.nextLine().toLowerCase();
            if ( in.equals( "b" ) || in.equals( "back" ) ) {
                return;
            }
            final String[] inputArray = in.split( "," );
            if ( inputArray.length == 6 ) {
                if ( !inputArray[0].trim().equals( "*" ) ) {
                	try {
                    	PatientDB pdb = new PatientDB(conn);
                    	if(pdb.getById(Integer.parseInt(inputArray[0].trim())) == null) {
                    		System.out.println("No patient found with that id");
                    		continue;
                    	}
                    	p.setPatientId( Integer.parseInt(inputArray[0].trim() ));
                    } catch (NumberFormatException e) {
                    	System.out.println("Invalid Patient Id");
                    }
                }
                if ( !inputArray[1].trim().equals( "*" ) ) {
                	try {
                    	CheckInDB cdb = new CheckInDB(conn);
                    	if(cdb.getById(Integer.parseInt(inputArray[1].trim())) == null) {
                    		System.out.println("No check in found with that id");
                    		continue;
                    	}
                    	p.setCheckinId( Integer.parseInt(inputArray[1].trim() ));
                    } catch (NumberFormatException e) {
                    	System.out.println("Invalid Check In Id");
                    }
                }
                if ( !inputArray[2].trim().equals( "*" ) ) {
                    try {
                    	p.setDate(Date.valueOf(inputArray[2].trim()));
                    } catch (IllegalArgumentException e) {
                    	System.out.println("Invalid Date");
                    	continue;
                    }
                }
                if ( !inputArray[3].trim().equals( "*" ) ) {
                    p.setPayerSsn(inputArray[3].trim());
                }
                if ( !inputArray[4].trim().equals( "*" ) ) {
                    p.setPmtType( inputArray[4].trim() );
                }
                if ( !inputArray[5].trim().equals( "*" ) ) {
                    p.setAddress( inputArray[5].trim() );
                }
                if ( badb.updateWithPatientAndCheckinIds(id, pid, cid, p) ) {
                    System.out.println( "Billing account successfully updated" );
                }
                else {
                    System.out.println( "Error updating Billing account" );
                }
                return;
            }
            else {
                System.out.println( "Invalid Formatting" );
            }
        }
    }
}
