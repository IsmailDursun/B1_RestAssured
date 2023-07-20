package io.loopcamp.test.tasks;


import io.loopcamp.util.HRApiTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.poi.ss.formula.functions.Count;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.annotation.meta.When;
import javax.lang.model.element.Element;
import javax.management.Query;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Homework_ORDS_API extends HRApiTestBase {

/* Q1:
            - Given accept type is Json
            - Path param value- US
            - When users sends request to /countries
            - Then status code is 200
                        - And Content - Type is Json
            - And country_id is US
            - And Country_name is United States of America
            - And Region_id is 10  */

    @DisplayName("GET /countries")
    @Test
    public void ordsApiQ1(){
    Response response = given().accept(ContentType.JSON)
            .and().pathParam("country_id","US")
            .when().get("/countries/{country_id}");
    assertEquals(HttpStatus.SC_OK,response.statusCode());
    assertEquals(ContentType.JSON.toString(),response.contentType());
    assertEquals("US",response.jsonPath().getString("country_id"));
    assertEquals("United States of America",response.jsonPath().getString("country_name"));
    assertEquals(10,response.jsonPath().getInt("region_id"));
}
    /*        Q2:
            - Given accept type is Json
            - Query param value - q={"department_id":80}
            - When user sends request to /employees
            - Then status code is 200
            - And Content - Type is Json
            - And all job_ids starts with 'SA'
            - And all department_ids are 80
            - Count is 25     */
    @DisplayName("GET /employees")
    @Test
    public void ordsApiQ2(){
    Response response = given().accept(ContentType.JSON)
                .and().queryParam("q","{\"department_id\":80}")
                .when().get("/employees");
    assertEquals(HttpStatus.SC_OK,response.statusCode());
    assertEquals(ContentType.JSON.toString(),response.contentType());
    JsonPath jsonPath = response.jsonPath();
    List <String> jobIdList = jsonPath.getList("items.findAll{it.department_id==80}.job_id");
    jobIdList.forEach(p->{assertTrue(p.startsWith("SA"));});
    List<Integer>depIdList = jsonPath.getList("items.department_id");
    depIdList.forEach(p->{assertTrue(p.equals(80));});
    assertEquals(25,jobIdList.size());}
/*          Q3:
            - Given accept type is Json
            -Query param value q= region_id 30
            - When users sends request to /countries
            - Then status code is 200
            - And all regions_id is 30
            - And count is 6
            - And hasMore is false
            - And Country_names are;
                Australia,China,India,Japan,Malaysia,Singapore      */
    @DisplayName("GET /region")
    @Test
    public void ordsApiQ3(){
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"region_id\":30}")
                .when().get("countries");
        assertEquals(HttpStatus.SC_OK,response.statusCode());
        assertEquals(ContentType.JSON.toString(),response.contentType());
        JsonPath jsonPath = response.jsonPath();
        jsonPath.getList("items.region_id").forEach(p->{assertTrue(p.equals(30));});
        assertEquals(6,jsonPath.getInt("count"));
        assertEquals(false,jsonPath.getBoolean("hasMore"));
        List<String> countries30 = jsonPath.getList("items.findAll{it.region_id==30}.country_name");
        System.out.println(countries30);


    }
}
