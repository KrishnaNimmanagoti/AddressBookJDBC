import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddressBookJSONTest {
    @Test
    public void testToRetrieveAllAddressBookData() {
        Response response = RestAssured.get("http://localhost:3000/addressBook");
        System.out.println(response.asString());
        Assertions.assertEquals(200, response.getStatusCode());
    }

    @Test
    public void testToContactDataInJSONServer(){
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"firstName\": \"Sai\",\"lastName\": \"Guni\",\"address\": \"Hudkeshwar\",\"city\": \"ladan\",\"state\": \"UttarPradesh\",\"zip\": \"440034\",\"phoneNumber\": \"8877445588\",\"email\": \"anisha@gmail.com\",\"personType\": \"Friend\"}")
                .when().post("http://localhost:3000/addressbook");
        response.then()
                .body("firstName", Matchers.is("Sai"));
        Assertions.assertEquals(201, response.getStatusCode());
    }

    @Test
    public void testToUpdateContactDataInJSONServer(){
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"firstName\": \"srinivas\",\"lastName\": \"Nimmanagoti\",\"address\": \"kattangur\",\"city\": \"Nalgonda\",\"state\": \"AndhraPradesh\",\"zip\": \"508205\",\"phoneNumber\": \"9133266613\",\"email\": \"srinivas@gmail.com\",\"personType\": \"Friend\"}")
                .when().put("http://localhost:3000/addressbook/4");
        response.then()
                .body("address", Matchers.is("kattangur"));
        Assertions.assertEquals(200, response.getStatusCode());
    }

    @Test
    public void testToDeleteContactDataInJSONServer(){
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when().delete("http://localhost:3000/addressbook/7");
        Assertions.assertEquals(200, response.getStatusCode());
    }
}
