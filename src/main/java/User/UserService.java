package User;

import java.util.List;

public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User getUserById(int id){
        return repository.getUserById(id).orElseThrow();
    }

    public List<User> getAllUsers(){
        return repository.getAllUsers();
    }

    // with this method we need to create a user and see if it is valid -> add it to DB , if not throw exception
    //as arguments ,we receive a user ,and a validator (PS: you can declar the validator in top of the class)
    public User createUser(final User user,UserValidator validator){
    if(validator.isUserValid(user)){
        return repository.addUser(user);
    }
    throw new IllegalArgumentException("User is invalid");
    }
}
