package lesson_4;

import org.junit.jupiter.api.Test;

public class BaseTest extends AbstractTest{

    @Test
    void test() throws Exception {
        System.out.println(getResource("text.txt"));
    }
}
