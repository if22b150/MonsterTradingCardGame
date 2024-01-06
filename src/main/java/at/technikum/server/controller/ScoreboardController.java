package at.technikum.server.controller;

import at.technikum.models.User;
import at.technikum.models.UserStat;
import at.technikum.repositories.battle.BattleRepository;
import at.technikum.repositories.battle.IBattleRepository;
import at.technikum.repositories.user.IUserRepository;
import at.technikum.repositories.user.UserRepository;
import at.technikum.repositories.userStat.IUserStatRepository;
import at.technikum.repositories.userStat.UserStatRepository;
import at.technikum.server.EContentType;
import at.technikum.server.HttpStatus;
import at.technikum.server.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ScoreboardController {
    private static final IUserStatRepository userStatRepository = new UserStatRepository();
    public Response show() {
        ArrayList<UserStat> userStats = userStatRepository.all();
        userStats.sort(Comparator.comparing(UserStat::getElo));
        Collections.reverse(userStats);

        String response = "[";
        int c = 0;
        for(UserStat us : userStats) {
            if(c > 0)
                response += ",";
            response += ("{\"user\":\"" + us.getUser().getUsername() + "\",\"ELO\":" + us.getElo() + ",\"gamesPlayed\":" + us.getGamesPlayed() + "}");
            c++;
        }
        response += "]";

        return new Response(HttpStatus.OK, EContentType.JSON, response);
    }
}
