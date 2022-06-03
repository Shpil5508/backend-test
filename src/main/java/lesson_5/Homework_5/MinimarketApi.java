package lesson_5.Homework_5;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface MinimarketApi {
    @GET("categories/{id}")
    Call<CategoryResult> getProductById(@Path("id") long id);

    @GET("products")
    Call<List<Product>> getProducts();

    @POST("products")
    Call<Product> postProduct(@Body Product product);

    @PUT("products")
    Call<Product> putProduct(@Body Product product);

    @GET("products/{id}")
    Call<Product> getProduct(@Path("id") long id);

    @DELETE("products/{id}")
    Call<ResponseBody> deleteProduct (@Path("id") long id);
}
