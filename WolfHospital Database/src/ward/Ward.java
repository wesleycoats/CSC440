package ward;

public class Ward {

    private int   wardNum;
    private int   capacity1;
    private int   capacity2;
    private int   capacity3;
    private float charge;
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
