package at.technikum.repositories.trade;

import at.technikum.models.CardTrade;

public interface ITradeRepository {
    CardTrade storeBattleTrade(int battleRoundId, int fromUserId, int toUserId, int fromUserCardId);
}
