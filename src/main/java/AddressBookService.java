import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddressBookService {
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
        } catch (SQLException | ClassNotFoundException throwables) {
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
            contacts.forEach(data -> System.out.println(data.firstName
                    + " " + data.lastName + " " + data.city + " " + data.state + " " + data.zip + " " + data.phoneNumber + " " + data.email + " " + data.type + " " + data.date));

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

    public String insretContactInAddressBookDatabase(String first_name, String Last_Name, String Address, String City, String State, int Zip, String email, String addressBook_Name, String Type, Date start) {
        getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("insert into address_book(first_name,Last_Name,Address,City,State," +
                    "Zip,Email,AddressBook_Name,Type,start) values(?,?,?,?,?,?,?,?,?,?)");

            preparedStatement.setString(1, first_name);
            preparedStatement.setString(2, Last_Name);
            preparedStatement.setString(3, Address);
            preparedStatement.setString(4, City);
            preparedStatement.setString(5, State);
            preparedStatement.setInt(6, Zip);
            preparedStatement.setString(7, email);
            preparedStatement.setString(8, addressBook_Name);
            preparedStatement.setString(9, Type);
            preparedStatement.setDate(10, (java.sql.Date) start);
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
}
