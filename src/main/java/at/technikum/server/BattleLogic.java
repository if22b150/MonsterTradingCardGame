package at.technikum.server;

import at.technikum.models.BattleRound;
import at.technikum.models.User;
import at.technikum.models.card.ACard;
import at.technikum.enums.ECardName;
import at.technikum.enums.ECardType;
import at.technikum.enums.EElementType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Objects;

public class BattleLogic {
    private static final Logger logger = LogManager.getLogger("battles");

    // calculate damage of first parameter card
    public static int getDamage(ACard card1, ACard card2) {
        // specialties
        if((Objects.equals(card1.getName(), ECardName.WaterGoblin.name) && Objects.equals(card2.getName(), ECardName.Dragon.name))
        || (Objects.equals(card1.getName(), ECardName.Ork.name) && Objects.equals(card2.getName(), ECardName.Wizard.name))
        || (Objects.equals(card1.getName(), ECardName.Knight.name) && Objects.equals(card2.getName(), ECardName.WaterSpell.name))
        || (Objects.equals(card1.getType(), ECardType.SPELL) && Objects.equals(card2.getName(), ECardName.Kraken.name))
        || (Objects.equals(card1.getName(), ECardName.Dragon.name) && Objects.equals(card2.getName(), ECardName.FireElf.name)))
            return 0;

        if (card1.getType() != ECardType.MONSTER || card2.getType() != ECardType.MONSTER) {
            // spells included, element types can affect damage
            EElementType doubleElement = card1.getElementType() == EElementType.FIRE ? EElementType.NORMAL : (card1.getElementType() == EElementType.WATER ? EElementType.FIRE : EElementType.WATER);
            EElementType halfElement = card1.getElementType() == EElementType.FIRE ? EElementType.WATER : (card1.getElementType() == EElementType.WATER ? EElementType.NORMAL : EElementType.FIRE);

            // card2 has element type, where the attack of card1 gets doubled
            if (card2.getElementType() == doubleElement)
                return card1.getDamage() * 2;
            // card2 has element type, where the attack of card1 gets halved
            if (card2.getElementType() == halfElement)
                return card1.getDamage() / 2;
        }

        // either the cards are both monster cards or they have the same element type
        return card1.getDamage();
    }

    public static void printBattleRoundResult(BattleRound battleRound) {
        logger.info(battleRound.getUser1().getUsername() + " attacks with " + battleRound.getUser1Card().getName() + " (" + battleRound.getUser1Card().getDamage() + ") -> Actual Damage: " + battleRound.getUser1Damage());
        logger.info(battleRound.getUser2().getUsername() + " attacks with " + battleRound.getUser2Card().getName() + " (" + battleRound.getUser2Card().getDamage() + ") -> Actual Damage: " + battleRound.getUser2Damage());
        if(battleRound.getWinnerUser() == null) {
            logger.info("DRAW");
        } else {
            logger.info("WINNER: " + battleRound.getWinnerUser().getUsername());
            logger.info("-> Card \"" + battleRound.getLoserCard().getPublicId() + "\" (" + battleRound.getLoserCard().getName() + ") goes from " + battleRound.getLoserUser().getUsername() + " to " + battleRound.getWinnerUser().getUsername());
        }
    }

    public static void printUsersDecks(User user1, User user2, ArrayList<ACard> user1Deck, ArrayList<ACard> user2Deck) {
        logger.info("Deck of " + user1.getUsername() + ":");
        for(ACard card : user1Deck)
            logger.info("    " + card.getName() + " (" + card.getDamage() + ")");

        logger.info("Deck of " + user2.getUsername() + ":");
        for(ACard card : user2Deck)
            logger.info("    " + card.getName() + " (" + card.getDamage() + ")");
    }
}
