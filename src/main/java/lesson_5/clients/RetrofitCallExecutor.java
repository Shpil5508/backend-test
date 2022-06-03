package lesson_5.clients;

import retrofit2.Call;
import retrofit2.Response;

import javax.annotation.Nullable;
import java.io.IOException;

public final class RetrofitCallExecutor {

    private RetrofitCallExecutor() {
    }

    @Nullable   // аннотация указывающая на то, что наш метод способен возвращать NULL
    public static <T> T executeCall(Call<T> call) {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body(); // если всё хорошо, то метод вернёт ТЕЛО ответа
            } else {
                return null;            // если не хорошо, то вернёт NULL
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
