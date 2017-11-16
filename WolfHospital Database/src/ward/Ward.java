package ward;

public class Ward {

	/** The ward's number */
    private int   wardNum;
    /** Whether the ward is a one bed ward */
    private int   capacity1;
    /** Whether the ward is a two bed ward */
    private int   capacity2;
    /** Whether the ward is a three bed ward */
    private int   capacity3;
    /** The price per day of the ward */
    private float charge;
    /** The id of the assigned nurse */
    private int   nurseId;

    /** Empty constructor */
    public Ward () {
    }

    /** Constructor with id */
    public Ward ( final int wardNum, final int capacity1, final int capacity2, final int capacity3, final float charge,
            final int nurseId ) {
        setWardNum( wardNum );
        setCapacity1( capacity1 );
        setCapacity2( capacity2 );
        setCapacity3( capacity3 );
        setCharge( charge );
        setNurseId( nurseId );
    }

    /** Constructor without id */
    public Ward ( final int capacity1, final int capacity2, final int capacity3, final float charge,
            final int nurseId ) {
        setCapacity1( capacity1 );
        setCapacity2( capacity2 );
        setCapacity3( capacity3 );
        setCharge( charge );
        setNurseId( nurseId );
    }

    public int getWardNum () {
        return wardNum;
    }

    public void setWardNum ( final int wardNum ) {
        this.wardNum = wardNum;
    }

    public float getCharge () {
        return charge;
    }

    public void setCharge ( final float charge ) {
        this.charge = charge;
    }

    public int getNurseId () {
        return nurseId;
    }

    public void setNurseId ( final int nurseId ) {
        this.nurseId = nurseId;
    }

    public int getCapacity1 () {
        return capacity1;
    }

    public void setCapacity1 ( final int capacity1 ) {
        this.capacity1 = capacity1;
    }

    public int getCapacity2 () {
        return capacity2;
    }

    public void setCapacity2 ( final int capacity2 ) {
        this.capacity2 = capacity2;
    }

    public int getCapacity3 () {
        return capacity3;
    }

    public void setCapacity3 ( final int capacity3 ) {
        this.capacity3 = capacity3;
    }

}
