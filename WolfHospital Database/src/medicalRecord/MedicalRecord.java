package medicalRecord;

import java.sql.Date;

public class MedicalRecord {

    private int    id;
    private int    patientId;
    private Date   startDate;
    private Date   endDate;
    private int    doctorId;
    private String testType;
    private String testResult;
    private String prescription;
    private String diagDetails;
    private String treatment;
    private float  consultFee;
    private float  testFee;
    private float  treatmentFee;
    private int    specialistId;

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
