package staff;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Staff {

	/** The id of the staff entry */
    private int    id;
    /** The name of the staff member */
    private String name;
    /** The age of the staff member */
    private int    age;
    /** The date of birth of the staff member */
    private Date   dateOfBirth;
    /** The gender of the staff member */
    private String gender;
    /** The phone number of the staff member */
    private String phoneNumber;
    /** The address of the staff member */
    private String address;
    /** The department the staff member belongs to */
    private String department;
    /** The staff member's job title */
    private String jobTitle;
    /** The staff member's professional title */
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

    @Override
    public boolean equals(Object object) {
        boolean sameSame = false;

        if (object != null && object instanceof Staff)
        {
            sameSame = this.id == ((Staff) object).id;
        }

        return sameSame;
    }
}
