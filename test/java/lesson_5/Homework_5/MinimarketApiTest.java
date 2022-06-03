package lesson_5.Homework_5;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MinimarketApiTest extends AbstractTest {
    private static MinimarketService service;

    @BeforeAll
    static void beforeAll() {
        service = new MinimarketService();
    }

    @Test
    @DisplayName("#1 Test GET to ID from category-controller")
    void testGetCategoryId() throws Exception {
        CategoryResult categoryId = service.getProductById(1);
        assertJson(getResource("category200.json"), categoryId);
    }

    @Test
    @DisplayName("#2 Test GET product-controller")
    void testGetProducts() throws Exception {
        List<Product> products = service.getProducts();
        assertJson(getResource("arrayProducts.json"), products);
    }

    @Test
    @DisplayName("#3 Test POST product in product-controller")
    void testPostProduct() throws Exception {
        Product product = new Product();
        product.setTitle("Limon");
        product.setPrice(95);
        product.setCategoryTitle("Food");

        service.postProduct(product);

        System.out.println(SerializeJson.getJson(product));
        assertJson(getResource("postProduct.json"), product);
    }

    @Test
    @DisplayName("#4 Test PUT product in product-controller")
    void testPutProduct() throws Exception {
        Product products = service.putProduct(464, "Banana", 120, "Food");
        System.out.println(SerializeJson.getJson(products));

        assertJson(getResource("putProduct.json"), products);
    }

    @Test
    @DisplayName("#5 Test GET product in product-controller")
    void testGetProduct() throws Exception {
        Product products = service.getProduct(464);

        assertJson(getResource("putProduct.json"), products);
    }

    @Test
    @DisplayName("#6 Test DELETE created product from product-controller")
    void testDeleteProduct() {
        Product productDel = new Product();
        service.deleteProduct(463);
    }
}
