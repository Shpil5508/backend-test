package lesson_5.Homework_5;

import retrofit2.Call;
import retrofit2.Response;

import javax.annotation.Nullable;
import java.io.IOException;

public class RetrofitCallMarket {

    private RetrofitCallMarket() {}

    @Nullable
    public static <T> T executeCall(Call<T> call) {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
