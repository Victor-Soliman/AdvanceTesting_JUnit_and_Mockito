import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class FibonacciTest {

    private Fibonacci fibonacci;

    @BeforeEach
    void setUp() {
        fibonacci = new Fibonacci();
    }


    @ParameterizedTest
    @CsvSource(value = {"6,8", "12,144", "14,377"}) // we choose the first value as the position of the number ,
        // and the second parameter is the number itself
    void getValueOfElement(int element, int value) {
        // in order to use the method from the filed fibonacci , we should create the object using @BeforeEach above .
        Assertions.assertEquals(value, fibonacci.getValueOfElement(element));
    }
}