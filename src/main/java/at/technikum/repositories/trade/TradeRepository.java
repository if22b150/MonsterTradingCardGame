package at.technikum.repositories.trade;

import at.technikum.database.Database;
import at.technikum.models.CardTrade;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TradeRepository implements ITradeRepository {
    @Override
    public CardTrade storeBattleTrade(int battleRoundId, int fromUserId, int toUserId, int fromUserCardId) {
        String query = "INSERT INTO card_trades (battle_round_id, from_user_id, to_user_id, from_user_card_id) VALUES (?,?,?,?);";
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1,battleRoundId);
            statement.setInt(2,fromUserId);
            statement.setInt(3,toUserId);
            statement.setInt(4,fromUserCardId);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1); // Assuming "id" is a long integer.
                System.out.println("CardTrade created with ID: " + id);
                return new CardTrade(id, battleRoundId, fromUserId, toUserId, fromUserCardId);
            } else {
                throw new SQLException("Creating CardTrade failed, no ID obtained.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
