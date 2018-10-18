package erpsystem;

/**
 *
 * @author Lupe
 */
public class Employee
{
    // vars for constructor
    private String name;
    private String address;
    private String email;
    private String dob;
    private String gender;
    private String ethnicity;
    private int age;

    // constructor to create employee object
    public Employee(String name, String address, String email, String dob, String gender, String ethnicity, int age)
    {
        this.name = name;
        this.address = address;
        this.email = email;
        this.dob = dob;
        this.gender = gender;
        this.ethnicity = ethnicity;
        this.age = age;
    }


    // methods for getting
    public String getName()
    {
        return this.name;
    }

    public String getAddress()
    {
        return this.address;
    }

    public String getEmail()
    {
        return this.email;
    }

     public String getDOB()
    {
        return this.dob;
    }

    public String getGender()
    {
        return this.gender;
    }

    public String getEthnicity()
    {
        return this.ethnicity;
    }

    public int getAge()
    {
        return this.age;
    }

    // methods for setting
    public void setName(String name)
    {
        this.name = name;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

     public void setDOB(String dob)
    {
        this.dob = dob;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public void setEthnicity(String ethnicity)
    {
       this.ethnicity = ethnicity;
    }

    public void setAge(int age)
    {
       this.age = age;
    }

    // Easy to read print method for Employee so that we can print it from elsewjere
    public String printEmployee()
    {
        return(
                "Name: " + getName() + ", " +
                "Address: " + getAddress() + ", " +
                "Email: " + getEmail() + ", " +
                "DOB: " + getDOB() + ", " +
                "Gender: " + getGender() + ", " +
                "Ethnicity: " + getEthnicity() + ", " +
                "Age: " + getAge() + "."
        );
    }

}
