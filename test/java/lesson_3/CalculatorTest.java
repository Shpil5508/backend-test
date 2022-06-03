package lesson_3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class CalculatorTest {
    private static Calculator calculator;

    @BeforeAll
    static void beforeAll() {
        calculator = new Calculator();
    }

    public static Stream<Arguments> data() {
        return Stream.of(
                Arguments.of(1, 2, 3),
                Arguments.of(2, 2, 4),
                Arguments.of(3, 2, 5)
        );
    }

    @ParameterizedTest
    @MethodSource("data")   // источник данных для теста - метод data()
    void testSum(int x, int y, int expected) {
        int actually = calculator.sum(x, y);
        Assertions.assertEquals(expected, actually);
    }
}
