package io.loopcamp.test.day02_a_headers;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.*;

public class MinionApiHelloWorld {
    /**
     When I send a GET request to http://your_ip:8000/api/minions
     Then Response STATUS CODE must be 200
     Then Response body should be equal to “Hello from minion”
     And content type is "text/plain;charset=ISO-8859-1" – This is for headers
     */

    String url = "http://3.89.9.33:8000/api/hello";

    @DisplayName("GET api/hello")
    @Test
    public void helloApiTest () {

        Response response = RestAssured.get(url);

        System.out.println("Status Code: " + response.statusCode());
        assertEquals(200, response.statusCode());

        response.prettyPrint();
        assertEquals("Hello from Minion", response.body().asString());


        //assesrtion below is for response header
        System.out.println("Content Type: " + response.contentType());
        assertEquals("text/plain;charset=ISO-8859-1", response.contentType());
        assertTrue(response.contentType().contains("text/plain"));

    }

    //same test in BDD approach
        public void helloApiBddTest(){
        when().get(url) //now i already have the response
                .then().assertThat().statusCode(200)
                .and().contentType("text/plain;charset=ISO-8859-1");
        }

}
