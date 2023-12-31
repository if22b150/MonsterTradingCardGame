package at.technikum.repositories.transaction;

import at.technikum.database.Database;
import at.technikum.models.Transaction;
import at.technikum.models.card.ACard;
import at.technikum.repositories.packages.IPackageRepository;
import at.technikum.repositories.packages.PackageRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TransactionRepository implements ITransactionRepository {
    private static final IPackageRepository packageRepository = new PackageRepository();

    @Override
    public Transaction create(int userId, int packageId) {
        Transaction transaction = null;
        String query = "INSERT INTO transactions (user_id, package_id) VALUES (?,?);";
        try {
            PreparedStatement statement = Database.connect().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1,userId);
            statement.setInt(2,packageId);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1); // Assuming "id" is a long integer.
                System.out.println("Transaction created with ID: " + id);
                transaction = new Transaction(id, userId, packageId);
//                return new Transaction(id, userId, packageId);
            } else {
                throw new SQLException("Creating transaction failed, no ID obtained.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ArrayList<ACard> cardsOfPackage = packageRepository.getCards(packageId);
        for(ACard card: cardsOfPackage) {
            String updateQuery = "UPDATE cards SET user_id = ? WHERE id = ?;";
            try {
                PreparedStatement updateStatement = Database.connect().prepareStatement(updateQuery);
                updateStatement.setInt(1,userId);
                updateStatement.setInt(2,card.getId());
                updateStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return transaction;
    }
}
