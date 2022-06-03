package lesson_3.spoonaccular;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;

// Используем для тестирования библиотек REST ASSURED
public class RecipesTest_2 {

    private static final String API_KEY = "487fe24fd3e846cbbb92723ddd94928d";

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://api.spoonacular.com/";
    }

    @Disabled
    @Test
    @DisplayName("POST")
    void postSpoon() throws IOException {
        given()
                .log()
                .all()
                .param("apiKey", API_KEY)
                .pathParam("username", "dsky")
//                .param("hash", "4b5v4398573406")
                .body(new Gson().toJson("{\n" +
                        "    \"date\": 1589500800,\n" +
                        "    \"slot\": 1,\n" +
                        "    \"position\": 0,\n" +
                        "    \"type\": \"INGREDIENTS\",\n" +
                        "    \"value\": {\n" +
                        "        \"ingredients\": [\n" +
                        "            {\n" +
                        "                \"name\": \"1 banana\"\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    }\n" +
                        "}"))
                .when()
                .post("mealplanner/{username}/items")
                .then()
                .statusCode(200);
    }

    /* чтение файла expected.json в /test/resources/spoonaccular - потому, что сам
    класс "RecipesTest_2" лежит в Package "spoonaccular" */
    public String getResourceAsString(String resource) throws IOException {
        InputStream stream = getClass().getResourceAsStream(resource);
        assert stream != null;
        byte[] bytes = stream.readAllBytes();
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
