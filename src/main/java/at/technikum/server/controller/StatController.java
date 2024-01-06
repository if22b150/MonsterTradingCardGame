package at.technikum.server.controller;

import at.technikum.models.Battle;
import at.technikum.models.User;
import at.technikum.repositories.battle.BattleRepository;
import at.technikum.repositories.battle.IBattleRepository;
import at.technikum.server.EContentType;
import at.technikum.server.HttpStatus;
import at.technikum.server.Response;

import java.util.ArrayList;

public class StatController {
    private static final IBattleRepository battleRepository = new BattleRepository();
    public Response show(User user) {
        int allBattles = battleRepository.getByUser(user.getId(), null).size();
        int wonBattles = battleRepository.getByUser(user.getId(), true).size();
        int lostBattles = battleRepository.getByUser(user.getId(), false).size();
        int draws = allBattles - wonBattles - lostBattles;

        int elo = 100; // starting value
        elo += wonBattles*3;
        elo -= lostBattles*5;

        String response = "{\"gamesPlayed\":" + allBattles + ",\"won\":" + wonBattles + ",\"lost\":" + lostBattles + ",\"draws\":" + draws + ",\"ELO\":" + elo + "}";

        return new Response(HttpStatus.OK, EContentType.JSON, response);
    }
}
