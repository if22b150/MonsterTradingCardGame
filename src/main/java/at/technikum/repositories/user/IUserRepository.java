package at.technikum.repositories.user;

import at.technikum.models.User;

public interface IUserRepository extends IRepository<User> {
    User create(String username, String password);
    User getByUsername(String username);
}
