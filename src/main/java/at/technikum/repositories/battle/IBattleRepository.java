package at.technikum.repositories.battle;

import at.technikum.models.Battle;

public interface IBattleRepository {
    Battle store(int user1Id, int user2Id, String status);
}
