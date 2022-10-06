package User;

import com.sun.source.tree.ModuleTree;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

// in order to use Mockito library we should put the annotations @ExtendWith(MockitoExtension.class)

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    // we need to simulate the id and the user that we will work with ( cause it wil NOT bring me data from DB , insted
    // we declare the data s fildes below)
    // So we create CONSTANTS taking in consideration the data type from the class in main ,and we five them values

    private static final int ID = 7; // for ID = 7 , this will be used in the tests methods below

//    private static final int ID = Mockito.anyInt();  // for any ID i want to use

    private static final User USER = new User(ID, "Vali", 32);

    private static final List<User> USER_LIST = List.of(USER,
            new User(3, "Marius", 30),
            new User(9, "Ion", 20));   // we declare this list to use in the test method of get all below

    //we declare Userservice object in order to reach the methods in it so we can test it,
    // but the method inside the class UserService depends on another class (UserRepository)to get the user searched,
    //so we MOCK (simulate) UserRepository

    @Mock
    private UserRepository userRepository; // FIRST way to mock objects from a class using @Mock Annotation


    @InjectMocks // because in the constructor of UserService he expects to receive a UserRepository ,so in
    // the background he will search in this class for a mock that fit his constructor
    private UserService userService;

    @Test
    void getUserById() {
        // the second option us used when we have only one test method

        //    UserRepository repository = Mockito.mock(UserRepository.class);  // SECOND option to mock objects from class
        //    UserService service = new UserService(repository);
        // .......

        //SIMULATION:
        // we simulate the behavior of UserRepository .. so it is instead of // when
        Mockito.when(userRepository.getUserById(ID)).thenReturn(Optional.of(USER));

        // CALLING
        // Assertions. (when JUnit) , AssertThat (when using AssertJ)
        // we need to test that the result USER , in the same as the user returned from method getUserById(ID) on userService
        Assertions.assertEquals(USER, userService.getUserById(ID));// the ID = 7 ,here if we choose any other number ,il will show error

        // be aware that we inside that the userService.getUserById(ID) method , it will go and use the userRepository.getUserById(ID)
        // which we mocked above

        //VERIFICATION
        // we can also check if the method from the mocked object happened to be called one on never or at leastOnce
        // here we use .verify
        Mockito.verify(userRepository).getUserById(ID);


    }

    @Test
    void getAllUsers() {

        //SIMULATION on the mocked object (userRepository)
        Mockito.when(userRepository.getAllUsers()).thenReturn(USER_LIST);
        //CALLING assertion.assertEquals to compare (what I expect , the method called on userService)
        Assertions.assertEquals(USER_LIST, userService.getAllUsers());

        Mockito.verify(userRepository).getAllUsers();

    }


    // we want to test if a user entered a valid data on the website for example
    // we create UserValidator Interface ,
    // we create a user method in UserService
    // when we test the method from the userService , we need to have 2 test methods for the 2 cases we have there
    @Test
    void createValidUser() {
        // 2.) We need to mock a validator so i can use it in the CALLING below
        UserValidator validator = Mockito.mock(UserValidator.class);

        //3.) We need to SIMULATE the validator object ,and also the Repository object
        Mockito.when(validator.isUserValid(USER)).thenReturn(true);

        // 4.) We SILMULATE the Repository object here
        Mockito.when(userRepository.addUser(USER)).thenReturn(USER);

        // 1.)CALLING assertions. (VERIFICATION) : (the expected USER, userService. theMethodIWantToTest())
        Assertions.assertEquals(USER, userService.createUser(USER, validator));

        // 5. ) VERIFICATION , we verify if userRepository was called at least once in the method, and also we verify
        // on validator because maybe some developer will change the code from using validator to writing the verification
        // manually so the test will fail for him, and he will come back to his mind :)
        Mockito.verify(userRepository).addUser(USER);
        Mockito.verify(validator).isUserValid(USER);

        // PS: always you need to mock all the objects used in the method you are testing

    }

    @Test
    void testCreateUserInvalid() {
        // 1.) we mock the validator object
        UserValidator validator = Mockito.mock(UserValidator.class);

        // 2.) here we write what is happening for a USER ( from now on anyone calls validator you return false(invalid) )
        Mockito.when(validator.isUserValid(USER)).thenReturn(false);


        //3.) we assertThrow , the first Parameter is the class name of the Exception ,and the second parameter is the
        // implementation of the Executable interface as lambda expression , and we can add as the third
        // parameter "the exception message" instead of creating a verify method to check the message
       Assertions.assertThrows(IllegalArgumentException.class, () -> userService.createUser(USER, validator),"User is invalid");

       //4.) we need to verify that the repository object is not called , we use verifyNoMoreInteractions from Mockito
        Mockito.verifyNoMoreInteractions(userRepository);
    }

}