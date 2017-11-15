package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

import checkIn.CheckInHelper;
import medicalRecord.MedicalRecordHelper;
import patient.PatientHelper;
import staff.StaffHelper;
import ward.WardHelper;

public class Controller {

    public static final String BASE_URL = "jdbc:mysql://localhost:3306";
    public static final String URL      = "jdbc:mysql://localhost:3306/wolfhospital";
    public static final String DRIVER   = "com.mysql.jdbc.Driver";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";

    private static Connection  conn;

    public static void main ( final String[] args ) {

        Scanner scan = null;
        try {
            Class.forName( DRIVER );
            conn = DriverManager.getConnection( URL, USERNAME, PASSWORD );
            String in = "";
            scan = new Scanner( System.in );
            while ( true ) {
                System.out.println( "Enter a command (a-Add, u-Update, d-Delete, e-Exit)" );
                System.out.print( "> " );
                in = scan.nextLine();
                in = in.toLowerCase();
                if ( in.equals( "a" ) || in.equals( "add" ) ) {
                    add( scan );
                }
                else if ( in.equals( "u" ) || in.equals( "update" ) ) {
                    update( scan );
                }
                else if ( in.equals( "d" ) || in.equals( "delete" ) ) {
                    delete( scan );
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
