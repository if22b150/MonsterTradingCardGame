package at.technikum.server.controller;

import at.technikum.models.User;
import at.technikum.repositories.battle.BattleRepository;
import at.technikum.repositories.battle.IBattleRepository;
import at.technikum.repositories.user.IUserRepository;
import at.technikum.repositories.user.UserRepository;
import at.technikum.server.EContentType;
import at.technikum.server.HttpStatus;
import at.technikum.server.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ScoreboardController {
    private static final IBattleRepository battleRepository = new BattleRepository();
    private static final IUserRepository userRepository = new UserRepository();
    public Response show() {
        ArrayList<User> users = userRepository.all();
        for(User user : users) {
            int allBattles = battleRepository.getByUser(user.getId(), null).size();
            int wonBattles = battleRepository.getByUser(user.getId(), true).size();
            int lostBattles = battleRepository.getByUser(user.getId(), false).size();
            int draws = allBattles - wonBattles - lostBattles;

            int elo = 100; // starting value
            elo += wonBattles*3;
            elo -= lostBattles*5;
            user.elo = elo;
        }
        users.sort(Comparator.comparing(User::getElo));
        Collections.reverse(users);

        String response = "[";
        int c = 0;
        for(User u : users) {
            if(c > 0)
                response += ",";
            response += ("{\"user\":\"" + u.getUsername() + "\",\"ELO\":" + u.getElo() + "}");
            c++;
        }
        response += "]";


        return new Response(HttpStatus.OK, EContentType.JSON, response);
    }
}
