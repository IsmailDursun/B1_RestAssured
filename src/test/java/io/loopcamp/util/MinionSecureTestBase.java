package io.loopcamp.util;

import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.baseURI;

public abstract class MinionSecureTestBase {
    // I want this method to run before all the class/methods.- JUnit 5 ------ > JUnit 4 ----- > @BeforeClass
    @BeforeAll
    public static void setUp(){
        baseURI = ConfigurationReader.getProperty("minion.secure.api.url");
    }

}
