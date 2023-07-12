package io.loopcamp.util;

import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.baseURI;

public abstract class MinionTestBase {

    @BeforeAll
    public static void setUp(){
        baseURI = ConfigurationReader.getProperty("minion.api.url");
    }
}
