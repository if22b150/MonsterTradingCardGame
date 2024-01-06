package at.technikum.repositories.battle;

import at.technikum.models.Battle;
import at.technikum.models.BattleRound;

import java.util.ArrayList;

public interface IBattleRepository {
    Battle store(int user1Id, int user2Id, String status);
    Battle get(int id);
    Battle setStatus(int id, String status);
    Battle setWinnerAndLoserUserId(int id, int winnerId, int loserId);
    BattleRound storeBattleRound(int battleId, int cardUser1Id, int cardUser2Id, int user1Damage, int user2Damage);
    ArrayList<Battle> getByUser(int userId, Boolean won);
}
