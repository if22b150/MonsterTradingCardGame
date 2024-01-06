package at.technikum.models;

public class Battle extends AModel {
    public int getUser1Id() {
        return user1Id;
    }

    public int getUser2Id() {
        return user2Id;
    }

    public String getStatus() {
        return status;
    }

    int user1Id;
    int user2Id;
    String status;

    int winnerUserId;
    int loserUserId;

    public Battle(int id, int user1Id, int user2Id, String status) {
        this.id = id;
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.status = status;
    }
}
