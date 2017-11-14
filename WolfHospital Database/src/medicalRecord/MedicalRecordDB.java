package medicalRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicalRecordDB {

    private final Connection conn;

    public MedicalRecordDB ( final Connection conn ) {
        this.conn = conn;
    }

    public MedicalRecord getById ( final int id ) {
        MedicalRecord s = null;
        try {
            final PreparedStatement stmt = conn.prepareStatement( "SELECT * FROM medical_record WHERE id = ?;" );
            stmt.setInt( 1, id );
            final ResultSet rs = stmt.executeQuery();
            if ( rs.next() ) {
            	s = new MedicalRecord();
                s.setId( id );
                s.setPatientId( rs.getInt( "patient_id" ) );
                s.setStartDate( rs.getDate( "start_date" ) );
                s.setEndDate( rs.getDate( "end_date" ) );
                s.setDoctorId( rs.getInt( "doctor_id" ) );
                s.setTestType( rs.getString( "test_type" ) );
                s.setTestResult( rs.getString( "test_results" ) );
                s.setPrescription( rs.getString( "prescription" ) );
                s.setDiagDetails( rs.getString( "diagnosis_details" ) );
                s.setTreatment( rs.getString( "treatment" ) );
                s.setConsultFee( rs.getFloat( "consultation_fee" ) );
                s.setTestFee( rs.getFloat( "test_fee" ) );
                s.setTreatmentFee( rs.getFloat( "treatment_fee" ) );
                s.setSpecialistId( rs.getInt( "specialist_id" ) );
            }
        }
        catch ( final SQLException e ) {
            // TODO put something here
            e.printStackTrace();
        }
        return s;
    }

    public boolean insert ( final MedicalRecord s ) {
        try {
            final PreparedStatement stmt = conn
                    .prepareStatement( "INSERT INTO medical_record VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);" );
            stmt.setInt( 1, s.getId() );
            stmt.setInt( 2, s.getPatientId() );
            stmt.setDate( 3, s.getStartDate() );
            stmt.setDate( 4, s.getEndDate() );
            stmt.setInt( 5, s.getDoctorId() );
            stmt.setString( 6, s.getTestType() );
            stmt.setString( 7, s.getTestResult() );
            stmt.setString( 8, s.getPrescription() );
            stmt.setString( 9, s.getDiagDetails() );
            stmt.setString( 10, s.getTreatment() );
            stmt.setFloat( 11, s.getConsultFee() );
            stmt.setFloat( 12, s.getTestFee() );
            stmt.setFloat( 13, s.getTreatmentFee() );
            stmt.setInt( 14, s.getSpecialistId() );

            return ( stmt.executeUpdate() > 0 );
        }
        catch ( final SQLException e ) {
            // TODO put something here
            e.printStackTrace();
        }
        return false;
    }

    public boolean update ( final MedicalRecord s ) {
        try {
            final PreparedStatement stmt = conn
                    .prepareStatement( "UPDATE medical_record SET patient_id = ?, start_date = ?, "
                            + "end_date = ?, doctor_id = ?, test_type = ?, test_results = ?, "
                            + "prescription = ?, diagnosis_details = ?, treatment = ?, consultation_fee = ?, "
                            + "test_fee = ?, treatment_fee = ?, specialist_id = ? WHERE id = ?;" );

            stmt.setInt( 1, s.getPatientId() );
            stmt.setDate( 2, s.getStartDate() );
            stmt.setDate( 3, s.getEndDate() );
            stmt.setInt( 4, s.getDoctorId() );
            stmt.setString( 5, s.getTestType() );
            stmt.setString( 6, s.getTestResult() );
            stmt.setString( 7, s.getPrescription() );
            stmt.setString( 8, s.getDiagDetails() );
            stmt.setString( 9, s.getTreatment() );
            stmt.setFloat( 10, s.getConsultFee() );
            stmt.setFloat( 11, s.getTestFee() );
            stmt.setFloat( 12, s.getTreatmentFee() );
            stmt.setInt( 13, s.getSpecialistId() );
            stmt.setInt( 14, s.getId() );
            return ( stmt.executeUpdate() > 0 );
        }
        catch ( final SQLException e ) {
            // TODO put something here
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteById ( final int id ) {
        try {
            final PreparedStatement stmt = conn.prepareStatement( "DELETE FROM medical_record WHERE id = ?;" );
            stmt.setInt( 1, id );
            return ( stmt.executeUpdate() > 0 );
        }
        catch ( final SQLException e ) {
            // TODO put something here
            e.printStackTrace();
        }
        return false;
    }
}
