package User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> getUserById(int id);

    List<User> getAllUsers();

    User addUser(User user);

}
