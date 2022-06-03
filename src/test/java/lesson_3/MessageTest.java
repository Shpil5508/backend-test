package lesson_3;

import com.google.gson.Gson;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static net.javacrumbs.jsonunit.core.Option.IGNORING_ARRAY_ORDER;

public class MessageTest {

    @Test
    @DisplayName("JSON body test to equals")
    void testMessageBody() throws IOException {
        String expected = getResourceAsString("expected.json"); // Ожидаемый (expected) результат в файле "expected.json".

        Message actually = Message.builder()    // Создаём Json с данными и выводим его в консоль
                .author("Mike")
                .text("Make JSON")
                .createDate(new Date())
                .build();
        printJson(actually);

        JsonAssert.assertJsonEquals(    // Проверка Json-ов между собой
                expected,
                actually,
                JsonAssert.when(IGNORING_ARRAY_ORDER)   // игнорируем поле массива в Json-е
        );
    }

    public void printJson(Object o) {   //метод выводит Json в консоль через библиотеку GSON
        System.out.println(new Gson().toJson(o));
    }

    // Метод для сравнивания JSON-ов.
    public String getResourceAsString(String resource) throws IOException {
        InputStream stream = getClass().getResourceAsStream(resource);
        assert stream != null;
        byte[] bytes = stream.readAllBytes();
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
