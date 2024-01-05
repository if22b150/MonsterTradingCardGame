package at.technikum.repositories.battle;

import at.technikum.database.Database;
import at.technikum.models.Battle;
import at.technikum.models.Transaction;
import at.technikum.models.card.ACard;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BattleRepository implements IBattleRepository {
    @Override
    public Battle store(int user1Id, int user2Id, String status) {
        String query = "INSERT INTO battles (user_1_id, user_2_id, status) VALUES (?,?,?);";
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1,user1Id);
            statement.setInt(2,user2Id);
            statement.setString(3,status);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1); // Assuming "id" is a long integer.
                System.out.println("Battle created with ID: " + id);
                return new Battle(id, user1Id, user2Id, status);
            } else {
                throw new SQLException("Creating battle failed, no ID obtained.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
