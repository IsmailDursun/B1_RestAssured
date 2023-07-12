package io.loopcamp.test.day03_json_path;

import io.loopcamp.util.ConfigurationReader;
import io.restassured.http.*;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class day03_tasks {
    @BeforeEach
    public void setUp() {
        baseURI = ConfigurationReader.getProperty("typicode.url");
    }
    /*    Q1:
- Given accept type is Json
- When user sends request to https://jsonplaceholder.typicode.com/posts
- Then print response body
- And status code is 200
- And Content - Type is Json*/
    @DisplayName("First step")
    @Test
    public void task1(){
        Response response = given().accept(ContentType.JSON)
                .when().get("/posts");
        response.prettyPrint();
        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType().substring(0,response.contentType().indexOf(";")));
    }
    /*Q2:
- Given accept type is Json
- Path param "id" value is 1
- When user sends request to https://jsonplaceholder.typicode.com/posts/{id}
- Then status code is 200
- And json body contains "repellat provident"
- And header Content - Type is Json
- And header "X-Powered-By" value is "Express"
- And header "X-Ratelimit-Limit" value is 1000
- And header "Age" value is more than 100
- And header "NEL" value contains "success_fraction"*/
    @DisplayName("Second step")
    @Test
    public void task2(){
        int id = 1;
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id",id)
                .when().get("/posts/{id}");
        response.prettyPrint();
        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertTrue(response.asString().contains("repellat provident"));
        assertEquals(ContentType.JSON.toString(), response.contentType());
        assertEquals("Express", response.getHeader("X-Powered-By"));
        assertEquals("1000", response.getHeader("X-Ratelimit-Limit"));
        assertTrue(Integer.valueOf(response.getHeader("Age"))>100);
        assertTrue(response.getHeader("NEL").contains("success_fraction"));
    }

    /*Q3:
- Given accept type is Json
- Path param "id" value is 12345
- When user sends request to  https://jsonplaceholder.typicode.com/posts/{id}
- Then status code is 404
- And json body contains "{}"*/
    @DisplayName("Third step")
    @Test
    public void task3(){
        int id = 12345;
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id",id)
                .when().get("/posts/{id}");
        assertEquals(HttpStatus.SC_NOT_FOUND, response.statusCode());
        assertTrue(response.body().asString().contains("{}"));
    }
    /*Q4:
- Given accept type is Json
- Path param "id" value is 2
- When user sends request to  https://jsonplaceholder.typicode.com/posts/{id}/comments
- Then status code is 200

- And header Content - Type is Json
- And json body contains "Presley.Mueller@myrl.com",  "Dallas@ole.me" , "Mallory_Kunze@marie.org"*/
    @DisplayName("Fourth step")
    @Test
    public void task4(){
        int id = 2;
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id",id)
                .when().get("/posts/{id}/comments");
        response.prettyPrint();
        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType().substring(0,response.contentType().indexOf(";")));
        assertTrue(response.body().toString().contains("Presley.Mueller@myrl.com"));
        assertTrue(response.body().toString().contains("Dallas@ole.me"));
        assertTrue(response.body().toString().contains("Mallory_Kunze@marie.org"));





    }
}
