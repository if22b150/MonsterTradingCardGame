package at.technikum.models;

public class Transaction extends AModel {
    public int getUserId() {
        return userId;
    }

    public int getPackageId() {
        return packageId;
    }

    protected int userId;
    protected int packageId;

    public Transaction(int userId, int packageId) {
        this.userId = userId;
        this.packageId = packageId;
    }
    public Transaction(int id, int userId, int packageId) {
        this.id = id;
        this.userId = userId;
        this.packageId = packageId;
    }
}
