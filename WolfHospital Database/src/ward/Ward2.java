package ward;

public class Ward2 {

    private int   id;
    private int   wardNum;
    private int   capacity;
    private float charge;
    private int   openBeds;
    private int   nurseId;

    public int getId () {
        return id;
    }

    public void setId ( final int id ) {
        this.id = id;
    }

    public int getWardNum () {
        return wardNum;
    }

    public void setWardNum ( final int wardNum ) {
        this.wardNum = wardNum;
    }

    public int getCapacity () {
        return capacity;
    }

    public void setCapacity ( final int capacity ) {
        this.capacity = capacity;
    }

    public float getCharge () {
        return charge;
    }

    public void setCharge ( final float charge ) {
        this.charge = charge;
    }

    public int getOpenBeds () {
        return openBeds;
    }

    public void setOpenBeds ( final int openBeds ) {
        this.openBeds = openBeds;
    }

    public int getNurseId () {
        return nurseId;
    }

    public void setNurseId ( final int nurseId ) {
        this.nurseId = nurseId;
    }

}
