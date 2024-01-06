package at.technikum.models;

import at.technikum.models.card.ACard;

public class BattleRound extends AModel {
    int battleId;
    int cardUser1Id;
    int cardUser2Id;

    public int getUser1Damage() {
        return user1Damage;
    }

    public int getUser2Damage() {
        return user2Damage;
    }

    int user1Damage;
    int user2Damage;

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    User user1;
    User user2;

    public ACard getUser1Card() {
        return user1Card;
    }

    public void setUser1Card(ACard user1Card) {
        this.user1Card = user1Card;
    }

    public ACard getUser2Card() {
        return user2Card;
    }

    public void setUser2Card(ACard user2Card) {
        this.user2Card = user2Card;
    }

    ACard user1Card;
    ACard user2Card;

    public User getWinnerUser() {
        return user1Damage > user2Damage ? user1 : (user2Damage > user1Damage ? user2 : null);
    }
    public User getLoserUser() {
        return user1Damage > user2Damage ? user2 : (user2Damage > user1Damage ? user1 : null);
    }
    public ACard getLoserCard() {
        return getWinnerUser().getId() == user1.getId() ? user2Card : (getWinnerUser() == null ? null : user1Card);
    }

    public BattleRound(int id, int battleId, int cardUser1Id, int cardUser2Id, int user1Damage, int user2Damage) {
        this.id = id;
        this.battleId = battleId;
        this.cardUser1Id = cardUser1Id;
        this.cardUser2Id = cardUser2Id;
        this.user1Damage = user1Damage;
        this.user2Damage = user2Damage;
    }
    public BattleRound(int id, int battleId, int cardUser1Id, int cardUser2Id, int user1Damage, int user2Damage, User user1, User user2) {
        this.id = id;
        this.battleId = battleId;
        this.cardUser1Id = cardUser1Id;
        this.cardUser2Id = cardUser2Id;
        this.user1Damage = user1Damage;
        this.user2Damage = user2Damage;
        this.user1 = user1;
        this.user2 = user2;
    }


}
