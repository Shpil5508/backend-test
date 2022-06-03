package lesson_6.Homework_6;

import lesson_6.db.dao.ProductsMapper;
import lesson_6.db.model.Products;
import lesson_6.db.model.ProductsExample;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.List;

public class MiniMarketBDTest {
    public static void main(String[] args) throws IOException {

        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("././myBatisConfig.xml"));

        try (SqlSession session = sessionFactory.openSession()) {
            ProductsMapper productsMapper = session.getMapper(ProductsMapper.class);

            ProductsExample example = new ProductsExample();

/* Создал в базе продукт "руками"
{"id": 470,
  "title": "Limon",
  "price": 99,
  "categoryTitle": "Food"}
Проверяем его присутствие в базе*/
            Products product = productsMapper.selectByPrimaryKey(470L);
            System.out.println("№1 Проверяем наличие созданного продукта в базе:");
            System.out.println(product);
            Assertions.assertEquals("Limon", product.getTitle());
            System.out.println();

            System.out.println("№2 Вносим изменения в продукт:");
            product.setTitle("Mango");
            product.setPrice(1000);
            System.out.println(product);
            Assertions.assertEquals("Mango", product.getTitle());
            Assertions.assertEquals(1000, product.getPrice());
            System.out.println();

            System.out.println("№3 Удаляем продукт по его ID:");
            productsMapper.deleteByPrimaryKey(470L);
            example.clear();
            example.createCriteria()
                    .andTitleLike("Mango")
                    .andPriceGreaterThan(1);

            List<Products> products = productsMapper.selectByExample(example);
            System.out.println(products);
            System.out.println();

            product = productsMapper.selectByPrimaryKey(470L);
            System.out.println("№4 Проверяем остался ли в базе продукт с ID: 470 ");
            System.out.println(product);
            Assertions.assertNull(product);
        }
    }
}
