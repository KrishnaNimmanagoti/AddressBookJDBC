import java.sql.*;
import java.sql.Date;
import java.util.*;

public class AddressBookService {
    static Contact[] arrayOfEmployee;
    private final String URL = "jdbc:mysql://localhost:3306/address_book_service?useSSL=false";
    private final String user = "root";
    private final String password = "krishna7609";
    public List<Contact> contacts = new ArrayList<>();
    public static Connection connection;
    public static Statement statement;

    public void getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, user, password);
            statement = connection.createStatement();
        } catch(SQLException | ClassNotFoundException throwables){
                throwables.printStackTrace();
        }
    }

    public List<Contact> retrieveEntriesFromDataBaseForAddressBook(String sql) {
        getConnection();

        try {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String firstName = resultSet.getString("First_Name");
                String lastName = resultSet.getString("Last_Name");
                String address = resultSet.getString("Address");
                String city = resultSet.getString("City");
                String state = resultSet.getString("State");
                int zip = resultSet.getInt("Zip");
                String phone_Number = resultSet.getString("Phone_Number");
                String email = resultSet.getString("Email");
                String addressBook_Name = resultSet.getString("AddressBook_Name");
                String type = resultSet.getString("Type");
                Date date = resultSet.getDate("start");
                contacts.add(new Contact(firstName, lastName, address, city, state, zip, phone_Number, email, addressBook_Name, type, date));
            }
//            contacts.forEach(data -> System.out.println(data.firstName
//                    + " " + data.lastName + " " + data.city + " " + data.state + " " + data.zip + " " + data.phoneNumber + " " + data.email + " " + data.type + " " + data.date));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contacts;
    }

    public String updateAddressBook(String first_name, String phone_number) {
        getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("update address_book set phone_number=? where first_name =?");
            preparedStatement.setString(1, phone_number);
            preparedStatement.setString(2, first_name);
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        retrieveEntriesFromDataBaseForAddressBook("select * from address_book;");
        for (Contact data : contacts) {
            if (data.firstName.equals(first_name))
                return data.phoneNumber;
        }
        return null;
    }

    public String insretContactInAddressBookDatabase(String first_name, String Last_Name, String Address, String City, String State, int Zip, String Phone_Number, String email, String addressBook_Name, String Type, Date start) {
        getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("insert into address_book(first_name,Last_Name,Address,City,State," +
                    "Zip,Phone_Number, Email,AddressBook_Name,Type,start) values(?,?,?,?,?,?,?,?,?,?,?)");

            preparedStatement.setString(1, first_name);
            preparedStatement.setString(2, Last_Name);
            preparedStatement.setString(3, Address);
            preparedStatement.setString(4, City);
            preparedStatement.setString(5, State);
            preparedStatement.setInt(6, Zip);
            preparedStatement.setString(7, Phone_Number);
            preparedStatement.setString(8, email);
            preparedStatement.setString(9, addressBook_Name);
            preparedStatement.setString(10, Type);
            preparedStatement.setDate(11, (java.sql.Date) start);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        retrieveEntriesFromDataBaseForAddressBook("select * from address_book;");
        for (Contact data : contacts) {
            if (data.firstName.equals(first_name))
                return data.firstName;
        }
        return null;
    }

    public void addDetails(List<Contact> contactDataList) {
        contactDataList.forEach(contactData -> {
            System.out.println("Employee being added : " + contactData.firstName);
            this.insretContactInAddressBookDatabase(contactData.firstName, contactData.lastName, contactData.address, contactData.city,
                    contactData.state, contactData.zip, contactData.phoneNumber, contactData.email,contactData.addressBook_Name,
                    contactData.type, (Date) contactData.date);
            System.out.println("Employee added : " + contactData.firstName);
        });
    }

    public void addDetailsWithThreads(List<Contact> contactDataList) {
        Map<Integer, Boolean> conatactAdditionStatus = new HashMap<>();
        contactDataList.forEach(contactData -> {
            Runnable runnable = () -> {
                conatactAdditionStatus.put(contactData.hashCode(), false);
                System.out.println("Employee being added : " + Thread.currentThread().getName());
                this.insretContactInAddressBookDatabase(contactData.firstName, contactData.lastName, contactData.address, contactData.city,
                        contactData.state, contactData.zip, contactData.phoneNumber, contactData.email,contactData.addressBook_Name,
                        contactData.type, (Date) contactData.date);
                conatactAdditionStatus.put(contactData.hashCode(), true);
                System.out.println("Employee added : " + Thread.currentThread().getName());
            };
            Thread thread = new Thread(runnable, contactData.firstName);
            thread.start();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        while (conatactAdditionStatus.containsValue(false)) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
