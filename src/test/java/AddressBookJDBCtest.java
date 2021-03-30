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
}
