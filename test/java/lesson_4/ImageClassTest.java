package lesson_4;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

public class ImageClassTest extends SpoonaccularTest {

    @Test
    void testBurgerClassification() {
        given()
                .multiPart(getFile("burger.jpg")) // путь к файлу test/resources/lesson_4/ImageClassTest/burger.jpg
                                                        // папка в resources называется как класс этого метода
                .expect()
                .body("category", is("burger"))
                .body("probability", greaterThan(0.7F))
                .log().all()
                .when()
                .post("food/images/classify");
    }
}
