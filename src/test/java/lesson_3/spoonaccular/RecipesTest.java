package lesson_3.spoonaccular;

import io.restassured.RestAssured;
import lesson_3.EquipmentItem;
import lesson_3.EquipmentResponse;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;
import static net.javacrumbs.jsonunit.core.Option.IGNORING_ARRAY_ORDER;
import static org.hamcrest.Matchers.*;

// Используем для тестирования библиотеку REST ASSURED
public class RecipesTest {
    private static final String API_KEY = "487fe24fd3e846cbbb92723ddd94928d";

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://api.spoonacular.com/recipes/";
    }

    @Disabled
    @Test
    @DisplayName("Test Autocomplete search")
    void testAutocompleteSearch() throws IOException {
        String actually = given() // Request (ЗАПРОС)
                .log()
                .all()
                .param("apiKey", API_KEY)
                .queryParam("query", "cheese")
                .queryParam("number", 10)
                .expect()   //Response (ОТВЕТ)
                .when()
                .get("autocomplete")
                .body()
                .prettyPrint();

        String expected = getResourceAsString("testAutocompleteSearch/expected.json");

        JsonAssert.assertJsonEquals(    // Проверка
                expected,
                actually,
                JsonAssert.when(IGNORING_ARRAY_ORDER) // игнорирование порядок элементов в массиве
        );
    }

    @Test
    @DisplayName("Test Recipe by ID")
    void testTasteRecipeById() {
        given() // Request (ЗАПРОС)
                .log()
                .all()
                .param("apiKey", API_KEY)
                .pathParam("id", 69095)
                .expect()   //Response (ОТВЕТ)
                .body("sweetness", greaterThan(48.14F))   //проверяем, что в теле ответа "sweetness" более чем 48.14
                .body("saltiness", lessThan(45.30F))
                .body("sourness", is(15.6F))
                .body("bitterness", is(19.17F))
                .body("savoriness", is(26.45F))
                .body("fattiness", is(100.0F))
                .body("spiciness", is(0.0F))
                .when()
                .get("{id}/tasteWidget.json")
                .prettyPrint();
    }

    @Test
    @DisplayName("Test Equipment by ID")
    void testEquipmentById() {
        given()
                .log()
                .all()
                .param("apiKey", API_KEY)
                .pathParam("id", 1003464)
                .expect()
                .body("equipment[1].name", is("pie form"))
                .body("equipment[1].image", is("pie-pan.png"))
                .when()
                .get("{id}/equipmentWidget.json")
                .prettyPrint();
    }

    @Test
    @DisplayName("Test Equipment by ID #2")
    void testEquipmentById_2() {
        EquipmentItem target = new EquipmentItem("pie-pan.png", "pie form");

        EquipmentResponse response = given()
                .log()
                .all()
                .param("apiKey", API_KEY)
                .pathParam("id", 1003464)
                .expect()
                .log()
                .body()
                .when()
                .get("{id}/equipmentWidget.json")
                .as(EquipmentResponse.class);

        response.getEquipment()
                .stream()
                .filter(item -> item.getName().equals("pie form"))
                //.peek(item -> Assertions.assertEquals("pie-pan.png", item.getImage()))
                .peek(item -> Assertions.assertEquals(target, item))
                .findAny()
                .orElseThrow();
    }

    @Test
    @DisplayName("Test Equipment by ID #3")
    void testEquipmentById_4() {
        EquipmentItem target = new EquipmentItem("pie-pan.png", "pie form");

        EquipmentResponse requestSpoon = given()
                .log()
                .all()
                .param("apiKey", API_KEY)
                .pathParam("id", 1003464)
                .when()
                .get("{id}/equipmentWidget.json")
                .then()
                .extract()
                .body()
                .as(EquipmentResponse.class);

//        assertThat(requestSpoon.getEquipment().get(1), is(target));

        Assertions.assertEquals(requestSpoon.getEquipment().get(1), target);
    }

    // чтение файла expected.json в /test/resources/spoonaccular
    public String getResourceAsString(String resource) throws IOException {
        InputStream stream = getClass().getResourceAsStream(resource);
        assert stream != null;
        byte[] bytes = stream.readAllBytes();
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
