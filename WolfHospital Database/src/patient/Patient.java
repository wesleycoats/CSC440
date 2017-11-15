package patient;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Patient {

    private int    id;
    private String name;
    private String ssn;
    private int    age;
    private Date   dob;
    private String gender;
    private String phone;
    private String address;
    private String status;

    /** Empty constructor */
    public Patient () {
    }

    /** Constructor with id */
    public Patient ( final int id, final String name, final String ssn, final Date dob, final String gender,
            final String phone, final String address, final String status ) {
        setId( id );
        setName( name );
        setSsn( ssn );
        setAge( dob );
        setDob( dob );
        setGender( gender );
        setPhone( phone );
        setAddress( address );
        setStatus( status );
    }

    /** Constructor without id */
    public Patient ( final String name, final String ssn, final Date dob, final String gender, final String phone,
            final String address, final String status ) {
        setName( name );
        setSsn( ssn );
        setAge( dob );
        setDob( dob );
        setGender( gender );
        setPhone( phone );
        setAddress( address );
        setStatus( status );
    }

    public int getId () {
        return id;
    }

    public void setId ( final int id ) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName ( final String name ) {
        this.name = name;
    }

    public String getSsn () {
        return ssn;
    }

    public void setSsn ( final String ssn ) {
        this.ssn = ssn;
    }

    public int getAge () {
        return age;
    }

    public void setAge ( final Date dob ) {
        age = (int) dob.toLocalDate().until( LocalDate.now(), ChronoUnit.YEARS );
    }

    public Date getDob () {
        return dob;
    }

    public void setDob ( final Date dob ) {
        this.dob = dob;
    }

    public String getGender () {
        return gender;
    }

    public void setGender ( final String gender ) {
        this.gender = gender;
    }

    public String getPhone () {
        return phone;
    }

    public void setPhone ( final String phone ) {
        this.phone = phone;
    }

    public String getAddress () {
        return address;
    }

    public void setAddress ( final String address ) {
        this.address = address;
    }

    public String getStatus () {
        return status;
    }

    public void setStatus ( final String status ) {
        this.status = status;
    }

}
