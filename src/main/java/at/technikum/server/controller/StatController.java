package at.technikum.server.controller;

import at.technikum.models.Battle;
import at.technikum.models.BattleRound;
import at.technikum.models.User;
import at.technikum.models.UserStat;
import at.technikum.repositories.battle.BattleRepository;
import at.technikum.repositories.battle.IBattleRepository;
import at.technikum.repositories.userStat.IUserStatRepository;
import at.technikum.repositories.userStat.UserStatRepository;
import at.technikum.enums.EContentType;
import at.technikum.enums.HttpStatus;
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

        int wlration = 0;
        int winPercentage = 0;
        if(lostBattles != 0)
            wlration = wonBattles / lostBattles;

        if(allBattles != 0)
            winPercentage = wonBattles/allBattles * 100;

        ArrayList<Battle> battles = battleRepository.getByUser(user.getId(), null);
        int wonRounds = 0;
        int lostRounds = 0;
        int drawRounds = 0;
        for (Battle b : battles) {
            ArrayList<BattleRound> battleRounds = battleRepository.getRoundsByBattle(b.getId());
            for(BattleRound br : battleRounds) {
                if(user.getId() == b.getUser1Id()) {
                    if(br.getUser1Damage() > br.getUser2Damage())
                        wonRounds++;
                    else if (br.getUser1Damage() < br.getUser2Damage())
                        lostRounds++;
                    else
                        drawRounds++;
                } else {
                    if(br.getUser1Damage() > br.getUser2Damage())
                        lostRounds++;
                    else if (br.getUser1Damage() < br.getUser2Damage())
                        wonRounds++;
                    else
                        drawRounds++;
                }
            }
        }

        String response = "{\"gamesPlayed\":" + allBattles + ",\"won\":" + wonBattles + ",\"lost\":" + lostBattles + ",\"draws\":" + draws + ",\"ELO\":" + elo + ",\"winLossRatio\":" + (wlration != 0 ? wlration : "null") + ",\"winPercentage\":" + winPercentage + ",\"wonRounds\":" + wonRounds + ",\"lostRounds\":" + lostRounds + ",\"drawRounds\":" + drawRounds + "}";

        return new Response(HttpStatus.OK, EContentType.JSON, response);
    }
}
