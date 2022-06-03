package lesson_4.Serialization;

import com.google.gson.Gson;

import java.util.Date;

public class SerializeJson {

    private static final Gson gson = new Gson();


    public static <T> String getJson(T object) {
        return gson.toJson(object);
    }

    public static <T> T getObject(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static void main(String[] args) {
        Message message = Message.builder()
                .createDate(new Date())
                .author("Mike")
                .text("Message text")
                .build();

        String json = getJson(message);
        System.out.println(json);

        System.out.println(getObject(json, Message.class));
    }
}
