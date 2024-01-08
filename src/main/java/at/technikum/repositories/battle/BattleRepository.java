package at.technikum.repositories.battle;

import at.technikum.database.Database;
import at.technikum.models.Battle;
import at.technikum.models.BattleRound;

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

    private Battle createBattleFromResultSet(ResultSet result) {
        try {
            return new Battle(
                result.getInt("id"),
                result.getInt("user_1_id"),
                result.getInt("user_2_id"),
                result.getString("status")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Battle get(int id) {
        String query = "SELECT * FROM battles where id = ?;";
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(query);
            statement.setInt(1,id);
            ResultSet result = statement.executeQuery();

            Battle battle = null;
            while (result.next())
                battle = createBattleFromResultSet(result);
            return battle;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Battle setStatus(int id, String status) {
        String query = "UPDATE battles SET status = ? where id = ?;";
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(query);
            statement.setString(1,status);
            statement.setInt(2,id);
            statement.executeUpdate();

            return this.get(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Battle setWinnerAndLoserUserId(int id, int winnerId, int loserId) {
        String query = "UPDATE battles SET winner_user_id = ?, loser_user_id = ? where id = ?;";
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(query);
            statement.setInt(1,winnerId);
            statement.setInt(2,loserId);
            statement.setInt(3,id);
            statement.executeUpdate();

            return this.get(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BattleRound storeBattleRound(int battleId, int cardUser1Id, int cardUser2Id, int user1Damage, int user2Damage) {
        String query = "INSERT INTO battle_rounds (battle_id, card_user_1_id, card_user_2_id, user_1_damage, user_2_damage) VALUES (?,?,?,?,?);";
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1,battleId);
            statement.setInt(2,cardUser1Id);
            statement.setInt(3,cardUser2Id);
            statement.setInt(4,user1Damage);
            statement.setInt(5,user2Damage);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1); // Assuming "id" is a long integer.
                System.out.println("Battle Round created with ID: " + id);
                return new BattleRound(id, battleId, cardUser1Id, cardUser2Id, user1Damage, user2Damage);
            } else {
                throw new SQLException("Creating battle round failed, no ID obtained.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Battle> getByUser(int userId, Boolean won) {
        String query = won == null ? "SELECT * FROM battles where user_1_id = ? or user_2_id = ?;" : (won ? "SELECT * FROM battles where winner_user_id = ?;" : "SELECT * FROM battles where loser_user_id = ?;");
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(query);
            statement.setInt(1,userId);
            if(won == null)
                statement.setInt(2, userId);
            ResultSet result = statement.executeQuery();

            ArrayList<Battle> battles = new ArrayList<>();

            while(result.next()) {
                battles.add(createBattleFromResultSet(result));
            }
            return battles;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
