package at.technikum.repositories.userStat;

import at.technikum.models.UserStat;

import java.util.ArrayList;

public interface IUserStatRepository {
    ArrayList<UserStat> all();
    UserStat getByUserId(int userId);
    UserStat createOrUpdate(int userId, int gamesPlayed, int elo);
}
