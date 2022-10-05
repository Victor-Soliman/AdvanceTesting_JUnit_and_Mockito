import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.time.Month;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    //    @Test
    @RepeatedTest(3)
    // instead of @Test ,we can use @RepeatedTest(number of tests)
    // we can use this when we need to add a user in the DB ,first time will add it
    // but second time will tell me that it is already exists
    void add() {
        // we write it because we want to see in console that it is repeated
        System.out.println("Test method add is executing");

        //given
        //we create the object because we need to use it in when
        // so we can access the method add from it
        Calculator calculator = new Calculator();
        //when
        double result = calculator.add(1, 2.5);
        //then
        //we use assertions cause this is the method in the JUnit library,
        // and not assertThat because it is a method in AsserJ library
        Assertions.assertEquals(3.5, result);

    }


    // Toate metodele de mai jos se folosesc IMPREUNA cu @ParameterizedTest


    // 2) an example to show that you can test a set of entry data in one shot
    // is is used to send a set of data
    // PS. you can use @CsvFile to test all data from a file
    @ParameterizedTest
    // instead of creating the instance each time in method ,we declare it on class level,
    // and we make a lifeCycle method -> @BeforeEach so we can create a calculator instance before each method to be tested
    @CsvSource({"10.0,10.0,20.0", "7.5,3.5,11", "13.9,7.1,21.0"})
    // if we use @ParameterizedTest and @CsvSource (set of entry values) like the format above, where first element
    // will be firstNumber ,and second element the second number , and the third element the result
    // and he will compare each third number(result) wit the result from the method test below
    void addWithParams(double firstNumber, double secondNumber, double result) {

        //then
        Assertions.assertEquals(result, calculator.add(firstNumber, secondNumber));
    }

    // 3) an example to show how to repeat the test for one set of values ( one test for several values)
    // we use @ValueSource that has different numbers of values , so it will make 4 tests ( one for each value)
    // it is used to send a single value
    @ParameterizedTest
    @ValueSource(doubles = {-5, 6, 7, -2.5})
    void reverseNumberTest(double number) {

        // then
        Assertions.assertEquals(number * -1, calculator.reverseNumber(number));
    }

    // another example to show that @ValueSource permit to send any DataType ( even classes)
    @ParameterizedTest
    @ValueSource(strings = {"Testing", "JUnit", "SDA"})
    void verify_if_value_is_not_null(String word) {
        assertNotNull(word);
    }

    //4) example shows @NullSource work , to check if a source (value) is null, a real example is if the server send
    // a null value and the repository throw an exception , so you test the null coming from server
    // @EmptySource
    @ParameterizedTest
    @NullSource
    void verifyNullValue(String value) {
        Assertions.assertNull(value);
    }


    //5) example to test if a month is a leap year

    @ParameterizedTest
    // in parantezele de Enum trebuie sa scriem valoarea enum-ului pe care il folosim ,in cazul nostru Month
    @EnumSource(value = Month.class, names = {"APRIL", "JUNE", "SEPTEMBER", "NOVEMBER"})
    void verifyMonthHas30Days(Month month) {
        final boolean isLeapYear = false;
        Assertions.assertEquals(30, month.length(isLeapYear));
    }


    // 6) another example shoes the use of  @CsvSource

    @ParameterizedTest
    @CsvSource(value = {"3, true", "5, true", "8,false"})
    void isOddResult(int number, boolean expected) {
        Assertions.assertEquals(expected, calculator.isOdd(number));
    }

    // 7) example for @CsvFileSource that test the data from a file ,
    // we need to create a resource file in test/ resources , and we write some values there to test if its odd number
    // @CsvFileSource know how to read strings only
    //
    @ParameterizedTest
    @CsvFileSource(resources = "abc/dataSource.csv")
    // we write the resources file name ,or the path to it from resources and down
    // the resources from Test is different than the resources in main
    void isOddResultFromCsv(String input, boolean expected) { // the order when choosing the data type matters and should match
        Assertions.assertEquals(expected, calculator.isOdd(Integer.parseInt(input)));  // we parse the input string value to int
    }

    // 8) exemple for @MethodSource where we will test adding when we receive the parameters from a method
    @ParameterizedTest
    @MethodSource("getValuesForAdd")
    // inside it we need to pass the name of the method that will bring us the data
    // we will create it below
    void verifyAddWithMethodSource(double firstNumber, double secondNumber, double result) {
        Assertions.assertEquals(result, calculator.add(firstNumber, secondNumber));
    }

    // the method that will return a Stream of Arguments ( so we can use data type like double ,string ...)
    // cause it is very possible that you have different data type in the set of data that ou are using
    //this Arguments come from Jupiter package
    // the method should be static
    static Stream<Arguments> getValuesForAdd() {
        return Stream.of(Arguments.of(10, 15, 25),
                Arguments.of(5, 8, 13),
                Arguments.of(100, 50, 150));
    }

    // 9) you can use List.of instead of Stream.of, but it is not used a lot
    @ParameterizedTest
    @MethodSource("getValuesAsListForAdd")
    void verifyAddWithMethodSourceAsList(double firstNumber, double secondNumber, double result) {
        Assertions.assertEquals(result, calculator.add(firstNumber, secondNumber));
    }

    // we create a normal method with the same name that we have in the () of @MethodSource

    static List<Arguments> getValuesAsListForAdd() {
        return List.of(Arguments.of(-5, +5, 0),
                Arguments.of(50, -25, 25),
                Arguments.of(500, 500, 1000));
    }


    // 10) exemple for @ArgumentsSource where we will test odd number when we receive the parameters from a class
    // usually we use it when we have something complex
    @ParameterizedTest
    @ArgumentsSource(Parameters.class)
    // we pass the class name that will give tus the arguments
    // we created below
    void testOddNumberWithArgument(int number, boolean result) {
        Assertions.assertEquals(result, calculator.isOdd(number));
    }

    // we create the class we will use in the  @ArgumentsSource() that implements ArgumentsProvider from JUnit
    static class Parameters implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            //we need to return the arguments that we will give to the test method , the number should be or not : odd
            return Stream.of(Arguments.of(10, false),
                    Arguments.of(7, true),
                    Arguments.of(13, true));
        }
    }

    // Testing Exceptions
    // 1) we need 2 test methods cause in out method ;
    // the first case : is when element == 0 , and the other case is when the element is different than 0

    @Test
    void testDivideByZero(){
        //when
        // we choose assertThrows ,and we need to tell what kind of exception we receive (IllegalArgumentException.class)
        // actually is the same exception type like the one thrown in the method,
        // and then we say when we use this exception ? -> answer -> when we divide by 0 , and because here we have
        // an executable interface(functional interface) wee use lambda expression for that
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> calculator.divide(5, 0));

        //then
        //here we test that the message "Cannot divide by 0" is the same as the one thrown in the method
        //the test will fail is the expected message is not the same as the one gotten
        Assertions.assertEquals("Cannot divide by 0", exception.getMessage());

    }
}