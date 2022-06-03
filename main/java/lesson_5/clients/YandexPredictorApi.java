package lesson_5.clients;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface YandexPredictorApi {

    @GET("getLangs") //аннотация на GET-запрос на https://predictor.yandex.net/api/v1/predict.json/getLangs
    Call<List<String>> getLangs(@Query("key") String key);  //аннотация Query передаёт query-параметр "key"
    /* По строке 12 - так как в документации на https://yandex.ru/dev/predictor/doc/dg/reference/getLangs.html:
     * "В JSON-интерфейсе вместо XML-элементов возвращаются JavaScript-объекты с теми же именами и семантикой:
     * ["ru","en","pl","uk","de","fr","es","it","tr"]"
     * тогда мы и создаём объект типа Call<List<String>>, т.к. в документации в ответе массив из строк*/

    /* См. документацию https://yandex.ru/dev/predictor/doc/dg/reference/complete.html:
    * Передаём значения к query-параметрах согласно документации. Если прописано в документации
        "Обязательные:
    "key"	string	API-ключ. Получите бесплатный API-ключ на этой странице.
    "lang"	string	Язык текста (например, "en"). Список языков можно получить с помощью метода getLangs.
    "q"	string	Текст, на который указывает курсор пользователя.
        Необязательные:
    "limit"	int	 Максимальное количество возвращаемых строк (по умолчанию 1)."
    *То и в аннотации @Query("") НУЖНО ПИСАТЬ ИМЕННО "key", "lang", "q", "limit" и назвать их в String уже можно как удобно нам*/
    @GET("complete")    // ссылка на URL https://predictor.yandex.net/api/v1/predict.json/complete
    Call<PredictorResult> complete(
            @Query("key") String key,
            @Query("q") String query,
            @Query("lang") String lang,
            @Query("limit") int limit
    );
}
