package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

import billingAccount.BillingAccountHelper;
import checkIn.CheckInHelper;
import medicalRecord.MedicalRecordHelper;
import patient.PatientHelper;
import staff.StaffHelper;
import ward.WardHelper;

public class Controller {

    /** Base url used as part of DatabaseBuilder */
    public static final String BASE_URL = "jdbc:mysql://localhost:3306";
    /** Full url path */
    public static final String URL      = "jdbc:mysql://localhost:3306/wolfhospital";
    /** The driver url */
    public static final String DRIVER   = "com.mysql.jdbc.Driver";
    /** The database username */
    public static final String USERNAME = "root";
    /** The database password */
    public static final String PASSWORD = "";

    /** The database connection to use */
    private static Connection  conn;

    /**
     * Runs the program and parses initial user commands
     * @param args Command line arguments
     */
    public static void main ( final String[] args ) {

        Scanner scan = null;
        try {
            Class.forName( DRIVER );
            conn = DriverManager.getConnection( URL, USERNAME, PASSWORD );
            String in = "";
            scan = new Scanner( System.in );
            String str = "Enter a command: a-Add, u-Update, d-Delete, e-Exit, "
                    + "ab-Available_beds, si-staff_info, gdp-get_doc_patients, "
                    + "mh-patient_med_history, wu-ward_usage, ppm-patients_per_month, "
                    + "ba-billing-acct";
            while ( true ) {
                System.out.println( str );
                System.out.print( "> " );
                in = scan.nextLine();
                in = in.toLowerCase();
                System.out.println("");
                if ( in.equals( "a" ) || in.equals( "add" ) ) {
                    add( scan );
                    break;
                }
                else if ( in.equals( "u" ) || in.equals( "update" ) ) {
                    update( scan );
                    break;
                }
                else if ( in.equals( "d" ) || in.equals( "delete" ) ) {
                    delete( scan );
                    break;
                }
                // gets available beds in wards
                else if ( in.equals( "ab" ) || in.equals( "Available_beds" ) ) {
                    new WardHelper( conn ).availableWardsAndBeds();
                }
                // staff info grouped by department
                else if ( in.equals( "si" ) || in.equals( "staff_info" ) ) {
                    new StaffHelper( conn ).getStaffInfoByDepartment(scan);
                }
                // get patients doctor is responsible for
                else if ( in.equals( "gdp" ) || in.equals( "get_doc_patients" ) ) {
                    new PatientHelper( conn ).getPatientsOfDoctor(scan);
                }
                // get medical history for patients
                else if ( in.equals( "mh" ) || in.equals( "patient_med_history" ) ) {
                    new MedicalRecordHelper( conn ).getPatientMedicalHistory(scan);
                }
                // get available beds in all wards
                else if ( in.equals( "wu" ) || in.equals( "ward_usage" ) ) {
                    new WardHelper( conn ).wardUsage();
                }
                // finds the patients per month
                else if ( in.equals( "ppm" ) || in.equals( "patients_per_month" ) ) {
                    new CheckInHelper( conn ).findPPM();
                }
                // gets billing account for patient
                else if ( in.equals( "ba" ) || in.equals( "billing-acct" ) ) {
                    new BillingAccountHelper( conn ).getBillingAccount(scan);
                }
                else if ( in.equals( "e" ) || in.equals( "exit" ) ) {
                    break;
                }
                else {
                    System.out.println( "Invalid command" );
                }
            }
        }
        catch ( final Exception e ) {
            e.printStackTrace();
        }
        finally {
            if ( scan != null ) {
                scan.close();
            }
        }
    }

    /**
     * Takes user input to figure out what type of entry to add and then calls the appropriate helper class
     * @param scan Used for user input
     */
    private static void add ( final Scanner scan ) {
        while ( true ) {
            System.out.println(
                    "What to add? (p-Patient, s-Staff, w-Ward, c-Check In, m-Medical Record, or b-Back to return)" );
            System.out.print( "> " );
            String in = scan.nextLine();
            in = in.toLowerCase();
            if ( in.equals( "p" ) || in.equals( "patient" ) ) {
            	final PatientHelper ph = new PatientHelper( conn );
            	while(true) {
            		System.out.println("Add o-One or m-Multiple entries?");
            		System.out.print("> ");
            		in = scan.nextLine();
            		in = in.trim().toLowerCase();
            		if(in.equals("o") || in.equals("one")) {
            			ph.add(scan);
            			return;
            		} else if(in.equals("m") || in.equals("multiple")) {
            			ph.addMultiple(scan);
            			return;
            		} else if(in.equals("b") || in.equals("back")) {
            			return;
            		} else {
            			System.out.println("Invalid command");
            		}
            	}
            }
            else if ( in.equals( "s" ) || in.equals( "staff" ) ) {
                final StaffHelper sh = new StaffHelper( conn );
                while(true) {
            		System.out.println("Add o-One or m-Multiple entries?");
            		System.out.print("> ");
            		in = scan.nextLine();
            		in = in.trim().toLowerCase();
            		if(in.equals("o") || in.equals("one")) {
            			sh.add(scan);
            			return;
            		} else if(in.equals("m") || in.equals("multiple")) {
            			sh.addMultiple(scan);
            			return;
            		} else if(in.equals("b") || in.equals("back")) {
            			return;
            		} else {
            			System.out.println("Invalid command");
            		}
            	}
            }
            else if ( in.equals( "w" ) || in.equals( "ward" ) ) {
                final WardHelper wh = new WardHelper( conn );
                wh.add( scan );
                return;
            }
            else if ( in.equals( "c" ) || in.equals( "check in" ) ) {
                final CheckInHelper ch = new CheckInHelper( conn );
                ch.add( scan );
                return;
            }
            else if ( in.equals( "m" ) || in.equals( "medical record" ) ) {
                final MedicalRecordHelper mh = new MedicalRecordHelper( conn );
                mh.add( scan );
                return;
            }
            else if ( in.equals( "b" ) || in.equals( "back" ) ) {
                return;
            }
            else {
                System.out.println( "Invalid Command" );
            }
        }
    }

    /**
     * Takes user input to figure out what type of entry to update and then calls the appropriate helper class
     * @param scan Used for user input
     */
    private static void update ( final Scanner scan ) {
        while ( true ) {
            System.out.println(
                    "What to update? (p-Patient, s-Staff, w-Ward, c-Check In, m-Medical Record, or b-Back to return)" );
            System.out.print( "> " );
            String in = scan.nextLine();
            in = in.toLowerCase();
            if ( in.equals( "p" ) || in.equals( "patient" ) ) {
                final PatientHelper ph = new PatientHelper( conn );
                ph.update( scan );
                return;
            }
            else if ( in.equals( "s" ) || in.equals( "staff" ) ) {
                final StaffHelper sh = new StaffHelper( conn );
                sh.update( scan );
                return;
            }
            else if ( in.equals( "w" ) || in.equals( "ward" ) ) {
                final WardHelper wh = new WardHelper( conn );
                wh.update( scan );
                return;
            }
            else if ( in.equals( "c" ) || in.equals( "check in" ) ) {
                final CheckInHelper ch = new CheckInHelper( conn );
                ch.update( scan );
                return;
            }
            else if ( in.equals( "m" ) || in.equals( "medical record" ) ) {
                final MedicalRecordHelper mh = new MedicalRecordHelper( conn );
                mh.update( scan );
                return;
            }
            else if ( in.equals( "b" ) || in.equals( "back" ) ) {
                return;
            }
            else {
                System.out.println( "Invalid Command" );
            }
        }
    }

    /**
     * Takes user input to figure out what type of entry to delete and then calls the appropriate helper class
     * @param scan Used for user input
     */
    private static void delete ( final Scanner scan ) {
        while ( true ) {
            System.out.println( "What to delete? (p-Patient, s-Staff, w-Ward, or b-Back to return)" );
            System.out.print( "> " );
            String in = scan.nextLine();
            in = in.toLowerCase();
            if ( in.equals( "p" ) || in.equals( "patient" ) ) {
                final PatientHelper ph = new PatientHelper( conn );
                ph.delete( scan );
                return;
            }
            else if ( in.equals( "s" ) || in.equals( "staff" ) ) {
                final StaffHelper sh = new StaffHelper( conn );
                sh.delete( scan );
                return;
            }
            else if ( in.equals( "w" ) || in.equals( "ward" ) ) {
                final WardHelper wh = new WardHelper( conn );
                wh.update( scan );
                return;
            }
            else if ( in.equals( "b" ) || in.equals( "back" ) ) {
                return;
            }
            else {
                System.out.println( "Invalid Command" );
            }
        }
    }
}
