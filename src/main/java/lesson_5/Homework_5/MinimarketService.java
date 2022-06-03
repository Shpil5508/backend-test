package lesson_5.Homework_5;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

import static lesson_5.Homework_5.RetrofitCallMarket.executeCall;

public class MinimarketService {
    private final MinimarketApi api;

    public MinimarketService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(System.out::println);
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        api = new Retrofit.Builder()
                .baseUrl("https://minimarket1.herokuapp.com/market/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(MinimarketApi.class);
    }

    public CategoryResult getProductById(long id) {
        return executeCall(api.getProductById(id));
    }

    public List<Product> getProducts() {
        return executeCall(api.getProducts());
    }

    public Product postProduct(Product product) {
        return executeCall(api.postProduct(product));
    }

    public Product putProduct(long id, String title, Integer price, String categoryTitle) {
        Product product = new Product(id, title, price, categoryTitle);
        return executeCall(api.putProduct(product));
    }

    public Product getProduct(long id) {
        return executeCall(api.getProduct(id));
    }

    public void deleteProduct(long id) {
        executeCall(api.deleteProduct(id));
    }
}
