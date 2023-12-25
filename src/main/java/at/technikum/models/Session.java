package at.technikum.models;

public class Session extends AModel {
    private int userId;

    public String getToken() {
        return token;
    }

    private String token;

    public Session(int userId, String token) {
        this.userId = userId;
        this.token = token;
    }
    public Session(int id, int userId, String token) {
        this.id = id;
        this.userId = userId;
        this.token = token;
    }
}
