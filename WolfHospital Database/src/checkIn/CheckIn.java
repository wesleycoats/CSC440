package checkIn;

import java.sql.Date;

public class CheckIn {

	/** The id of the check in entry */
    private int   id;
    /** The id of the associated patient */
    private int   patientId;
    /** The start date of the check in */
    private Date  startDate;
    /** The end date of the check in */
    private Date  endDate;
    /** The id of the assigned ward */
    private int   wardId;
    /** The number of the assigned bed */
    private int   bedNum;
    /** The check in fee */
    private float fee;

    /** Empty constructor */
    public CheckIn () {
    }

    /** Constructor with id */
    public CheckIn ( final int id, final int patientId, final Date startDate, final Date endDate, final int wardId,
            final int bedNum, final float fee ) {
        setId( id );
        setPatientId( patientId );
        setStartDate( startDate );
        setEndDate( endDate );
        setWardId( wardId );
        setBedNum( bedNum );
        setFee( fee );
    }

    /** Constructor without id */
    public CheckIn ( final int patientId, final Date startDate, final Date endDate, final int wardId, final int bedNum,
            final float fee ) {
        setPatientId( patientId );
        setStartDate( startDate );
        setEndDate( endDate );
        setWardId( wardId );
        setBedNum( bedNum );
        setFee( fee );
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

    public int getWardId () {
        return wardId;
    }

    public void setWardId ( final int wardId ) {
        this.wardId = wardId;
    }

    public int getBedNum () {
        return bedNum;
    }

    public void setBedNum ( final int bedNum ) {
        this.bedNum = bedNum;
    }

    public float getFee () {
        return fee;
    }

    public void setFee ( final float fee ) {
        this.fee = fee;
    }

}
