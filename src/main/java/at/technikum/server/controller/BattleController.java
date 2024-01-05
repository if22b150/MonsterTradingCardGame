package at.technikum.server.controller;

import at.technikum.BattleLogic;
import at.technikum.models.Battle;
import at.technikum.models.User;
import at.technikum.models.card.ACard;
import at.technikum.repositories.battle.BattleRepository;
import at.technikum.repositories.battle.IBattleRepository;
import at.technikum.repositories.card.CardRepository;
import at.technikum.repositories.card.ICardRepository;
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

    public Response store(int user1Id, int user2Id) {
        User user1 = userRepository.get(user1Id);
        User user2 = userRepository.get(user2Id);

//        Battle battle = battleRepository.store(user1Id, user2Id, "IN_PROGRESS");
        int c = 0;
        while (c < 5) {
            ArrayList<ACard> user1Deck = cardRepository.getUserDeck(user1Id);
            ArrayList<ACard> user2Deck = cardRepository.getUserDeck(user2Id);

            if(c > 0)
                System.out.println("Remaining cards in deck: " + user1.getUsername() + ": " + user1Deck.size() + "; " + user2.getUsername() + ": " + user2Deck.size());

            if(user1Deck.isEmpty() || user2Deck.isEmpty()) {
                break;
            }

            // select random card from deck
            ACard user1Card = user1Deck.get((int)(Math.random() * user1Deck.size()));
            ACard user2Card = user1Deck.get((int)(Math.random() * user2Deck.size()));

            System.out.println(user1.getUsername() + " Card: " + user1Card.getName() + "; Damage: " + BattleLogic.getDamage(user1Card, user2Card));
            System.out.println(user2.getUsername() + " Card: " + user2Card.getName() + "; Damage: " + BattleLogic.getDamage(user2Card, user1Card));

            Integer winnerId = BattleLogic.getRoundWinner(user1Card, user2Card);
            if(winnerId == null) {
                System.out.println("Draw.");
            } else {
                User winner = user1Id == winnerId ? user1 : user2;
                User loser = user1Id == winnerId ? user2 : user1;
                ACard loserCard = user1Id == winnerId ? user2Card : user1Card;
                System.out.println("Winner: " + winner.getUsername());
                System.out.println("Card Transaction: Card \"" + loserCard.getPublicId() + "\" (" + loserCard.getName() + ") goes from " + loser.getUsername() + " to " + winner.getUsername());
            }
            System.out.println();
            c++;
        }

        // set battle status
        // set stats
        // print winner

        return new Response(HttpStatus.OK, EContentType.JSON, HttpStatus.OK.message);
    }
}
