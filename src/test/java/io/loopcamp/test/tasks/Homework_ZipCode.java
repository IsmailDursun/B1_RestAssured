package io.loopcamp.test.tasks;

import io.restassured.http.ContentType;
import io.restassured.response.*;
import org.apache.http.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.*;

public class Homework_ZipCode extends ZipCode.ZipCodeTestBase {
/*Given Accept application/json
And path zipcode is 22031
When I send a GET request to /us endpoint
------------
Then status code must be 200
And content type must be application/json
And Server header is cloudflare
And Report-To header exists
And body should contains following information
post code is 22031
country  is United States
country abbreviation is US
place name is Fairfax
state is Virginia
latitude is 38.8604*/
@DisplayName("GET /path with zipcode")
@Test
public void getPathWithZipCode() {
    Response response = given().accept(ContentType.JSON)
            .and().pathParams("country", "US", "zipcode", "22031")
            .when().get("{country}/{zipcode}");
    assertEquals(HttpStatus.SC_OK, response.statusCode());
    assertEquals(ContentType.JSON.toString(), response.contentType());
    assertTrue(response.header("Server").equals("cloudflare"));
    assertTrue(!response.header("Report-To").isEmpty());

    ZipCode zipCode = response.as(ZipCode.class);

    assertEquals("22031",zipCode.getPostCode());
    assertEquals("United States",zipCode.getCountry());
    assertEquals("US",zipCode.getCountryAbbreviation());
    assertEquals("Fairfax",zipCode.getPlaces().get(0).get("place name"));
    assertEquals("Virginia",zipCode.getPlaces().get(0).get("state"));
    assertEquals("38.8604",zipCode.getPlaces().get(0).get("latitude"));
    }

    /*Given Accept application/json
And path zipcode is 50000
When I send a GET request to /us endpoint
------
Then status code must be 404
And content type must be application/json*/
    @DisplayName("GET /path with zipcode negative case")
    @Test
    public void zipCodeNegativeTest(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParams("country", "US", "zipcode", "50000")
                .when().get("{country}/{zipcode}");
        assertEquals(HttpStatus.SC_NOT_FOUND,response.statusCode());
        assertEquals(ContentType.JSON.toString(),response.contentType());
    }






    /*Given Accept application/json
And path state is va
And path city is farifax
When I send a GET request to /us endpoint
------------
Then status code must be 200
And content type must be application/json
And payload should contains following information
    country abbreviation is US
    country  United States
    place name  Fairfax
    each places must contains fairfax as a value
    each post code must start with 22*/
    @DisplayName("GET /path state and city")
    @Test
    public void getPathWithStateCity(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParams("country","us","state","va","city","fairfax")
                .when().get("/{country}/{state}/{city}");
        assertEquals(HttpStatus.SC_OK,response.statusCode());
        assertEquals(ContentType.JSON.toString(),response.contentType());
        ZipCode zipCode = response.as(ZipCode.class);
        assertEquals("US",zipCode.getCountryAbbreviation());
        assertEquals("United States", zipCode.getCountry());
        assertEquals("Fairfax",zipCode.getPlaces().get(0).get("place name"));
        for (int i = 0; i < zipCode.getPlaces().size(); i++) {
            assertTrue(zipCode.getPlaces().get(i).toString().contains("Fairfax"));}
        for (int i = 0; i < zipCode.getPlaces().size(); i++) {
            assertTrue(zipCode.getPlaces().get(i).get("post code").toString().startsWith("22"));}
    }
}
