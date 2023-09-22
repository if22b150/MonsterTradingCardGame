package at.technikum.repositories.user;

import at.technikum.models.User;

import java.util.ArrayList;

public interface IUserRepository {

    ArrayList<User> all();

    User get(int id);

    void create();
}
