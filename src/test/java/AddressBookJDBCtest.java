import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
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
    public void retrieveEntryByStateOrCityTest() {
        AddressBookService addressBookService = new AddressBookService();
        AddressBookService addressBookService2 = new AddressBookService();
        String sql1 = "select * from address_book where state = 'Maharashtra';";
        String sql2 = "select * from address_book where city = 'Hyderabad';";
        List<Contact> dataByState =addressBookService.retrieveEntriesFromDataBaseForAddressBook(sql1);
        List<Contact> dataByCity = addressBookService2.retrieveEntriesFromDataBaseForAddressBook(sql2);
        Assertions.assertEquals(1, dataByState.size());
        Assertions.assertEquals(3, dataByCity.size());
    }

    @Test
    public void insretContactInAddressBookDatabaseTest() {
        AddressBookService addressBookService = new AddressBookService();
        String first_name = "Haji";
        String updatedname = addressBookService.insretContactInAddressBookDatabase(first_name,"Shaik", "Eduloor", "Hyderabad", "Delhi",
                3333, "96409343","haji@123.gmail.com", "FellowShip","Friend", Date.valueOf("2021-01-01"));
        Assertions.assertEquals(first_name,updatedname);
    }

    @Test
    public void givenContacts_WhenAddedToDB_ShouldMatchEmployeeEntries() {
        Contact[] arrayOfContact = {
                new Contact("Sudhakar", "Dharmik", "ModiNo6", "Nagpur", "Maharashtra", 440016, "9865430031",
                        "rajeshd@gmail.com", "Profession","Colleague", Date.valueOf("2021-03-25")),
                new Contact("Ravi", "Ghosh", "PimpleGurav", "Pune", "Maharashtra", 411055, "9865854331",
                        "ghoshalshreya@gmail.com", "Friend","neighbour", Date.valueOf("2021-03-28"))};
        AddressBookService addressBookService = new AddressBookService();
        Instant start = Instant.now();
        addressBookService.addDetails(Arrays.asList(arrayOfContact));
        Instant end = Instant.now();
        System.out.println("Duration without thread : " + Duration.between(start, end));
        Instant threadStart = Instant.now();
        addressBookService.addDetailsWithThreads(Arrays.asList(arrayOfContact));
        Instant threadEnd = Instant.now();
        System.out.println("Duration with Thread : " + Duration.between(threadStart, threadEnd));
    }
}
