package at.technikum.server.controller;

import at.technikum.models.Battle;
import at.technikum.models.User;
import at.technikum.models.UserStat;
import at.technikum.repositories.battle.BattleRepository;
import at.technikum.repositories.battle.IBattleRepository;
import at.technikum.repositories.userStat.IUserStatRepository;
import at.technikum.repositories.userStat.UserStatRepository;
import at.technikum.server.EContentType;
import at.technikum.server.HttpStatus;
import at.technikum.server.Response;

import java.util.ArrayList;

public class StatController {
    private static final IBattleRepository battleRepository = new BattleRepository();
    private static final IUserStatRepository userStatRepository = new UserStatRepository();
    public Response show(User user) {
        int allBattles = 0;
        int wonBattles = 0;
        int lostBattles = 0;
        int draws = 0;
        int elo = 100;

        UserStat userStat = userStatRepository.getByUserId(user.getId());
        if(userStat != null) {
            elo = userStat.getElo();
            allBattles = userStat.getGamesPlayed();
            wonBattles = battleRepository.getByUser(user.getId(), true).size();
            lostBattles = battleRepository.getByUser(user.getId(), false).size();
            draws = allBattles - wonBattles - lostBattles;
        }

        String response = "{\"gamesPlayed\":" + allBattles + ",\"won\":" + wonBattles + ",\"lost\":" + lostBattles + ",\"draws\":" + draws + ",\"ELO\":" + elo + "}";

        return new Response(HttpStatus.OK, EContentType.JSON, response);
    }
}
