package lesson_5;

import lesson_4.Serialization.SerializeJson;
import lesson_5.clients.PredictorResult;
import lesson_5.clients.YandexPredictorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class YandexPredictorTest extends AbstractTest{

    private static YandexPredictorService service;

    @BeforeAll
    static void beforeAll() {
        service = new YandexPredictorService();
    }

    @Test
    @DisplayName("#1 Сравниваем Json-файлы по GET запросу Langs")
    void testGetLAngs() throws Exception {
        List<String> langs = service.getLangs();
        assertJson(getResource("langs.json"), langs);
    }

    @Test
    void testPrediction() throws Exception {
        PredictorResult result = service.complete("Hello w", "en", 3);
        System.out.println(SerializeJson.getJson(result));

        assertJson(getResource("complete.json"), result);
    }
}
