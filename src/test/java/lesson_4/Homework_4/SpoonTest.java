package lesson_4.Homework_4;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import junit.framework.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SpoonTest extends SpecBase {

    final String SEARCH_RECIPES_URI = "recipes/complexSearch";
    final String RECIPE_INFO_BY_ID = "recipes/{id}/information";
    final String INGREDIENT_SEARCH = "food/ingredients/search";

    @Test
    @DisplayName("#1 Test Search Recipes by query & maxCalories & number")
    void testSearchRecipes() {
        String response = given()
                .queryParam("query", "chicken")
                .queryParam("maxCalories", 1000)
                .queryParam("number", 2)
                .expect()
                .body("results[0].title", is("Chicken 65"))
                .body("number", is(2))
                .when()
                .get(SEARCH_RECIPES_URI)
                .prettyPrint();

        Assertions.assertNotNull("results.nutrition.nutrients");
        Assertions.assertNotNull("nutrition.nutrients");
        assertThat(response.contains("results"), is(true));
    }

    @Test
    @DisplayName("#2 Test Search Recipes to equals json")
    void testSearchRecipesJson() throws Exception {
        String actually = given()
                .queryParam("query", "chicken")
                .queryParam("maxCalories", 1000)
                .queryParam("number", 2)
                .expect()
                .when()
                .get(SEARCH_RECIPES_URI)
                .prettyPrint();

        String expected = getResource("expected.json");
        assertJson(expected, actually);
    }

    @Test
    @DisplayName("#3 Test get recipe information by ID")
    void testGetRecipeInfo() {
        String response = given()
                .pathParam("id", 716429)
                .queryParam("includeNutrition", false)
                .when()
                .get(RECIPE_INFO_BY_ID)
                .prettyPrint();

        assertThat(response.contentEquals("vegetarian"), is(false));
        assertThat(response.contains("vegan"), is(true));
    }

    @Test
    @DisplayName("#4 Test ingredient search")
    void testIngredientSearch() {
        given()
                .queryParam("query", "banana")
                .queryParam("number", 2)
                .queryParam("sort", "calories")
                .expect()
                .when()
                .get(INGREDIENT_SEARCH)
                .prettyPrint();

        Assertions.assertNotNull("results.id");
        Assertions.assertNotNull("results.name");
        Assertions.assertNotNull("results.image");
    }

    @Test
    @DisplayName("#5 Test ingredient search, number more 100")
    void testIngredientSearchIsNegative() {
        given()
                .queryParam("query", "banana")
                .queryParam("number", 101)
                .queryParam("sort", "calories")
                .expect()
                .body("number", lessThan(101))
                .body("results[0].name", is("banana chips"))
                .when()
                .get(INGREDIENT_SEARCH)
                .prettyPrint();
    }

    @Test
    @DisplayName("#6 Test ingredient search, number more 100")
    void testIngredientSearchIsNegative_2() {
        Response response = given()
                .queryParam("query", "banana")
                .queryParam("number", 101)
                .queryParam("sort", "calories")
                .when()
                .get(INGREDIENT_SEARCH)
                .then()
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        String name0 = jsonPath.get("results[0].name");
        String image0 = jsonPath.get("results[0].image");

        Assert.assertEquals("banana chips", name0);
        Assert.assertEquals("banana-chips.jpg", image0);
    }
}




