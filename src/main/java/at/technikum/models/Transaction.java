package at.technikum.models;

public class Transaction extends AModel {
    protected int userId;
    protected int packageId;

    public Transaction(int userId, int packageId) {
        this.userId = userId;
        this.packageId = packageId;
    }
}
