package staff;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Staff {

    private int    id;
    private String name;
    private int    age;
    private Date   dateOfBirth;
    private String gender;
    private String phoneNumber;
    private String address;
    private String department;
    private String jobTitle;
    private String professionalTitle;

    /** Empty constructor */
    public Staff () {
    }

    /** Constructor with id */
    public Staff ( final int id, final String name, final Date dateOfBirth, final String gender,
            final String phoneNumber, final String address, final String department, final String jobTitle,
            final String professionalTitle ) {
        setId( id );
        setName( name );
        setAge( dateOfBirth );
        setDateOfBirth( dateOfBirth );
        setGender( gender );
        setPhoneNumber( phoneNumber );
        setAddress( address );
        setDepartment( department );
        setJobTitle( jobTitle );
        setProfessionalTitle( professionalTitle );
    }

    /** Constructor without id */
    public Staff ( final String name, final Date dateOfBirth, final String gender, final String phoneNumber,
            final String address, final String department, final String jobTitle, final String professionalTitle ) {
        setName( name );
        setAge( dateOfBirth );
        setDateOfBirth( dateOfBirth );
        setGender( gender );
        setPhoneNumber( phoneNumber );
        setAddress( address );
        setDepartment( department );
        setJobTitle( jobTitle );
        setProfessionalTitle( professionalTitle );
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

    public int getAge () {
        return age;
    }

    public void setAge ( final Date dob ) {
        age = (int) dob.toLocalDate().until( LocalDate.now(), ChronoUnit.YEARS );
        System.out.println( age );
    }

    public Date getDateOfBirth () {
        return dateOfBirth;
    }

    public void setDateOfBirth ( final Date dateOfBirth ) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender () {
        return gender;
    }

    public void setGender ( final String gender ) {
        this.gender = gender;
    }

    public String getPhoneNumber () {
        return phoneNumber;
    }

    public void setPhoneNumber ( final String phoneNumber ) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress () {
        return address;
    }

    public void setAddress ( final String address ) {
        this.address = address;
    }

    public String getDepartment () {
        return department;
    }

    public void setDepartment ( final String department ) {
        this.department = department;
    }

    public String getJobTitle () {
        return jobTitle;
    }

    public void setJobTitle ( final String jobTitle ) {
        this.jobTitle = jobTitle;
    }

    public String getProfessionalTitle () {
        return professionalTitle;
    }

    public void setProfessionalTitle ( final String professionalTitle ) {
        this.professionalTitle = professionalTitle;
    }
}
