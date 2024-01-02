package at.technikum.repositories.session;

import at.technikum.database.Database;
import at.technikum.models.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SessionRepository implements ISessionRepository {
    @Override
    public Session create(int userId, String token) {
        String query = "INSERT INTO sessions (user_id,token) VALUES (?,?);";
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1,userId);
            statement.setString(2,token);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1); // Assuming "id" is a long integer.
                System.out.println("Session created with ID: " + id);
                return new Session(id, userId, token);
            } else {
                throw new SQLException("Creating session failed, no ID obtained.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Session getByUser(int userId) {
        String query = "SELECT * FROM sessions where user_id = ?;";
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(query);
            statement.setInt(1,userId);
            ResultSet result = statement.executeQuery();

            Session session = null;

            while(result.next()) {
                session = new Session(
                        result.getInt("id"),
                        result.getInt("user_id"),
                        result.getString("token")
                );
            }
            return session;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Session getByToken(String token) {
        String query = "SELECT * FROM sessions where token = ?;";
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(query);
            statement.setString(1,token);
            ResultSet result = statement.executeQuery();

            Session session = null;

            while(result.next()) {
                session = new Session(
                        result.getInt("id"),
                        result.getInt("user_id"),
                        result.getString("token")
                );
            }
            return session;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
