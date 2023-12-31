package at.technikum.repositories.transaction;

import at.technikum.models.Transaction;
import at.technikum.models.User;

public interface ITransactionRepository {
    Transaction create(int userId, int packageId);
}
