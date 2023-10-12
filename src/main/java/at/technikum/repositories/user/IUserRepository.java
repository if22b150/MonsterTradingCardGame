package at.technikum.repositories.user;

import at.technikum.models.User;
import at.technikum.repositories.IRepository;

public interface IUserRepository extends IRepository<User> {
    User create(String username, String password);
    User getByUsername(String username);
}
