package io.loopcamp.test.tasks;
import io.loopcamp.util.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class Regions {
    /**
     * given accept is json
     * and content type is json
     * When I send post request to "/regions/"
     * With json:
     * {
     *     "region_id":100,
     *     "region_name":"Test Region"
     * }
     * Then status code is 201
     * content type is json
     * region_id is 100
     * region_name is Test Region
     */
    @DisplayName("POST /regions/")
    @Test
    public void TestRegion (){
        // Prepare the JSON request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("region_id", 100);
        requestBody.put("region_name", "Test Region");

        // Send the POST request and validate the response
        given()
                .accept(ContentType.JSON).and()
                .contentType(ContentType.JSON).and()
                .body(requestBody)
                .when()
                .post("/regions/")
                .then()
                .statusCode(201).and()
                .contentType(ContentType.JSON).and()
                .assertThat().body("region_id", equalTo(100)).and()
                .assertThat().body("region_name", equalTo("Test Region"));
    }
    @DisplayName("GET /regions/100")
    @Test
    public void testGetRegionById() {
        // Send the GET request and validate the response
        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .get("/regions/100");

        response.then()
                .statusCode(200).and()
                .contentType(ContentType.JSON).and()
                .body("region_id", equalTo(100)).and()
                .body("region_name", equalTo("Test Region"));

        response.prettyPrint();
        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());
    }
    /**
     * Given accept type is Json
     * And content type is json
     * When i send PUT request to /regions/100
     * With json body:
     *    {
     *      "region_id": 100,
     *      "region_name": "Puzzle Region"
     *    }
     * Then status code is 200
     * And content type is json
     * region_id is 100
     * region_name is Puzzle Region
     */

    /**
     * Given accept type is Json
     * When i send DELETE request to /regions/100
     * Then status code is 200
     */

    @DisplayName("PUT /regions/100")
    @Test
    public void testUpdateRegion() {
        // Prepare the JSON request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("region_id", 100);
        requestBody.put("region_name", "Puzzle Region");
        // Send the PUT request and validate the response
        given()
                .accept(ContentType.JSON).and()
                .contentType(ContentType.JSON).and()
                .body(requestBody)
                .when()
                .put("/regions/100")
                .then()
                .statusCode(200).and()
                .contentType(ContentType.JSON).and()
                .assertThat().body("region_id", equalTo(100)).and()
                .assertThat().body("region_name", equalTo("Puzzle Region"));
    }
    @DisplayName("DELETE /regions/100")
    @Test
    public void testDeleteRegion() {
        // Send the DELETE request and validate the response
        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .delete("/regions/100");
        assertEquals(HttpStatus.SC_OK, response.statusCode());
    }
    @DisplayName("POST /regions/ and Validate Using DB")
    @Test
    public void testCreateRegionAndDatabaseValidations() {

        Map<String, Object> jsonbody3 = new HashMap<>();
        jsonbody3.put("region_id", 200);
        jsonbody3.put("region_name", "Test Region");

        given()
                .accept(ContentType.JSON).and()
                .contentType(ContentType.JSON).and()
                .body(jsonbody3)
                .when()
                .post("/regions/")
                .then()
                .statusCode(201) // HTTP status code 201 - Created
                .contentType(ContentType.JSON).and()
                .assertThat().body("region_id", equalTo(200)).and()
                .assertThat().body("region_name", equalTo("Test Region"));

        Connection connection = null;

        try {
            String query = "SELECT region_id, region_name FROM regions WHERE region_id = 200";
            String dbURl = "jdbc:oracle:thin:@34.207.85.246:1521:XE";
            String dbUserName = "hr";
            String dbPassword = "hr";
            Connection conn = DriverManager.getConnection(dbURl, dbUserName, dbPassword);
            Statement stmtn = conn.createStatement();
            ResultSet rs = stmtn.executeQuery(query);

            if (rs.next()) {
                String regionNameFromDatabase = rs.getString("region_name");
                assertEquals("Test Region", regionNameFromDatabase);
            } //else {
            //System.out.println("Region with ID 200 was not found in the database");
            // }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public abstract static class ZipCodeTestBase {
        @BeforeAll
        //This method will run before all the class/methods. <---JUnit 5   JUnit 4 ---> @BeforeClass
        public static void setUp() {
            baseURI = ConfigurationReader.getProperty("hr.api.url");
        }
    }
}
