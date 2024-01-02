package at.technikum.repositories.card;

import at.technikum.database.Database;
import at.technikum.models.User;
import at.technikum.models.card.ACard;
import at.technikum.models.card.MonsterCard;
import at.technikum.models.card.SpellCard;
import at.technikum.utils.enums.ECardType;
import at.technikum.utils.enums.EElementType;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class CardRepository implements ICardRepository {
    private ACard createCardFromResultSet(ResultSet result) {
        try {
            ACard card = null;
            if(Objects.equals(result.getString("card_type"), ECardType.SPELL.type))
                card = new SpellCard(
                        result.getInt("id"),
                        result.getString("public_id"),
                        result.getString("name"),
                        result.getInt("damage"),
                        EElementType.valueOf(result.getString("element_type").toUpperCase()),
                        result.getInt("package_id"),
                        result.getInt("user_id"),
                        result.getBoolean("deck")
                );
            else
                card = new MonsterCard(
                        result.getInt("id"),
                        result.getString("public_id"),
                        result.getString("name"),
                        result.getInt("damage"),
                        EElementType.valueOf(result.getString("element_type").toUpperCase()),
                        result.getInt("package_id"),
                        result.getInt("user_id"),
                        result.getBoolean("deck")
                );
            return card;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ACard get(int id) {
        String query = "SELECT * FROM cards where id = ?;";
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(query);
            statement.setInt(1,id);
            ResultSet result = statement.executeQuery();

            ACard card = null;
            while (result.next())
                card = createCardFromResultSet(result);
            return card;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ACard getByPublicId(String publicId) {
        String query = "SELECT * FROM cards where public_id = ?;";
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(query);
            statement.setString(1,publicId);
            ResultSet result = statement.executeQuery();

            ACard card = null;
            while (result.next())
                card = createCardFromResultSet(result);
            return card;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public ACard create(String publicId, String name, int damage, ECardType cardType, EElementType elementType, int packageId) {
        PreparedStatement statement = null;
        String query = "INSERT INTO cards (public_id, name, damage, card_type, element_type, package_id) VALUES (?,?,?,?,?,?);";
        try {
            statement = Database.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,publicId);
            statement.setString(2,name);
            statement.setInt(3,damage);
            statement.setString(4,cardType.type);
            statement.setString(5,elementType.type);
            statement.setInt(6,packageId);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1); // Assuming "id" is a long integer.
                System.out.println("Card created with ID: " + id);
                if(cardType == ECardType.SPELL)
                    return new SpellCard(id, publicId, name, damage, elementType, packageId);
                else
                    return new MonsterCard(id, publicId, name, damage, elementType, packageId);
            } else {
                throw new SQLException("Creating card failed, no ID obtained.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<ACard> getAllByUser(int userId) {
        String query = "SELECT * FROM cards where user_id = ?;";
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(query);
            statement.setInt(1,userId);
            ResultSet result = statement.executeQuery();

            ArrayList<ACard> cards = new ArrayList<>();

            while(result.next()) {
                cards.add(createCardFromResultSet(result));
            }
            return cards;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<ACard> getUserDeck(int userId) {
        String query = "SELECT * FROM cards where user_id = ? AND deck = true;";
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(query);
            statement.setInt(1,userId);
            ResultSet result = statement.executeQuery();

            ArrayList<ACard> cards = new ArrayList<>();

            while(result.next()) {
                cards.add(createCardFromResultSet(result));
            }
            return cards;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ACard setDeck(int id, boolean deck) {
        String query = "UPDATE cards SET deck = ? where id = ?;";
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(query);
            statement.setBoolean(1,deck);
            statement.setInt(2,id);
            statement.executeUpdate();

            return this.get(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
