package lesson_5.Homework_5;

import com.google.gson.Gson;

public class SerializeJson {

    private static final Gson gson = new Gson();

    public static <T> String getJson(T object) {
        return gson.toJson(object);
    }

    public static <T> T getObject(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
}
