package at.technikum.server.controller;

import at.technikum.BattleLogic;
import at.technikum.models.Battle;
import at.technikum.models.BattleRound;
import at.technikum.models.User;
import at.technikum.models.UserStat;
import at.technikum.models.card.ACard;
import at.technikum.repositories.battle.BattleRepository;
import at.technikum.repositories.battle.IBattleRepository;
import at.technikum.repositories.card.CardRepository;
import at.technikum.repositories.card.ICardRepository;
import at.technikum.repositories.trade.ITradeRepository;
import at.technikum.repositories.trade.TradeRepository;
import at.technikum.repositories.user.IUserRepository;
import at.technikum.repositories.user.UserRepository;
import at.technikum.repositories.userStat.IUserStatRepository;
import at.technikum.repositories.userStat.UserStatRepository;
import at.technikum.enums.EContentType;
import at.technikum.enums.HttpStatus;
import at.technikum.server.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class BattleController {
    private static final Logger logger = LogManager.getLogger("battles");
    private static final IUserRepository userRepository = new UserRepository();
    private static final IBattleRepository battleRepository = new BattleRepository();
    private static final ICardRepository cardRepository = new CardRepository();
    private static final ITradeRepository tradeRepository = new TradeRepository();
    private static final IUserStatRepository userStatRepository = new UserStatRepository();

    public Response store(int user1Id, int user2Id) {
        User user1 = userRepository.get(user1Id);
        User user2 = userRepository.get(user2Id);

        logger.info("Battle started with users " + user1.getUsername() + " and " + user2.getUsername());

        Battle battle = battleRepository.store(user1Id, user2Id, "IN_PROGRESS");

        User battleWinner = null;

        // fetch current decks
        ArrayList<ACard> user1Deck = cardRepository.getUserDeck(user1Id);
        ArrayList<ACard> user2Deck = cardRepository.getUserDeck(user2Id);

        int round = 1;
        while (round < 101) {
            // show how much cards are left in each user's deck
            if(round > 1)
                logger.info("(Remaining cards in deck: " + user1.getUsername() + ": " + user1Deck.size() + "; " + user2.getUsername() + ": " + user2Deck.size() + ")");

            // if deck of one user is empty, game is over
            if(user1Deck.isEmpty() || user2Deck.isEmpty()) {
                battleWinner = user1Deck.isEmpty() ? user2 : user1;

                battleRepository.setStatus(battle.getId(), "FINISHED");
                battleRepository.setWinnerAndLoserUserId(battle.getId(), user1Deck.isEmpty() ? user2.getId() : user1.getId(), user1Deck.isEmpty() ? user1.getId() : user2.getId());

                break;
            }

            logger.info("\n--------\nRound " + round + "\n--------");

            // print users decks
            BattleLogic.printUsersDecks(user1, user2, user1Deck, user2Deck);

            // select random card from deck
            ACard user1Card = user1Deck.get((int)(Math.random() * user1Deck.size()));
            ACard user2Card = user2Deck.get((int)(Math.random() * user2Deck.size()));

            // store battleRound in DB
            BattleRound battleRound = battleRepository.storeBattleRound(
                    battle.getId(),
                    user1Card.getId(),
                    user2Card.getId(),
                    BattleLogic.getDamage(user1Card, user2Card),
                    BattleLogic.getDamage(user2Card, user1Card)
            );
            battleRound.setUser1(user1);
            battleRound.setUser2(user2);
            battleRound.setUser1Card(user1Card);
            battleRound.setUser2Card(user2Card);

            // print results of round
            BattleLogic.printBattleRoundResult(battleRound);

            if(battleRound.getWinnerUser() != null) {
                // swap card of loser to winner
                cardRepository.setUserId(battleRound.getLoserCard().getId(), battleRound.getWinnerUser().getId());
                // store card swap/trade
                tradeRepository.storeBattleTrade(battleRound.getId(), battleRound.getLoserUser().getId(), battleRound.getWinnerUser().getId(), battleRound.getLoserCard().getId());

                // perform trade on battleDecks
                if(battleRound.getWinnerUser().getId() == user1Id) {
                    int indexOfLoserCard = getIndexOfCardInDeck(user2Deck, battleRound.getLoserCard().getId());
                    if(indexOfLoserCard == -1) {
                        logger.error("Card could not be traded");
                    } else {
                        ACard cardToTrade = user2Deck.remove(indexOfLoserCard);
                        user1Deck.add(cardToTrade);
                    }
                } else {
                    int indexOfLoserCard = getIndexOfCardInDeck(user1Deck, battleRound.getLoserCard().getId());
                    if(indexOfLoserCard == -1) {
                        logger.error("Card could not be traded");
                    } else {
                        ACard cardToTrade = user1Deck.remove(indexOfLoserCard);
                        user2Deck.add(cardToTrade);
                    }

//                    ACard cardToTrade = user1Deck.remove(user1Deck.indexOf(user1Deck.stream().filter(card -> Objects.equals(card.getId(), battleRound.getLoserCard().getId())).findFirst().orElse(null)));
//                    user2Deck.add(cardToTrade);
                }
            }

            round++;
        }

        // stat calculation
        int eloUser1 = 100;
        int eloUser2 = 100;
        int gamesPlayedUser1 = 1;
        int gamesPlayedUser2 = 1;

        UserStat currentStatUser1 = userStatRepository.getByUserId(user1Id);
        UserStat currentStatUser2 = userStatRepository.getByUserId(user2Id);
        if(currentStatUser1 != null) {
            gamesPlayedUser1 = currentStatUser1.getGamesPlayed();
            gamesPlayedUser1++;
            eloUser1 = currentStatUser1.getElo();
        }
        if(currentStatUser2 != null) {
            gamesPlayedUser2 = currentStatUser2.getGamesPlayed();
            gamesPlayedUser2++;
            eloUser2 = currentStatUser2.getElo();
        }

        if(battleWinner != null) {
            if(battleWinner.getId() == user1Id) {
                eloUser1 += 3;
                eloUser2 -= 5;
            } else {
                eloUser1 -= 5;
                eloUser2 += 3;
            }
        }

        userStatRepository.createOrUpdate(user1Id,gamesPlayedUser1,eloUser1);
        userStatRepository.createOrUpdate(user2Id,gamesPlayedUser2,eloUser2);

        logger.info("\n----------------------------------------\nBATTLE OVER! Result: " + ((battleWinner != null) ? ("!!! WINNER IS " + battleWinner.getUsername() + " !!!") : "DRAW") + "\n----------------------------------------");


        return new Response(HttpStatus.OK, EContentType.JSON, HttpStatus.OK.message);
    }

    private static int getIndexOfCardInDeck(ArrayList<ACard> deck, int cardId) {
        int index = 0;
        for(ACard c : deck) {
            if(c.getId() == cardId)
                return index;
            index++;
        }
        return -1;
    }

}
