package at.technikum.models;

public class UserStat extends AModel {
    protected int userId;
    protected int gamesPlayed;

    public int getUserId() {
        return userId;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getElo() {
        return elo;
    }

    protected int elo;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    protected User user;

    public UserStat(int id, int userId, int gamesPlayed, int elo) {
        this.id = id;
        this.userId = userId;
        this.gamesPlayed = gamesPlayed;
        this.elo = elo;
    }
}
