package io.loopcamp.test.day11_b_access_token;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DocuportAccessToken {

            /**
             Given accept type is Json
             And content type is Json
             And body is:
             {
             "usernameOrEmailAddress": "b1g3_client@gmail.com",
             "password": "Group3"
             }
             When I send POST request to
             Url: https://beta.docuport.app/api/v1/authentication/account/authenticate
             Then status code is 200
             And accessToken should be present and not empty
             */

            @Test
            public void docuportLoginTest () {
                // You can store this into a MAP and pass it as part of the Body as well
                String jsonBody = "{\n" +
                        "\"usernameOrEmailAddress\": \"b1g3_client@gmail.com\",\n" +
                        "\"password\": \"Group3\"\n" +
                        "}";

                Response response = given().accept(ContentType.JSON)
                        .and().contentType(ContentType.JSON)
                        // Some companies may pass this username and password as the QUERY PARAM.
                        // It depends how the endpoint was build
                        // and().queryParam("usernameOrEmailAddress", "b1g3_client@gmail.com")
                        // and().queryParam("password", "Group3")
                        .and().body(jsonBody)
                        .when().post("https://beta.docuport.app/api/v1/authentication/account/authenticate");

                //response.prettyPrint();

                System.out.println("Status Code: " + response.statusCode());
                assertThat(response.statusCode(), is(HttpStatus.SC_OK));

                 //Extract the token from response body
                String accessToken = response.path("user.jwtToken.accessToken");
                System.out.println("Access Token: " + accessToken);
                Assertions.assertTrue(accessToken != null && !accessToken.isEmpty());

//                JsonPath jsonPath = response.jsonPath();
//                String accessToken = jsonPath.getString("user.jwtToken.accessToken");
//                System.out.println("Access token: "+accessToken);
//                assertThat(accessToken, not(emptyOrNullString()));
            }
}
