# AdvanceTesting_JUnit_and_Mockito

- You can create a test by clicking on the class name you want to make class Test for it ,and choose a librery ,then choose the method(s) you want to test.
- the test methods don't need a a return type.
- the  tests helps us to reduce the time needed to run the entier code.

-Life Cycle methods in JUnit 5: 

@BefreEach
@BeforeAll
@AfterEach
@AfterAll

- you have different Annotation for JUnit tests like :

@Test
@RepeatedTest(3)
@ParameterizedTest
@CsvSource
@ValueSource
@NullSource
@EnumSource
@CsvFileSource
@MethodSource
@ArgumentsSource

.....
- TESTING EXCEPTIONS:

- if you want to test Exceptions with JUnit , you have the method assertThrows with different parameters
- in test methods ,we test that the method throws exception , not the try cach on it ,
 if you want to test (try catch) , you deal with it as if it is (if else).

....

MOCKITO Library :

- To use Mockito you should Annotate the class with @ExtendWith(MockitoExtension.class)
- It helps with the simulation of objects or classes ,but we don't need to implement the hole logic.
- It is a librerey that creats a dummy objects witch can be mocked.
- we should add the mockito dependency in the pom.xml file.
- we use Mockito so we don't test directly on the DB .
- The developer should do these Unit tests, but not the integretion tests and the automated tests ,those are done by QA.
  so basically we simulate the process with Mockito.
- The tests shold be repetitive.

- WE HAVE 3 WAYS TO MOCK;

1- on the class level : you declare an object and put @Mock on top of it.
   we use this way when the mocked object is used in different places in the class.
   example: 
     @Mock
    private UserRepository userRepository;
     @InjectMocks
     private UserService userService;
     
     that means you want to mock userRepository , so you can inject it in userService
    
2- mock directly in the method : Mockito.mock(className.class);
   we use this way when we need to mock locally.
   
3- you send it as a parameter (@Mock HTTPClient) ,and HTTPClient is a parameter.

   
 - On test methods we have 3 steps usually;

 
 1- You need to have the mocked objects
 2- SIMULATION on mocked object using When() and Return()
 3- CALLING Assertion on (expected,the method on object)
 4- VERIFICATION usinf verify .
 
 PS:
 * All the cases in the method should be tasted(like if somthing do this , or throw excepion).
 * Using mock you don't bring data from the databse , we actually simulate the behavior.
 
 ..........
 
POWERMOCKITO Library:
 
 - It is a librery based on Mockito.
 - It helps us not only to test the behavior of the methods ,but also the simulation of creating objects..
 - we can simulate the constructors.
 - we can simulate the Static final methods with PowerMockito, rather than simulating only
   public non static methods wit Mockito

- in pom.xml we should add the PowerMockito and the Api dependency.

