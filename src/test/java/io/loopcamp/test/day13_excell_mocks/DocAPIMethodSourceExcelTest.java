package io.loopcamp.test.day13_excell_mocks;

import io.loopcamp.util.DocuportApiTestBase;
import io.loopcamp.util.ExcellUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;

public class DocAPIMethodSourceExcelTest extends DocuportApiTestBase {

    @DisplayName("GET /sign - data from DocQa3.xlsx")
    @ParameterizedTest
    @MethodSource ("getUserCredentials")
    public void docuportAuthDDTTest (Map<String, String> data) {
        System.out.println("User Info: " + data);
        System.out.println(data.get("email"));
        System.out.println(data.get("password"));
        String accTok = getAccessToken(data.get("email"), data.get("password"));
        System.out.println(accTok);
    }

    public static List <Map<String, String>> getUserCredentials () {
        String path = "src/test/resources/DocQa3.xlsx";
        ExcellUtil excellUtil =new ExcellUtil(path,"QA2");
        List<Map<String, String>>data = excellUtil.getDataList();
        return data;
    }
}
