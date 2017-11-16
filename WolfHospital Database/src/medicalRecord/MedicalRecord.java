package medicalRecord;

import java.sql.Date;

public class MedicalRecord {

	/** The id of the medical record */
    private int    id;
    /** The id of the associated patient */
    private int    patientId;
    /** The start date for the record */
    private Date   startDate;
    /** The end date for the record */
    private Date   endDate;
    /** The id of the responsible doctor */
    private int    doctorId;
    /** The type of test performed */
    private String testType;
    /** The result of the test */
    private String testResult;
    /** The prescription given */
    private String prescription;
    /** Diagnosis details */
    private String diagDetails;
    /** The type of treatment given */
    private String treatment;
    /** Fee for consultation */
    private float  consultFee;
    /** Fee for testing */
    private float  testFee;
    /** Fee for treatment */
    private float  treatmentFee;
    /** The id of the associated specialist */
    private int    specialistId;

    /** Empty constructor */
    public MedicalRecord () {
    }

    /** Constructor with id */
    public MedicalRecord ( final int id, final int patientId, final Date startDate, final Date endDate,
            final int doctorId, final String testType, final String testResult, final String prescription,
            final String diagDetails, final String treatment, final float consultFee, final float testFee,
            final float treatmentFee, final int specialistId ) {
        setId( id );
        setPatientId( patientId );
        setStartDate( startDate );
        setEndDate( endDate );
        setDoctorId( doctorId );
        setTestType( testType );
        setTestResult( testResult );
        setPrescription( prescription );
        setDiagDetails( diagDetails );
        setTreatment( treatment );
        setConsultFee( consultFee );
        setTestFee( testFee );
        setTreatmentFee( treatmentFee );
        setSpecialistId( specialistId );
    }

    /** Constructor without id */
    public MedicalRecord ( final int patientId, final Date startDate, final Date endDate, final int doctorId,
            final String testType, final String testResult, final String prescription, final String diagDetails,
            final String treatment, final float consultFee, final float testFee, final float treatmentFee,
            final int specialistId ) {
        setStartDate( startDate );
        setEndDate( endDate );
        setDoctorId( doctorId );
        setTestType( testType );
        setTestResult( testResult );
        setPrescription( prescription );
        setDiagDetails( diagDetails );
        setTreatment( treatment );
        setConsultFee( consultFee );
        setTestFee( testFee );
        setTreatmentFee( treatmentFee );
        setSpecialistId( specialistId );
    }

    public int getId () {
        return id;
    }

    public void setId ( final int id ) {
        this.id = id;
    }

    public int getPatientId () {
        return patientId;
    }

    public void setPatientId ( final int patientId ) {
        this.patientId = patientId;
    }

    public Date getStartDate () {
        return startDate;
    }

    public void setStartDate ( final Date startDate ) {
        this.startDate = startDate;
    }

    public Date getEndDate () {
        return endDate;
    }

    public void setEndDate ( final Date endDate ) {
        this.endDate = endDate;
    }

    public int getDoctorId () {
        return doctorId;
    }

    public void setDoctorId ( final int doctorId ) {
        this.doctorId = doctorId;
    }

    public String getTestType () {
        return testType;
    }

    public void setTestType ( final String testType ) {
        this.testType = testType;
    }

    public String getTestResult () {
        return testResult;
    }

    public void setTestResult ( final String testResult ) {
        this.testResult = testResult;
    }

    public String getPrescription () {
        return prescription;
    }

    public void setPrescription ( final String prescription ) {
        this.prescription = prescription;
    }

    public String getDiagDetails () {
        return diagDetails;
    }

    public void setDiagDetails ( final String diagDetails ) {
        this.diagDetails = diagDetails;
    }

    public String getTreatment () {
        return treatment;
    }

    public void setTreatment ( final String treatment ) {
        this.treatment = treatment;
    }

    public float getConsultFee () {
        return consultFee;
    }

    public void setConsultFee ( final float consultFee ) {
        this.consultFee = consultFee;
    }

    public float getTestFee () {
        return testFee;
    }

    public void setTestFee ( final float testFee ) {
        this.testFee = testFee;
    }

    public float getTreatmentFee () {
        return treatmentFee;
    }

    public void setTreatmentFee ( final float treatmentFee ) {
        this.treatmentFee = treatmentFee;
    }

    public int getSpecialistId () {
        return specialistId;
    }

    public void setSpecialistId ( final int specialistId ) {
        this.specialistId = specialistId;
    }

}
