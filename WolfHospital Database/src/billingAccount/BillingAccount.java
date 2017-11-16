package billingAccount;

import java.sql.Date;

public class BillingAccount {

	/** Id of the account */
    private int    id;
    /** Id of the patient associated with the account */
    private int    patientId;
    /** Id of the check in associated with the account */
    private int    checkinId;
    /** The date the account was created */
    private Date   date;
    /** The SSN of the payer on the account */
    private String payerSsn;
    /** The type of payment used */
    private String pmtType;
    /** The billing address of the payer */
    private String address;
    /** Default consulting fee */
    private float consulting_fee = 0;
    /** Default treatment fee */
    private float treatment_fee = 0;
    /** Default test fee */
    private float test_fee = 0;
    private float registration_fee = 0;
    private float accomadation_fee = 0;

    /** Empty constructor */
    public BillingAccount () {
    }

    /** Constructor with id */
    public BillingAccount ( final int id, final int patientId, final int checkinId, final Date date,
            final String payerSsn, final String pmtType, final String address ) {
        setId( id );
        setPatientId( patientId );
        setCheckinId( checkinId );
        setDate( date );
        setPayerSsn( payerSsn );
        setPmtType( pmtType );
        setAddress( address );
    }

    /** Constructor without id */
    public BillingAccount ( final int checkinId, final int patientId, final Date date, final String payerSsn,
            final String pmtType, final String address ) {
        setPatientId( patientId );
        setCheckinId( checkinId );
        setDate( date );
        setPayerSsn( payerSsn );
        setPmtType( pmtType );
        setAddress( address );
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

    public int getCheckinId () {
        return checkinId;
    }

    public void setCheckinId ( final int checkinId ) {
        this.checkinId = checkinId;
    }

    public Date getDate () {
        return date;
    }

    public void setDate ( final Date date ) {
        this.date = date;
    }

    public String getPayerSsn () {
        return payerSsn;
    }

    public void setPayerSsn ( final String payerSsn ) {
        this.payerSsn = payerSsn;
    }

    public String getPmtType () {
        return pmtType;
    }

    public void setPmtType ( final String pmtType ) {
        this.pmtType = pmtType;
    }

    public String getAddress () {
        return address;
    }

    public void setAddress ( final String address ) {
        this.address = address;
    }

    public float getConsultingFee() {
        return consulting_fee;
    }

    public void setConsultingFee(float consulting_fee) {
        this.consulting_fee = consulting_fee;
    }

    public float getTestFee() {
        return test_fee;
    }

    public void setTestFee(float test_fee) {
        this.test_fee = test_fee;
    }

    public float getTreatmentFee() {
        return treatment_fee;
    }

    public void setTreatmentFee(float treatment_fee) {
        this.treatment_fee = treatment_fee;
    }

    public float getRegistrationFee() {
        return registration_fee;
    }

    public void setRegistrationFee(float registration_fee) {
        this.registration_fee = registration_fee;
    }

    public float getAccomadationFee() {
        return accomadation_fee;
    }

    public void setAccomadationFee(float accomadation_fee) {
        this.accomadation_fee = accomadation_fee;
    }
}
