import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AddressBookJDBCtest {
    @Test
    public void retrieveEntriesFromDataBaseForAddressBookTest() {
        AddressBookService addressBookService = new AddressBookService();
        List<Contact> data = addressBookService.retrieveEntriesFromDataBaseForAddressBook("select * from address_book;");
        Assertions.assertEquals(4, data.size());

    }

    @Test
    public void updateAddressBookTest() {
        AddressBookService addressBookService = new AddressBookService();
        String first_name = "Srinivas";
        String phone_number = "123456789";
        String phone_numberUpdated = addressBookService.updateAddressBook(first_name, phone_number);
        Assertions.assertEquals(phone_number, phone_numberUpdated);
    }

    @Test
    public void retriveEntryByDateTest() {
        AddressBookService addressBookService = new AddressBookService();
        String sql = "SELECT * FROM address_book WHERE start BETWEEN CAST('2016,-01-01' AS DATE) AND DATE(NOW());";
        List<Contact> data = addressBookService.retrieveEntriesFromDataBaseForAddressBook(sql);
        Assertions.assertEquals(2, data.size());
    }

    @Test
    public void retriveEntryByStateOrCityTest() {
        AddressBookService addressBookService = new AddressBookService();
        AddressBookService addressBookService2 = new AddressBookService();
        String sql1 = "select * from address_book where state = 'Maharashtra';";
        String sql2 = "select * from address_book where city = 'Hyderabad';";
        List<Contact> dataByState =addressBookService.retrieveEntriesFromDataBaseForAddressBook(sql1);
        List<Contact> dataByCity = addressBookService2.retrieveEntriesFromDataBaseForAddressBook(sql2);
        Assertions.assertEquals(1, dataByState.size());
        Assertions.assertEquals(3, dataByCity.size());
    }
}
