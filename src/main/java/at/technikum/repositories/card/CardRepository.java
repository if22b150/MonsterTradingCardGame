package at.technikum.repositories.card;

import at.technikum.database.Database;
import at.technikum.models.card.ACard;
import at.technikum.models.card.MonsterCard;
import at.technikum.models.card.SpellCard;
import at.technikum.utils.enums.ECardType;
import at.technikum.utils.enums.EElementType;

import java.sql.*;

public class CardRepository implements ICardRepository {
//    @Override
//    public ArrayList<ACard> all() {
//        ACard c1 = new MonsterCard("99f8f8dc-e25e-4a95-aa2c-782823f36e2a", "Monster", 20, EElementType.FIRE);
//        ArrayList<ACard> l = new ArrayList<ACard>();
//        l.add(c1);
//        return l;
//    }

//    @Override
//    public ACard get(int id) {
//        return new MonsterCard("99f8f8dc-e25e-4a95-aa2c-782823f36e2a", "Monster", 20, EElementType.FIRE);
//    }

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
}
