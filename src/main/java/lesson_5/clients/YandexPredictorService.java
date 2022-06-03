package lesson_5.clients;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

import static lesson_5.clients.RetrofitCallExecutor.executeCall;

public class YandexPredictorService {
    private static final String API_KEY = "pdct.1.1.20220521T122010Z.70bded073ad608e4.51d4f023ddaede76662d759c2cecc32ccacb33d6";
    private final YandexPredictorApi api;

    public YandexPredictorService() {
        /* Подключаем логирование ОТВЕТОВ */
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(s -> {
            System.out.println(s);
        });

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        api = new Retrofit.Builder()
                .baseUrl("https://predictor.yandex.net/api/v1/predict.json/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(YandexPredictorApi.class);
    }

    public List<String> getLangs() {
        return executeCall(api.getLangs(API_KEY));
//        call.enqueue(new Callback<>() { //enqueue() - означает, что мы call ставим в очередь и передаём
//                                                    // данному методу экземпляр Callback
//            @Override
//            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<List<String>> call, Throwable throwable) {
//
//            }
//        });
    }

    public PredictorResult complete(String query, String lang, Integer limit) {
        return executeCall(api.complete(API_KEY, query, lang, limit));
    }
}
