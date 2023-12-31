package at.technikum.repositories.packages;

import at.technikum.database.Database;
import at.technikum.models.Package;
import at.technikum.models.User;
import at.technikum.models.card.ACard;
import at.technikum.models.card.MonsterCard;
import at.technikum.models.card.SpellCard;
import at.technikum.utils.enums.ECardType;
import at.technikum.utils.enums.EElementType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

public class PackageRepository implements IPackageRepository {
    @Override
    public Package create() {
        String query = "INSERT INTO packages DEFAULT VALUES;";
        try {
            PreparedStatement statement = Database.connect().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1); // Assuming "id" is a long integer.
                System.out.println("Package created with ID: " + id);
                return new Package(id);
            } else {
                throw new SQLException("Creating package failed, no ID obtained.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<ACard> getCards(int packageId) {
        String query = "SELECT * FROM cards where package_id = ?;";
        try {
            PreparedStatement statement = Database.connect().prepareStatement(query);
            statement.setInt(1,packageId);
            ResultSet result = statement.executeQuery();

            ArrayList<ACard> cards = new ArrayList<>();

            while(result.next()) {
                ACard card = null;
                if(Objects.equals(result.getString("card_type"), ECardType.SPELL.type))
                    card = new SpellCard(
                            result.getInt("id"),
                            result.getString("public_id"),
                            result.getString("name"),
                            result.getInt("damage"),
                            EElementType.valueOf(result.getString("element_type").toUpperCase()),
                            result.getInt("package_id")
                    );
                else
                    card = new MonsterCard(
                            result.getInt("id"),
                            result.getString("public_id"),
                            result.getString("name"),
                            result.getInt("damage"),
                            EElementType.valueOf(result.getString("element_type").toUpperCase()),
                            result.getInt("package_id")
                    );
                cards.add(card);
            }
            return cards;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getAvailable() {
        String query = "SELECT DISTINCT p.id AS package_id FROM packages p RIGHT JOIN cards c ON p.id = c.package_id WHERE c.user_id IS NULL;";
        try {
            PreparedStatement statement = Database.connect().prepareStatement(query);
            ResultSet result = statement.executeQuery();

            ArrayList<Integer> packageIds = new ArrayList<Integer>();

            while(result.next()) {
                packageIds.add(result.getInt("package_id"));
            }
            if(packageIds.isEmpty())
                return -1;
            System.out.println(packageIds);
            return packageIds.get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
