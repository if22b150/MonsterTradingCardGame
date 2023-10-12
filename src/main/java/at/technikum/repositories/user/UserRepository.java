package at.technikum.repositories.user;

import at.technikum.database.Database;
import at.technikum.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    public User create(String username, String password) {
        String query = "INSERT INTO users (username,password,coins) VALUES (?,?,?);";
        try {
            PreparedStatement statement = Database.connect().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,username);
            statement.setString(2,password);
            statement.setInt(3,20);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1); // Assuming "id" is a long integer.
                System.out.println("User created with ID: " + id);
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new User(username, password);
    }

    @Override
    public User getByUsername(String username) {
        return null;
    }
}
