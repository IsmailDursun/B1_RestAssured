package io.loopcamp.test.day09_b_put_patch_request;
import io.loopcamp.util.MinionRestUtil;
import io.loopcamp.util.MinionTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
public class MinionPatchRequestTest extends MinionTestBase {
    /**
     PATCH /api/minions/{id}
     Partially Update A Minion
     Given accept type is json
     And content type is json
     And path param id is 15
     And json body is
     {
     "phone": 1234567425
     }
     When i send PATCH request to /minions/{id}
     Then status code 204
     */
    @DisplayName("PATCH /minion/{id}")
    @Test
    public void patchMinionTest(){
        Map <String, Object> patchMap = new HashMap<>();
        patchMap.put("phone",1234567425);
        int minID = 15;

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(patchMap)
                .and().pathParam("id",minID)
                .when().patch("/minions/{id}");
    //          .then().assertThat(statusCode(is(204)));   // if want to assert without assigning to Response response.

        response.prettyPrint();
        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Status Line: " + response.statusLine());
        assertThat(response.statusCode(), is(204));

        // to get the updated minion with ID
        Map <String, Object> minion = MinionRestUtil.getMinionInMap(minID);
        System.out.println(minID + " Minion info: " + minion);

        assertThat(minion.get("phone"), equalTo(patchMap.get("phone").toString()));
    }
}
