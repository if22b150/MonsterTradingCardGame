package at.technikum.models;

public class CardTrade extends AModel {
    int battleRoundId;
    int fromUserId;
    int toUserId;
    int fromUserCardId;
    int toUserCardId;

    public CardTrade(int id, int battleRoundId, int fromUserId, int toUserId, int fromUserCardId) {
        this.id = id;
        this.battleRoundId = battleRoundId;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.fromUserCardId = fromUserCardId;
    }
    public CardTrade(int id, int battleRoundId, int fromUserId, int toUserId, int fromUserCardId, int toUserCardId) {
        this.id = id;
        this.battleRoundId = battleRoundId;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.fromUserCardId = fromUserCardId;
        this.toUserCardId = toUserCardId;
    }
}
