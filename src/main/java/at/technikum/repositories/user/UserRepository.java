package at.technikum.repositories.user;

import at.technikum.models.User;

import java.util.ArrayList;

public class UserRepository implements IUserRepository{
    @Override
    public ArrayList<User> all() {
        User u1 = new User("user", "123456");
        User u2 = new User("user2", "1234567");
        ArrayList<User> l = new ArrayList<User>();
        l.add(u1);
        l.add(u2);
        return l;
    }

    @Override
    public User get(int id) {
        return new User("user", "123456");
    }

    @Override
    public void create() {

    }
}
