package lesson_4;

import lesson_3.EquipmentItem;
import lesson_3.EquipmentResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RecipesTest extends SpoonaccularTest {

    @Test
    @DisplayName("#1 Test Autocomplete search")
    void testAutocompleteSearch() throws Exception {
        String actually = given()
                .queryParam("query", "cheese")
                .queryParam("number", 10)
                .expect()
                .when()
                .get("recipes/autocomplete")
                .body()
                .prettyPrint();

        String expected = getResource("expected.json");
        assertJson(expected, actually);
    }

    @Test
    @DisplayName("#2 Test Recipe by ID")
    void testTasteRecipeById() {
        given()
                .pathParam("id", 69095)
                .expect()
                .body("sweetness", greaterThan(48.14F))
                .body("saltiness", lessThan(45.30F))
                .body("sourness", is(15.6F))
                .body("bitterness", is(19.17F))
                .body("savoriness", is(26.45F))
                .body("fattiness", is(100.0F))
                .body("spiciness", is(0.0F))
                .when()
                .get("recipes/{id}/tasteWidget.json")
                .prettyPrint();
    }

    @Test
    @DisplayName("#3 Test Equipment by ID")
    void testEquipmentById() {
        given()
                .pathParam("id", 1003464)
                .expect()
                .body("equipment[1].name", is("pie form"))
                .body("equipment[1].image", is("pie-pan.png"))
                .when()
                .get("recipes/{id}/equipmentWidget.json")
                .prettyPrint();
    }

    @Test
    @DisplayName("#4 Test Equipment by ID")
    void testEquipmentById_2() {
        EquipmentItem target = new EquipmentItem("pie-pan.png", "pie form");

        EquipmentResponse response = given()
                .pathParam("id", 1003464)
                .expect()
                .log()
                .body()
                .when()
                .get("recipes/{id}/equipmentWidget.json")
                .as(EquipmentResponse.class);

        response.getEquipment()
                .stream()
                .filter(item -> item.getName().equals("pie form"))
                .peek(item -> Assertions.assertEquals(target, item))
                .findAny()
                .orElseThrow();
    }

    @Test
    @DisplayName("#5 Test Equipment by ID")
    void testEquipmentById_4() {
        EquipmentItem target = new EquipmentItem("pie-pan.png", "pie form");

        EquipmentResponse requestSpoon = given()
                .pathParam("id", 1003464)
                .when()
                .get("recipes/{id}/equipmentWidget.json")
                .then()
                .extract()
                .body()
                .as(EquipmentResponse.class);

        assertThat(requestSpoon.getEquipment().get(1), is(target));
//        Assertions.assertEquals(requestSpoon.getEquipment().get(1), target);
    }
}
