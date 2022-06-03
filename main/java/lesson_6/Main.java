package lesson_6;

import lesson_6.db.dao.ProductsMapper;
import lesson_6.db.model.Products;
import lesson_6.db.model.ProductsExample;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("./myBatisConfig.xml"));

/* SQL сессию обрамляем в try() */
        try (SqlSession session = sessionFactory.openSession()) {
            ProductsMapper productsMapper = session.getMapper(ProductsMapper.class);

/* Достанем продукт по его ID из ресурса
https://minimarket1.herokuapp.com/market/swagger-ui.html#/product-controller */
            Products product = productsMapper.selectByPrimaryKey(444L);
            System.out.println("Продукт по его ID: ");
            System.out.println(product);
            System.out.println("");

/* Создаём критерии поиска */
            ProductsExample example = new ProductsExample();
            example.createCriteria()
                            .andTitleLike("Banana")
                                    .andPriceGreaterThan(10);

            List<Products> products = productsMapper.selectByExample(example);
            System.out.println("Ищем продукт 'Banana' с ценой более 10: ");
            System.out.println(products);
            System.out.println("");

/* Теперь найдём все продукты 2-й categoryId */
            example.clear();   // обнуляем поисковые данные, т.к. мы в одном методе
            example.createCriteria()
                    .andCategoryIdEqualTo(2L);

            products = productsMapper.selectByExample(example);
            System.out.println("Вывод всех продуктов по categoryId = 2");
            System.out.println(products);
            System.out.println("");

/* Удаляем сущность */
            productsMapper.deleteByPrimaryKey(469L);
            example.clear();
            example.createCriteria()
                    .andTitleLike("Limon")
                    .andPriceGreaterThan(1);

            products = productsMapper.selectByExample(example);
            System.out.println("Удаляем продукт по его ID и выводим список из продуктов по их " +
                    "наименованию и цене более чем 1");
            System.out.println(products);
            System.out.println("");

/* Вывод в консоль всех сущностей с ID > чем 444  */
            example.clear();
            example.createCriteria()
                    .andIdGreaterThan(444L);

            products = productsMapper.selectByExample(example);
            System.out.println("Вывод всех сущностей с ID более чем 444");
            System.out.println(products);
            System.out.println("");

        }
    }
}
