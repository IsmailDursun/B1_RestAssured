package io.loopcamp.test.tasks;
import com.fasterxml.jackson.annotation.*;
import io.loopcamp.util.ConfigurationReader;
import lombok.Data;
import org.junit.jupiter.api.BeforeAll;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ZipCode {
    @JsonProperty("post code")
    private String postCode;
    private String country;
    @JsonProperty("country abbreviation")
    private String countryAbbreviation;
    private List<Map<String,Object>> places;

    public abstract static class ZipCodeTestBase {
        @BeforeAll
        //This method will run before all the class/methods. <---JUnit 5   JUnit 4 ---> @BeforeClass
        public static void setUp() {
            baseURI = ConfigurationReader.getProperty("zipcode.api.url");
        }
    }
}
