package at.technikum.server.controller;

import at.technikum.BattleLogic;
import at.technikum.models.Battle;
import at.technikum.models.BattleRound;
import at.technikum.models.User;
import at.technikum.models.card.ACard;
import at.technikum.repositories.battle.BattleRepository;
import at.technikum.repositories.battle.IBattleRepository;
import at.technikum.repositories.card.CardRepository;
import at.technikum.repositories.card.ICardRepository;
import at.technikum.repositories.trade.ITradeRepository;
import at.technikum.repositories.trade.TradeRepository;
import at.technikum.repositories.user.IUserRepository;
import at.technikum.repositories.user.UserRepository;
import at.technikum.server.EContentType;
import at.technikum.server.HttpStatus;
import at.technikum.server.Response;

import java.util.ArrayList;
import java.util.Objects;

public class BattleController {
    private static final IUserRepository userRepository = new UserRepository();
    private static final IBattleRepository battleRepository = new BattleRepository();
    private static final ICardRepository cardRepository = new CardRepository();
    private static final ITradeRepository tradeRepository = new TradeRepository();

    public Response store(int user1Id, int user2Id) {
        User user1 = userRepository.get(user1Id);
        User user2 = userRepository.get(user2Id);

        Battle battle = battleRepository.store(user1Id, user2Id, "IN_PROGRESS");

        // fetch current decks
        ArrayList<ACard> user1Deck = cardRepository.getUserDeck(user1Id);
        ArrayList<ACard> user2Deck = cardRepository.getUserDeck(user2Id);

        int round = 1;
        while (round < 101) {
            // show how much cards are left in each user's deck
            if(round > 1)
                System.out.println("(Remaining cards in deck: " + user1.getUsername() + ": " + user1Deck.size() + "; " + user2.getUsername() + ": " + user2Deck.size() + ")\n");

            // if deck of one user is empty, game is over
            if(user1Deck.isEmpty() || user2Deck.isEmpty()) {
                battleRepository.setStatus(battle.getId(), "FINISHED");
                battleRepository.setWinnerAndLoserUserId(battle.getId(), user1Deck.isEmpty() ? user2.getId() : user1.getId(), user1Deck.isEmpty() ? user1.getId() : user2.getId());

                System.out.println("----------------------------------------");
                System.out.println("!!! WINNER is " + (user1Deck.isEmpty() ? user1.getUsername() : user2.getUsername()) + " !!!");

                break;
            }

            System.out.println("--------\nRound " + round + "\n--------");

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
                    ACard cardToTrade = user2Deck.remove(user2Deck.indexOf(user2Deck.stream().filter(card -> card.getId() == battleRound.getLoserCard().getId()).findFirst().orElse(null)));
                    user1Deck.add(cardToTrade);
                } else {
                    ACard cardToTrade = user1Deck.remove(user1Deck.indexOf(user1Deck.stream().filter(card -> card.getId() == battleRound.getLoserCard().getId()).findFirst().orElse(null)));
                    user2Deck.add(cardToTrade);
                }
            }

            round++;
        }

        System.out.println("----------------------------------------\nBATTLE OVER\n----------------------------------------");

        return new Response(HttpStatus.OK, EContentType.JSON, HttpStatus.OK.message);
    }


}
