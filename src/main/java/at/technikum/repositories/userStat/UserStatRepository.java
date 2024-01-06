package at.technikum.repositories.userStat;

import at.technikum.database.Database;
import at.technikum.models.UserStat;
import at.technikum.repositories.user.IUserRepository;
import at.technikum.repositories.user.UserRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserStatRepository implements IUserStatRepository {
    private static final IUserRepository userRepository = new UserRepository();
    @Override
    public ArrayList<UserStat> all() {
        String query = "SELECT * FROM user_stats;";
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(query);
            ResultSet result = statement.executeQuery();

            ArrayList<UserStat> userStats = new ArrayList<>();

            while(result.next()) {
                UserStat us = new UserStat(
                        result.getInt("id"),
                        result.getInt("user_id"),
                        result.getInt("games_played"),
                        result.getInt("elo")
                );
                us.setUser(userRepository.get(result.getInt("user_id")));
                userStats.add(us);
            }
            return userStats;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserStat getByUserId(int userId) {
        String query = "SELECT * FROM user_stats where user_id = ?;";
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(query);
            statement.setInt(1,userId);
            ResultSet result = statement.executeQuery();

            UserStat userStat = null;

            while(result.next()) {
                userStat = new UserStat(
                        result.getInt("id"),
                        result.getInt("user_id"),
                        result.getInt("games_played"),
                        result.getInt("elo")
                );
            }
            return userStat;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserStat createOrUpdate(int userId, int gamesPlayed, int elo) {
        if(getByUserId(userId) == null) {
            String query = "INSERT INTO user_stats (user_id,games_played,elo) VALUES (?,?,?);";
            try {
                PreparedStatement statement = Database.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1,userId);
                statement.setInt(2,gamesPlayed);
                statement.setInt(3,elo);
                statement.executeUpdate();

                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1); // Assuming "id" is a long integer.
                    return new UserStat(id, userId, gamesPlayed, elo);
                } else {
                    throw new SQLException("Creating userStat failed, no ID obtained.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            String query = "UPDATE user_stats SET games_palyed = ?, elo = ? where user_id = ?;";
            try {
                PreparedStatement statement = Database.getConnection().prepareStatement(query);
                statement.setInt(1,gamesPlayed);
                statement.setInt(2,elo);
                statement.setInt(3,userId);
                statement.executeUpdate();

                return this.getByUserId(userId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
