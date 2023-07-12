package io.loopcamp.test.day01_Intro;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class HelloWorldApiTest {
    String url = "https://sandbox.api.service.nhs.uk/hello-world/hello/world";

    @DisplayName("Hello World GET request")   //displays the test name on run console
    @Test
    public void helloWorldGetRequestTest(){
        //send a GET request and save response inside the Response object
        Response response = RestAssured.get(url);
//      printing response
        response.prettyPrint();
//        System.out.println(response.body().asString());
//        System.out.println(response.asString());

        //how to validate status code
        System.out.println("response.statusCode() = " + response.statusCode());
        System.out.println("response.statusLine() = " + response.statusLine());

        //assert status code is 200
        Assertions.assertTrue(response.statusCode()==200);
        Assertions.assertEquals(200,response.statusCode());

//      Assert that the hello world is in the response
        Assertions.assertTrue(response.asString().contains("Hello World!"));
//        Assertions.assertTrue(response.prettyPrint().contains("Hello World"));

    }

}