import java.util.Date;

public class Contact {
    public String addressBook_Name;
    public String firstName;
    public String lastName;
    public String address;
    public String city;
    public String state;
    public int zip;
    public String phoneNumber;
    public String email;
    public String type;
    public Date date;

    public Contact(String firstName, String lastName, String address, String city, String state, int zip, String phone_number, String email, String addressBook_Name,String type, Date date) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phone_number;
        this.email = email;
        this.addressBook_Name = addressBook_Name;
        this.type = type;
        this.date = date;

    }
}