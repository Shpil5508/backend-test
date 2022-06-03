package lesson_4;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import org.junit.jupiter.api.BeforeAll;

public class SpoonaccularTest extends AbstractTest{
    private static final String API_KEY = "487fe24fd3e846cbbb92723ddd94928d";

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://api.spoonacular.com/";

        RestAssured.requestSpecification = new RequestSpecBuilder()
//                .setContentType(ContentType.JSON)
                .addQueryParam("apiKey", API_KEY)
                .log(LogDetail.ALL)
                .build();

        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();
    }
}
