package io.loopcamp.test.day09_b_put_patch_request;
import io.loopcamp.util.MinionTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
    public class MinionPutRequestTest extends MinionTestBase {
        /**
         Given accept type is json
         And content type is json
         And path param id is 15
         And json body is
         {
         "gender": "Male",
         "name": "PutRequest",
         "phone": 1234567425
         }
         When i send PUT request to /minions/{id}
         Then status code 204
         */
        @DisplayName("PUT /minions/{id}")
        @Test
        public void updateMinionTest() {
            Map <String, Object> reqMap = new HashMap<>();
            reqMap.put("gender", "Male");
            reqMap.put("name", "PutRequest");
            reqMap.put("phone", 1234567425);

            Response response = given().accept(ContentType.JSON)
                    .and().contentType(ContentType.JSON)
                    .and().body(reqMap)
                    .and().pathParam("id", 133)  // Since we are updating already existing Minion, I need to provide id
                    .when().put("/minions/{id}");

            response.prettyPrint();
            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Status Line: " + response.statusLine());
            assertThat(response.statusCode(), is(204));
        }
}
