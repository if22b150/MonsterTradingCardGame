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
        String query = "SELECT * FROM users where username != ?;";
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(query);
            statement.setString(1,"admin");
            ResultSet result = statement.executeQuery();

            ArrayList<User> users = new ArrayList<>();

            while(result.next()) {
                users.add(new User(
                        result.getInt("id"),
                        result.getString("username"),
                        result.getString("name"),
                        result.getString("bio"),
                        result.getString("image"),
                        result.getInt("coins"),
                        result.getString("password")
                ));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User get(int id) {
        String query = "SELECT * FROM users where id = ?;";
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(query);
            statement.setInt(1,id);
            ResultSet result = statement.executeQuery();

            User user = null;

            while(result.next()) {
                user = new User(
                        result.getInt("id"),
                        result.getString("username"),
                        result.getString("name"),
                        result.getString("bio"),
                        result.getString("image"),
                        result.getInt("coins"),
                        result.getString("password")
                );
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User create(String username, String password) {
        String query = "INSERT INTO users (username,password,coins) VALUES (?,?,?);";
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,username);
            statement.setString(2,password);
            statement.setInt(3,20);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1); // Assuming "id" is a long integer.
                System.out.println("User created with ID: " + id);
                return new User(id, username, password);
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getByUsername(String username) {
        String query = "SELECT * FROM users where username = ?;";
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(query);
            statement.setString(1,username);
            ResultSet result = statement.executeQuery();

            User user = null;

            while(result.next()) {
                user = new User(
                        result.getInt("id"),
                        result.getString("username"),
                        result.getString("name"),
                        result.getString("bio"),
                        result.getString("image"),
                        result.getInt("coins"),
                        result.getString("password")
                );
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User edit(String username, String name, String bio, String image, int coins) {
        String query = "UPDATE users SET name = ?, bio = ?, image = ?, coins = ? where username = ?;";
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(query);
            statement.setString(1,name);
            statement.setString(2,bio);
            statement.setString(3,image);
            statement.setInt(4,coins);
            statement.setString(5,username);
            statement.executeUpdate();

            return this.getByUsername(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM users WHERE id = ?;";
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(query);
            statement.setInt(1,id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
