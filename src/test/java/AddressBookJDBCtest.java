import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AddressBookJDBCtest {
    @Test
    public void retrieveEntriesFromDataBaseForAddressBookTest() {
        AddressBookService addressBookService = new AddressBookService();
        List<Contact> data = addressBookService.retrieveEntriesFromDataBaseForAddressBook();
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
}
