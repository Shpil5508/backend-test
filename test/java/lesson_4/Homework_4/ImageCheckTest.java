package lesson_4.Homework_4;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

public class ImageCheckTest extends SpecBase {

    @Test
    void testEggsClassification() {
        given()
                .multiPart(getFile("coffee.jpg"))
                .expect()
                .body("category", is("coffee"))
                .body("probability", greaterThan(0.8F))
                .log().all()
                .when()
                .post("food/images/classify");
    }

}
