package lesson_4;

import net.javacrumbs.jsonunit.JsonAssert;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static net.javacrumbs.jsonunit.core.Option.IGNORING_ARRAY_ORDER;

public class AbstractTest {

    public void assertJson(Object expected, Object actually) {
        JsonAssert.assertJsonEquals(    // Проверка
                expected,
                actually,
                JsonAssert.when(IGNORING_ARRAY_ORDER) // игнорирование порядок элементов в массиве
        );
    }

    /* Создаём класс для чтения файлов из resources. Эти файлы нужно помещать в директорию с названием класса
    * в котором прописывается метод для его чтения. */
    public String getResource(String name) throws Exception {
        String resource = getClass().getSimpleName() + "/" + name;
        InputStream inputStream = getClass().getResourceAsStream(resource);
        assert inputStream != null;
        byte[] bytes = inputStream.readAllBytes();
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public File getFile(String name) {
        String resource = getClass().getSimpleName() + "/" + name;
        String file = getClass().getResource(resource).getFile();
        return new File(file);
    }

}
