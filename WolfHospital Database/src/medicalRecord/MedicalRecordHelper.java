package medicalRecord;

import java.sql.Connection;
import java.sql.Date;
import java.util.Scanner;

import patient.PatientDB;
import staff.StaffDB;

public class MedicalRecordHelper {
    private final Connection conn;

    public static final int  NUMBER_OF_ATTRIBUTES = 14;

    public MedicalRecordHelper ( final Connection conn ) {
        this.conn = conn;
    }

    public void add ( final Scanner scan ) {
        final MedicalRecordDB mdb = new MedicalRecordDB( conn );
        while ( true ) {
            System.out.println( "Enter the medicalRecord information in the following format: "
                    + "Id, Patient Id, Start Date (YYYY-MM-DD), End Date (YYYY-MM-DD), Doctor Id, Test Type, Test Results, "
                    + "Prescription, Diagnosis Details, Treatment, Consultation Fee, Test Fee, Treatment Fee, Specialist Id" );
            System.out.print( "> " );
            final String in = scan.nextLine().toLowerCase();
            if ( in.equals( "b" ) || in.equals( "back" ) ) {
                return;
            }
            final String[] inputArray = in.split( "," );
            if ( inputArray.length == NUMBER_OF_ATTRIBUTES ) {
                final MedicalRecord m = new MedicalRecord();
                // Id
                try {
                    final int id = Integer.parseInt( inputArray[0].trim() );
                    if ( mdb.getById( id ) == null ) {
                        m.setId( id );
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
                        m.setPatientId( id );
                    }
                }
                catch ( final NumberFormatException e ) {
                    System.out.println( "Invalid Id" );
                    continue;
                }
                // Start Date and End Date
                try {
                    m.setStartDate( Date.valueOf( inputArray[2].trim() ) );
                    m.setEndDate( Date.valueOf( inputArray[3].trim() ) );
                }
                catch ( final IllegalArgumentException e ) {
                    System.out.println( "Invalid Date" );
                    continue;
                }
                // Doctor Id
                try {
                    final StaffDB sdb = new StaffDB( conn );
                    final int id = Integer.parseInt( inputArray[4].trim() );
                    // Make sure patient id exists
                    if ( sdb.getById( id ) == null ) {
                        System.out.println( "No staff exists with that id" );
                        continue;
                    }
                    else {
                        m.setDoctorId( id );
                    }
                }
                catch ( final NumberFormatException e ) {
                    System.out.println( "Invalid Id" );
                    continue;
                }
                // Test Type
                m.setTestType( inputArray[5].trim() );
                // Test Results
                m.setTestResult( inputArray[6].trim() );
                // Prescription
                m.setPrescription( inputArray[7].trim() );
                // Diagnosis Details
                m.setDiagDetails( inputArray[8].trim() );
                // Treatment
                m.setTreatment( inputArray[9].trim() );
                // Consultation Fee, Test Fee and Treatment Fee
                try {
                    m.setConsultFee( Float.parseFloat( inputArray[10].trim() ) );
                    m.setTestFee( Float.parseFloat( inputArray[11].trim() ) );
                    m.setTreatmentFee( Float.parseFloat( inputArray[12].trim() ) );
                }
                catch ( final NumberFormatException e ) {
                    System.out.println( "Invalid format" );
                    continue;
                }
                // Specialist Id
                try {
                    final StaffDB sdb = new StaffDB( conn );
                    final int id = Integer.parseInt( inputArray[13].trim() );
                    // Make sure patient id exists
                    if ( sdb.getById( id ) == null ) {
                        System.out.println( "No staff exists with that id" );
                        continue;
                    }
                    else {
                        m.setSpecialistId( id );
                    }
                }
                catch ( final NumberFormatException e ) {
                    System.out.println( "Invalid Id" );
                    continue;
                }

                // Add
                if ( mdb.insert( m ) ) {
                    System.out.println( "Medical record successfully added" );
                }
                else {
                    System.out.println( "Error adding medical record" );
                }
                return;
            }
            else {
                System.out.println( "Invalid Formatting" );
            }
        }
    }

    public void update ( final Scanner scan ) {
        final MedicalRecordDB mdb = new MedicalRecordDB( conn );
        MedicalRecord m = null;
        int id;
        while ( true ) {
            System.out.println( "Enter the id of the medical record entry to update" );
            System.out.print( "> " );
            final String in = scan.nextLine().trim().toLowerCase();
            if ( in.equals( "b" ) || in.equals( "back" ) ) {
                return;
            }
            try {
                id = Integer.parseInt( in );
                m = mdb.getById( id );
                if ( m == null ) {
                    System.out.println( "No medical record found with that ID" );
                    continue;
                }
                break;
            }
            catch ( final NumberFormatException e ) {
                System.out.println( "Invalid id" );
            }
        }
        while ( true ) {
            System.out.println( "Enter new data for this entry in the following format: "
                    + "Id, Patient Id, Start Date (YYYY-MM-DD), End Date (YYYY-MM-DD), Doctor Id, Test Type, Test Results, "
                    + "Prescription, Diagnosis Details, Treatment, Consultation Fee, Test Fee, Treatment Fee, Specialist Id" );
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
                    try {
                        final int newId = Integer.parseInt( inputArray[0].trim() );
                        if ( mdb.getById( newId ) == null ) {
                            m.setId( newId );
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
                // Patient Id
                if ( !inputArray[1].trim().equals( "*" ) ) {
                    try {
                        final PatientDB pdb = new PatientDB( conn );
                        final int newid = Integer.parseInt( inputArray[1].trim() );
                        // Make sure patient id exists
                        if ( pdb.getById( newid ) == null ) {
                            System.out.println( "No patient exists with that id" );
                            continue;
                        }
                        else {
                            m.setPatientId( newid );
                        }
                    }
                    catch ( final NumberFormatException e ) {
                        System.out.println( "Invalid Id" );
                        continue;
                    }
                }
                // Start Date
                if ( !inputArray[2].trim().equals( "*" ) ) {
                    try {
                        m.setStartDate( Date.valueOf( inputArray[2].trim() ) );
                    }
                    catch ( final IllegalArgumentException e ) {
                        System.out.println( "Invalid Date" );
                        continue;
                    }
                }
                // End Date
                if ( !inputArray[3].trim().equals( "*" ) ) {
                    try {
                        m.setEndDate( Date.valueOf( inputArray[3].trim() ) );
                    }
                    catch ( final IllegalArgumentException e ) {
                        System.out.println( "Invalid Date" );
                        continue;
                    }
                }
                // Doctor Id
                if ( !inputArray[4].trim().equals( "*" ) ) {
                    try {
                        final StaffDB sdb = new StaffDB( conn );
                        final int newid = Integer.parseInt( inputArray[4].trim() );
                        // Make sure patient id exists
                        if ( sdb.getById( newid ) == null ) {
                            System.out.println( "No staff exists with that id" );
                            continue;
                        }
                        else {
                            m.setDoctorId( newid );
                        }
                    }
                    catch ( final NumberFormatException e ) {
                        System.out.println( "Invalid Id" );
                        continue;
                    }
                }
                // Test Type
                if ( !inputArray[5].trim().equals( "*" ) ) {
                    m.setTestType( inputArray[5].trim() );
                }
                // Test Results
                if ( !inputArray[6].trim().equals( "*" ) ) {
                    m.setTestResult( inputArray[6].trim() );
                }
                // Prescription
                if ( !inputArray[7].trim().equals( "*" ) ) {
                    m.setPrescription( inputArray[7].trim() );
                }
                // Diagnosis Details
                if ( !inputArray[8].trim().equals( "*" ) ) {
                    m.setDiagDetails( inputArray[8].trim() );
                }
                // Treatment
                if ( !inputArray[9].trim().equals( "*" ) ) {
                    m.setTreatment( inputArray[9].trim() );
                }
                // Consultation Fee
                if ( !inputArray[10].trim().equals( "*" ) ) {
                    try {
                        m.setConsultFee( Float.parseFloat( inputArray[10].trim() ) );
                    }
                    catch ( final NumberFormatException e ) {
                        System.out.println( "Invalid format" );
                        continue;
                    }
                }
                // Test Fee
                if ( !inputArray[11].trim().equals( "*" ) ) {
                    try {
                        m.setTestFee( Float.parseFloat( inputArray[11].trim() ) );
                    }
                    catch ( final NumberFormatException e ) {
                        System.out.println( "Invalid format" );
                        continue;
                    }
                }
                // Treatment Fee
                if ( !inputArray[12].trim().equals( "*" ) ) {
                    try {
                        m.setTreatmentFee( Float.parseFloat( inputArray[12].trim() ) );
                    }
                    catch ( final NumberFormatException e ) {
                        System.out.println( "Invalid format" );
                        continue;
                    }
                }
                // Specialist Id
                if ( !inputArray[13].trim().equals( "*" ) ) {
                    try {
                        final StaffDB sdb = new StaffDB( conn );
                        final int newid = Integer.parseInt( inputArray[13].trim() );
                        // Make sure patient id exists
                        if ( sdb.getById( newid ) == null ) {
                            System.out.println( "No staff exists with that id" );
                            continue;
                        }
                        else {
                            m.setSpecialistId( newid );
                        }
                    }
                    catch ( final NumberFormatException e ) {
                        System.out.println( "Invalid Id" );
                        continue;
                    }
                }
                // Update
                if ( mdb.update( id, m ) ) {
                    System.out.println( "Medical record successfully updated" );
                }
                else {
                    System.out.println( "Error updating medical record" );
                }
                return;
            }
            else {
                System.out.println( "Invalid Formatting" );
            }
        }
    }

    public void delete ( final Scanner scan ) {
        final MedicalRecordDB mdb = new MedicalRecordDB( conn );
        int id;
        while ( true ) {
            System.out.println( "Enter the id of the medical record entry to delete" );
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
            if ( mdb.getById( id ) != null ) {
                if ( mdb.deleteById( id ) ) {
                    System.out.println( "Medical record entry successfully deleted" );
                }
                else {
                    System.out.println( "Medical record entry could not be deleted" );
                }
                return;
            }
            else {
                System.out.println( "No entry found with that ID" );
            }
        }
    }
}
