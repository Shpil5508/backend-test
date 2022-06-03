package lesson_3.Homework_3;

import io.restassured.RestAssured;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;
import static net.javacrumbs.jsonunit.core.Option.IGNORING_ARRAY_ORDER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SpoonTest {
    private static final String API_KEY = "487fe24fd3e846cbbb92723ddd94928d";
    final String SEARCH_RECIPES_URI = "recipes/complexSearch";
    final String RECIPE_INFO_BY_ID = "recipes/{id}/information";
    final String INGREDIENT_SEARCH = "food/ingredients/search";

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://api.spoonacular.com/";
    }

    @Test
    @DisplayName("#1 Test Search Recipes by query & maxCalories & number")
    void testSearchRecipes() {
        given()
                .log()
                .all()
                .param("apiKey", API_KEY)
                .queryParam("query", "chicken")
                .queryParam("maxCalories", 1000)
                .queryParam("number", 2)
                .expect()
                .body("results[0].title", is("Chicken 65"))
                .body("results[0].nutrition.nutrients[0].name", is("Calories"))
                .body("number", is(2))
                .when()
                .get(SEARCH_RECIPES_URI)
                .prettyPrint();
    }

    @Test
    @DisplayName("#2 Test Search Recipes to equals json")
    void testSearchRecipesJson() throws IOException {
        String actually = given()
                .log()
                .all()
                .param("apiKey", API_KEY)
                .queryParam("query", "chicken")
                .queryParam("maxCalories", 1000)
                .queryParam("number", 2)
                .expect()
                .when()
                .get(SEARCH_RECIPES_URI)
                .body()
                .prettyPrint();

        String expected = getResourceAsString("expected.json");

        JsonAssert.assertJsonEquals(
                expected,
                actually,
                JsonAssert.when(IGNORING_ARRAY_ORDER)
        );
    }

    public String getResourceAsString(String resource) throws IOException {
        InputStream stream = getClass().getResourceAsStream(resource);
        assert stream != null;
        byte[] bytes = stream.readAllBytes();
        return new String(bytes, StandardCharsets.UTF_8);
    }

    @Test
    @DisplayName("#3 Test get recipe information by ID")
    void testGetRecipeInfo() {
        String response = given()
                .log()
                .all()
                .param("apiKey", API_KEY)
                .pathParam("id", 716429)
                .queryParam("includeNutrition", false)
                .when()
                .get(RECIPE_INFO_BY_ID)
                .body()
                .prettyPrint();

        assertThat(response.contentEquals("vegetarian"), is(false));
        assertThat(response.contains("vegan"), is(true));
    }

    @Test
    @DisplayName("#4 Test ingredient search")
    void testIngredientSearch() {
        given()
                .log()
                .all()
                .param("apiKey", API_KEY)
                .queryParam("query", "banana")
                .queryParam("number", 2)
                .queryParam("sort", "calories")
                .when()
                .get(INGREDIENT_SEARCH)
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("#5 Test ingredient search, number more 100")
    void testIngredientSearchIsNegative() {
        String response = given()
                .log()
                .all()
                .param("apiKey", API_KEY)
                .queryParam("query", "banana")
                .queryParam("number", 101)
                .queryParam("sort", "calories")
                .expect()
                .body("number", lessThan(101))
                .when()
                .get(INGREDIENT_SEARCH)
                .prettyPrint();
    }
}




