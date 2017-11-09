package billingAccount;

import java.sql.Date;

public class BillingAccount {

    private int    id;
    private int    checkinId;
    private Date   date;
    private String payerSsn;
    private String pmtType;
    private String address;

    public int getId () {
        return id;
    }

    public void setId ( final int id ) {
        this.id = id;
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

}
